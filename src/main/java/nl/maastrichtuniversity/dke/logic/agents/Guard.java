package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.ai.NeuralGameState;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class Guard extends Agent {

    private MultiLayerNetwork explorationNetwork;

    private final Direction[] orderList = {
            Direction.NORTH, Direction.EAST,
            Direction.SOUTH, Direction.WEST
    };


    public Guard() {
        super();

        // loadNetwork("src/main/resources/networks/network-1647872939503-input-22-output-3-nodes-128-learningrate-0.01-epochs-1000.zip");
    }

    public void explore() {
        markingStep();
        navigationStep();
    }

    private boolean findPath(Tile input, Tile target){
        var map = getMemoryModule().getMap();
        List<Tile> frontier = new ArrayList<>();
        frontier.add(input);
        while(true){
            if(frontier.isEmpty())
                return false;
            Tile tile = frontier.remove(0);
            if(tile.equals(target))
                return true;
            for(Tile x : map.getNeighbouringTiles(tile)){
                if(x.getType().isPassable()
                        && !x.getType().equals(TileType.UNKNOWN)
                        && !x.equals(tile)
                        && !((MemoryTile) x).isVisited())
                    frontier.add(x);
            }
        }
    }


    private void markingStep() {
        if (!currentCellBlocksPath()) { getCurrentTile().setVisited(true); }

        getCurrentTile().setExplored(true);
    }

    private void navigationStep() {
        if (hasNeighboringUnexploredTiles()) {
            moveToBestUnexploredTile();
        } else if (hasNeighboringExploredTiles()) {
            log.info("No unexplored tiles, but we have explored tiles");
            moveToBestExploredTile();
        }
    }

    private void moveToBestUnexploredTile() {
        var targetTile = getBestUnexploredTile();
        var nextPosition = getMovement().goForward(getPosition(), getDirection(), Game.getInstance().getTime());

        log.info("nextPosition: {}, targetTile: {}", nextPosition, targetTile.getPosition());
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
        var unexploredTiles = getUnexploredNeighboringTiles(getCurrentTile()).stream();

        List<Tile> sortedList = unexploredTiles.sorted(
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

    private boolean hasNeighboringExploredTiles() {
        return getExploredNeighboringTiles(getCurrentTile()).size() > 0;
    }

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
                (surroundingTile) -> isNonPassable((MemoryTile) surroundingTile)
        ).collect(Collectors.toList());
    }

    /**
     * @param tile The tile to check
     * @return checks if a tile is non-passable (or visited in the case of the algorithm).
     */
    private boolean isNonPassable(MemoryTile tile) {
        return tile.isVisited() || !tile.getType().isPassable();
    }

    /**
     * @param tile The tile we get the neighboring tiles from
     * @return A list of tiles which are adjacent to the agent and are not marked as explored
     */
    private List<Tile> getUnexploredNeighboringTiles(Tile tile) {
        var surroundingTiles = getMemoryModule().getMap().getNeighbouringTiles(tile).stream();

        return surroundingTiles.filter(
                memoryTile -> !((MemoryTile)memoryTile).isExplored() && memoryTile.getType().isPassable()
        ).collect(Collectors.toList());
    }

    /**
     * @param tile The tile we get the neighboring tiles from
     * @return A list of tiles which are adjacent to the agent and are marked as explored
     */
    private List<Tile> getExploredNeighboringTiles(Tile tile) {
        var surroundingTiles = getMemoryModule().getMap().getNeighbouringTiles(tile).stream();
        return surroundingTiles.filter(
                memoryTile -> !((MemoryTile)memoryTile).isExplored()
        ).collect(Collectors.toList());
    }

    private boolean currentCellBlocksPath() { return false; }















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
