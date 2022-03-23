package nl.maastrichtuniversity.dke.logic.agents.modules.exploration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ExplorationModule implements IExplorationModule {


    private final Environment environment;

    private final List<Direction> orderList;

    private @Getter boolean isDoneExploring;

    private final IMovement movementModule;

    private Position currentPosition;
    private Direction currentDirection;

    public ExplorationModule(Environment environment, IMovement movementModule) {
        this.environment = environment;
        this.movementModule = movementModule;

        isDoneExploring = false;
        orderList = Direction.getAllDirections();
        Collections.shuffle(orderList);
    }

    @Override
    public MoveAction explore(Position currentPosition, Direction currentDirection) {
        this.currentPosition = currentPosition;
        this.currentDirection = currentDirection;

        if (isDoneExploring) return MoveAction.DO_NOTHING;

        markingStep();
        return navigationStep();
    }

    private void markingStep() {
        if (!currentCellBlocksPath()) {
            log.info("Marking current cell as explored");
            getCurrentTile().setVisited(true);
        }

        getCurrentTile().setExplored(true);
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

    private MoveAction navigationStep() {
        if (hasNeighboringUnexploredTiles()) {
            return moveToBestUnexploredTile();
        } else if (hasNeighboringExploredTiles()) {
            return moveToBestExploredTile();
        } else {
            isDoneExploring = true;
        }

        return MoveAction.DO_NOTHING;
    }

    private MoveAction moveToBestUnexploredTile() {
        var targetTile = getBestUnexploredTile();
        var nextPosition = movementModule.goForward(currentPosition, currentDirection, Game.getInstance().getTime());

        boolean targetTileAhead = nextPosition.equals(targetTile.getPosition());

        if (targetTileAhead || targetTile.getType() == TileType.TELEPORT) return MoveAction.MOVE_FORWARD;

        return MoveAction.ROTATE_LEFT;
    }

    private MoveAction moveToBestExploredTile() {
        var targetTile = getBestExploredTile();
        var nextPosition = movementModule.goForward(currentPosition, currentDirection, Game.getInstance().getTime());

        if (nextPosition.equals(targetTile.getPosition())) { return MoveAction.MOVE_FORWARD; }

        return MoveAction.ROTATE_LEFT;
    }

    private MemoryTile getCurrentTile() {
        var x = currentPosition.getX();
        var y = currentPosition.getY();
        return (MemoryTile) environment.getTileMap()[x][y];
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

        unexploredTiles.sort((t1 , t2) -> getNonPassableNeighbors(t2).size() - getNonPassableNeighbors(t1).size());

        return (MemoryTile) unexploredTiles.get(0);
    }

    private Tile getBestExploredTile() {
        for (Direction direction : orderList) {
            var tile = (MemoryTile) getTileInDirection(getCurrentTile(), direction);

            if (!tile.isVisited() && tile.isExplored()) {
                return tile;
            }
        }

        return null;
    }

    private Tile getTileInDirection(Tile tile, Direction direction) {
        var x = tile.getPosition().getX() + direction.getMoveX();
        var y = tile.getPosition().getY() + direction.getMoveY();

        return environment.getTileMap()[x][y];
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
        var surroundingTiles = environment.getNeighbouringTiles(tile).stream();

        return surroundingTiles.filter((neighborTile) -> !isPassable(neighborTile)).collect(Collectors.toList());
    }

    /**
     * @param tile The tile to check
     * @return A list of tiles adjacent to the given tile which are passable
     */
    private List<Tile> getPassableNeighbors(Tile tile) {
        var neighbors = environment.getNeighbouringTiles(tile);

        return neighbors.stream().filter((this::isPassable)).collect(Collectors.toList());
    }

    /**
     * @param tile The tile to check
     * @return checks if a tile is passable (or visited in the case of the algorithm).
     */
    private boolean isPassable(Tile tile) {
        return !((MemoryTile)tile).isVisited() && tile.getType().isPassable();
    }

    /**
     * @param tile The tile we get the neighboring tiles from
     * @return A list of tiles which are adjacent to the agent and are not marked as explored
     */
    private List<Tile> getUnexploredNeighboringTiles(Tile tile) {
        var surroundingTiles = environment.getNeighbouringTiles(tile).stream();
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
        var surroundingTiles = environment.getNeighbouringTiles(tile).stream();
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
}
