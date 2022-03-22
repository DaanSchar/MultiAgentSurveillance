package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.ai.NeuralGameState;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class Guard extends Agent {

    private MultiLayerNetwork explorationNetwork;

    private final ArrayList<Direction> orderList = new ArrayList<>(Arrays.stream(new Direction[]{
            Direction.NORTH, Direction.EAST,
            Direction.SOUTH, Direction.WEST
    }).toList());

    public Guard() {
        super();
        Collections.shuffle(orderList);
    }

    public void explore() {
        if (!isDone()) {
            markingStep();
            navigationStep();
        }
    }

    private void markingStep() {
        if (!currentCellBlocksPath()) {
            getCurrentTile().setVisited(true);
        }

        getCurrentTile().setExplored(true);
    }

    private void navigationStep() {
        if (hasNeighboringUnexploredTiles()) {
            moveToBestUnexploredTile();
        } else if (hasNeighboringExploredTiles()) {
            moveToBestExploredTile();
        } else {
            setDone(true);
        }
    }

    private boolean currentCellBlocksPath() {
        var neighbours = getPassableNeighbors(getCurrentTile());

        for (Tile neighbour : neighbours) {
            for (Tile otherNeighbour : neighbours) {
                if (!neighbour.equals(otherNeighbour)) {
                    var hasPath = new BFS().search(neighbour, otherNeighbour);
                    return !hasPath;
                }
            }
        }

        return false;
    }

    private void moveToBestUnexploredTile() {
        var targetTile = getBestUnexploredTile();
        var nextPosition = getMovement().goForward(getPosition(), getDirection(), Game.getInstance().getTime());

        if (nextPosition.equals(targetTile.getPosition()) || targetTile.getType() == TileType.TELEPORT) {
            goForward(Game.getInstance().getTime());
        } else {
            rotate(-1, Game.getInstance().getTime());
        }
    }

    /**
     * @return The MemoryTile adjacent to the agent which is most
     * likely to be marked as visited in the marking step
     *
     * the best tile is the tile which has the most adjacent non-passable tiles.
     */
    private MemoryTile getBestUnexploredTile() {
        var unexploredTiles = getUnexploredNeighboringTiles(getCurrentTile());

        if (Math.random() < Game.getInstance().getRandomness()) { Collections.shuffle(unexploredTiles); }

        List<Tile> sortedList = unexploredTiles.stream().sorted(
                (t1 , t2) -> getNonPassableNeighbors(t2).size() - getNonPassableNeighbors(t1).size()
        ).collect(Collectors.toList());


        return (MemoryTile) sortedList.get(0);
    }

    private void moveToBestExploredTile() {
        var targetTile = getBestExploredTile();
        var nextPosition = getMovement().goForward(getPosition(), getDirection(), Game.getInstance().getTime());

        if (nextPosition.equals(targetTile.getPosition())) {
            goForward(Game.getInstance().getTime());
        } else {
            rotate(-1, Game.getInstance().getTime());
        }
    }

    private Tile getBestExploredTile() {
        for (Direction direction : orderList) {
            var tile = (MemoryTile)getTileInDirection(getCurrentTile(), direction);

            if (!tile.isVisited() && tile.isExplored()) {
                return tile;
            }
        }

        return null;
    }

    private Tile getTileInDirection(Tile tile, Direction direction) {
        var map = getMemoryModule().getMap().getTileMap();
        var x = tile.getPosition().getX() + direction.getMoveX();
        var y = tile.getPosition().getY() + direction.getMoveY();

        return map[x][y];
    }

    /**
     * @return true if the agent has any explored tiles adjacent to it
     */
    private boolean hasNeighboringExploredTiles() {
        return getExploredNeighboringTiles(getCurrentTile()).size() > 0;
    }

    /**
     * @return true if the agent has any unexplored tiles adjacent to it
     */
    private boolean hasNeighboringUnexploredTiles() {
        return getUnexploredNeighboringTiles(getCurrentTile()).size() > 0;
    }

    /**
     * @param tile The tile to check
     * @return A list of tiles adjacent to the given tile which are non-passable
     */
    private List<Tile> getNonPassableNeighbors(Tile tile) {
        var surroundingTiles = getMemoryModule().getMap().getNeighbouringTiles(tile).stream();

        return surroundingTiles.filter(
                (surroundingTile) -> !isPassable((MemoryTile) surroundingTile)
        ).collect(Collectors.toList());
    }

    /**
     * @param tile The tile to check
     * @return A list of tiles adjacent to the given tile which are passable
     */
    private List<Tile> getPassableNeighbors(Tile tile) {
        var surroundingTiles = getMemoryModule().getMap().getNeighbouringTiles(tile).stream();

        return surroundingTiles.filter(
                (surroundingTile) -> isPassable((MemoryTile) surroundingTile)
        ).collect(Collectors.toList());
    }

    /**
     * @param tile The tile to check
     * @return checks if a tile is non-passable (or visited in the case of the algorithm).
     */
    private boolean isPassable(MemoryTile tile) {
        return !tile.isVisited() && tile.getType().isPassable();
    }

    /**
     * @param tile The tile we get the neighboring tiles from
     * @return A list of tiles which are adjacent to the agent and are not marked as explored
     */
    private List<Tile> getUnexploredNeighboringTiles(Tile tile) {
        var surroundingTiles = getMemoryModule().getMap().getNeighbouringTiles(tile).stream();
        return surroundingTiles.filter(memoryTile -> isUnExplored((MemoryTile) memoryTile)).collect(Collectors.toList());
    }

    /**
     *
     * @param tile The tile we check if it is unexplored
     * @return True if the tile is unexplored, is passable and not visited
     */
    private boolean isUnExplored(MemoryTile tile) {
        return !tile.isExplored() && tile.getType().isPassable() && !tile.isVisited();
    }

    /**
     * @param tile The tile we get the neighboring tiles from
     * @return A list of tiles which are adjacent to the agent and are marked as explored
     */
    private List<Tile> getExploredNeighboringTiles(Tile tile) {
        var surroundingTiles = getMemoryModule().getMap().getNeighbouringTiles(tile).stream();
        return surroundingTiles.filter(memoryTile -> isExplored((MemoryTile) memoryTile)).collect(Collectors.toList());
    }

    /**
     * @param tile The tile we check if it is explored
     * @return True if a tile is marked as explored, is passable and not visited
     */
    private boolean isExplored(MemoryTile tile) {
        return tile.isExplored() && tile.getType().isPassable() && !tile.isVisited();
    }


    class BFS {

        private final Queue<Tile> queue;
        private final List<Tile> visited;

        public BFS() {
            this.queue = new LinkedList<>();
            this.visited = new ArrayList<>();
        }

        public boolean search(Tile start, Tile goal) {
            queue.add(start);

            Tile current;
            while (!queue.isEmpty()) {
                current = queue.poll();
                if (current.equals(goal)) { return true; }

                visited.add(current);

                for (Tile neighbour : getPassableNeighbors(current)) {
                    if (neighbour.equals(goal)) { return true; }
                    if (!visited.contains(neighbour)) {
                        queue.add(neighbour);
                        visited.add(neighbour);
                    }
                }
            }
            return false;
        }

    }









    // network stuff

    private void performActionFromNetwork() {
        int action = getActionFromNetwork();


        if (action == 0) {
            goForward(Game.getInstance().getTime());
        }  else {
            rotate(action, Game.getInstance().getTime());
        }
    }

    private void loadNetwork(String network) {
        try {
            this.explorationNetwork = MultiLayerNetwork.load(new File(network), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return int, the action that the network outputs.
     */
    private int getActionFromNetwork() {
        var input = getStateVector();
        var output = getNetworkOutput(input);
        var action = getIndexOfLargestValue(output);

        if (Math.random() < 0.01) {
            action = (int) Math.round(Math.random() * 3);
        }

        return action - 1;
    }


    /**
     * Get the output of the network for the given input
     * @param input double[], the input vector.
     * @return double[], the output vector.
     */
    private double[] getNetworkOutput(double[] input) {
        NeuralGameState state = new NeuralGameState(input);
        INDArray output = explorationNetwork.output(state.getMatrix(), false);

        return output.data().asDouble();
    }

    /**
     * Get the index of the largest value in the given array.
     * @param values double[], the array to search in.
     * @return int, the index of the largest value.
     */
    private int getIndexOfLargestValue(double[] values) {
        int index = 0;

        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[index]) {
                index = i;
            }
        }

        return index;
    }

}
