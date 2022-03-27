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

@Slf4j
public class BrickAndMortar implements IExplorationModule {

    private final double randomness = 0.2;

    private final Environment environment;

    private List<Direction> directionPriority;

    private @Getter boolean isDoneExploring;

    private final IMovement movementModule;

    private Position currentPosition;
    private Direction currentDirection;

    public BrickAndMortar(Environment environment, IMovement movementModule) {
        this.environment = environment;
        this.movementModule = movementModule;
        this.isDoneExploring = false;

        shufflePriorityOfDirections();
    }

    @Override
    public MoveAction explore(Position currentPosition, Direction currentDirection) {
        this.currentPosition = currentPosition;
        this.currentDirection = currentDirection;

        if (isDoneExploring) return MoveAction.DO_NOTHING;

        markingStep();
        return navigationStep();
    }

    private void shufflePriorityOfDirections() {
        this.directionPriority = Direction.getAllDirections();
        Collections.shuffle(directionPriority);
    }

    private void markingStep() {
        if (!currentCellBlocksPath()) { getCurrentTile().setVisited(true);}

        getCurrentTile().setExplored(true);
    }

    private MoveAction navigationStep() {
        MoveAction nextMove;

        if (hasNeighboringUnexploredTiles()) {
            nextMove = getMoveToBestUnexploredTile();
        } else if (hasNeighboringExploredTiles()) {
            nextMove = getMoveToBestExploredTile();
        } else {
            isDoneExploring = true;
            nextMove = MoveAction.DO_NOTHING;
        }

        return nextMove;
    }

    /**
     * @return true if the there exists no path between all explored/unexplored tiles would
     *         the agent mark this tile as visited.
     */
    private boolean currentCellBlocksPath() {
        List<Tile> neighbours = getPassableNeighbors(getCurrentTile());

        for (int i = 0; i < neighbours.size(); i++) {
            for (int j = i; j < neighbours.size(); j++) {
                if (!pathExists(neighbours.get(i), neighbours.get(j))) { return true; }
            }
        }

        return false;
    }

    /**
     * @return the move the agent should make to get to the best unexplored tile
     */
    private MoveAction getMoveToBestUnexploredTile() {
        MemoryTile targetTile = getBestUnexploredTile();
        Position nextPosition = movementModule.goForward(currentPosition, currentDirection, Game.getInstance().getTime());

        boolean targetTileAhead = nextPosition.equals(targetTile.getPosition());

        if (targetTileAhead || targetTile.getType() == TileType.TELEPORT) return MoveAction.MOVE_FORWARD;

        return MoveAction.ROTATE_LEFT;
    }

    /**
     * @return the move the agent should make to get to the best explored tile
     */
    private MoveAction getMoveToBestExploredTile() {
        Tile targetTile = getBestExploredTile();
        Position nextPosition = movementModule.goForward(currentPosition, currentDirection, Game.getInstance().getTime());

        if (nextPosition.equals(targetTile.getPosition())) { return MoveAction.MOVE_FORWARD; }

        return MoveAction.ROTATE_LEFT;
    }

    /**
     * @return the tile which the agent is currently standing on
     */
    private MemoryTile getCurrentTile() {
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        return (MemoryTile) environment.getTileMap()[x][y];
    }

    /**
     * @return The MemoryTile adjacent to the agent which is most
     * likely to be marked as visited in the marking step
     *
     * the best tile is the tile which has the most adjacent non-passable tiles.
     */
    private MemoryTile getBestUnexploredTile() {
        List<Tile> unexploredTiles = getUnexploredNeighboringTiles(getCurrentTile());

        if (Math.random() < randomness) { Collections.shuffle(unexploredTiles); }

        unexploredTiles.sort((t1 , t2) -> getNonPassableNeighbors(t2).size() - getNonPassableNeighbors(t1).size());

        return (MemoryTile) unexploredTiles.get(0);
    }

    /**
     * @return The best tile considered as explored, where best
     * means it will choose the first tile considered explored according to the
     * direction priority.
     */
    private Tile getBestExploredTile() {
        for (Direction direction : directionPriority) {
            MemoryTile tile = (MemoryTile) getFacingTile(getCurrentTile(), direction);

            if (tile.isExplored() && tile.isPassable()) { return tile; }
        }

        return getCurrentTile();
    }

    /**
     * @return The Tile you would move to if you were to move forward
     */
    private Tile getFacingTile(Tile tile, Direction direction) {
        int x = tile.getPosition().getX() + direction.getMoveX();
        int y = tile.getPosition().getY() + direction.getMoveY();

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
        return environment.getNeighborsAndFilter(tile, (neighbor) -> !neighbor.isPassable());
    }

    /**
     * @param tile The tile to check
     * @return A list of tiles adjacent to the given tile which are passable
     */
    private List<Tile> getPassableNeighbors(Tile tile) {
        return environment.getNeighborsAndFilter(tile, Tile::isPassable);
    }

    /**
     * @param tile The tile we get the neighboring tiles from
     * @return A list of tiles which are adjacent to the agent and are not marked as explored
     */
    private List<Tile> getUnexploredNeighboringTiles(Tile tile) {
        return environment.getNeighborsAndFilter(tile, (neighbor) -> !((MemoryTile)neighbor).isExplored() && neighbor.isPassable());
    }

    /**
     * @param tile The tile we get the neighboring tiles from
     * @return A list of tiles which are adjacent to the agent and are marked as explored
     */
    private List<Tile> getExploredNeighboringTiles(Tile tile) {
        return environment.getNeighborsAndFilter(tile, neighbor -> ((MemoryTile)neighbor).isExplored() && neighbor.isPassable());
    }

    /**
     * @return whether there is an existing path between two tiles.
     */
    private boolean pathExists(Tile start, Tile end) {
        return new BFS().search(start, end);
    }

    /**
     * Breadth-first search
     */
    class BFS {

        private Queue<Tile> queue;
        private List<Tile> visited;

        public BFS() {
            this.queue = new LinkedList<>();
            this.visited = new ArrayList<>();
        }

        /**
         * @return true if there exists a path between the two tiles
         */
        public boolean search(Tile start, Tile goal) {
            this.queue = new LinkedList<>();
            this.visited = new ArrayList<>();
            queue.add(start);

            Tile current;
            while (!queue.isEmpty()) {
                current = queue.poll();
                if (current.equals(goal)) { return true; }

                visited.add(current);

                for (Tile neighbour : getPassableNeighbors(current)) {
                    if (neighbour.equals(goal)) { return true; }
                    // not including the tile which the agent is currently on
                    if (!visited.contains(neighbour) && !neighbour.equals(getCurrentTile())) {
                        queue.add(neighbour);
                        visited.add(neighbour);
                    }

                }
            }
            return false;
        }

    }
}
