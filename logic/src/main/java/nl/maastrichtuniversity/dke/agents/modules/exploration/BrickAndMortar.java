package nl.maastrichtuniversity.dke.agents.modules.exploration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovementModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.*;

/**
 * implementation of a brick and mortar exploration algorithm.
 * source: Brick & Mortar, http://www.cs.ox.ac.uk/people/niki.trigoni/papers/ICRA2007.pdf
 */
@Slf4j
public class BrickAndMortar implements IExplorationModule {

    private final double randomness = 0.2;

    private final Environment environment;

    private final List<Direction> directionPriority;

    private @Getter boolean isDoneExploring;

    private final IMovementModule movementModule;

    private Position currentPosition;
    private Direction currentDirection;

    public BrickAndMortar(Environment environment, IMovementModule movementModule) {
        this.environment = environment;
        this.movementModule = movementModule;
        this.isDoneExploring = false;
        this.directionPriority = Direction.getAllDirections();
        Collections.shuffle(directionPriority);
    }

    public void reset() {
        isDoneExploring = false;

        for (Tile tile : environment) {
            MemoryTile memoryTile = (MemoryTile) tile;
            memoryTile.setVisited(false);
            memoryTile.setExplored(false);
        }
    }

    @Override
    public MoveAction explore(Position currentPosition, Direction currentDirection) {
        this.currentPosition = currentPosition;
        this.currentDirection = currentDirection;

        if (isDoneExploring) {
            return MoveAction.STAND_STILL;
        }

        markingStep();
        return navigationStep();
    }

    private void markingStep() {
        if (Math.random() < randomness) {
            Collections.shuffle(directionPriority);
        }

        if (!currentCellBlocksPath()) {
            getCurrentTile().setVisited(true);
        }

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
            nextMove = MoveAction.STAND_STILL;
        }

        return nextMove;
    }

    private boolean currentCellBlocksPath() {
        List<Tile> neighbours = getPassableNeighbors(getCurrentTile());

        for (int i = 0; i < neighbours.size(); i++) {
            for (int j = i; j < neighbours.size(); j++) {
                if (!pathExists(neighbours.get(i), neighbours.get(j))) {
                    return true;
                }
            }
        }

        return false;
    }

    private MoveAction getMoveToBestUnexploredTile() {
        MemoryTile targetTile = getBestUnexploredTile();
        Position nextPosition = getNextPosition();

        boolean targetTileAhead = nextPosition.equals(targetTile.getPosition());

        if (targetTileAhead || targetTile.getType() == TileType.TELEPORT) {
            return MoveAction.MOVE_FORWARD;
        }

        Position leftOf = currentPosition.getPosInDirection(currentDirection.leftOf());
        boolean targetTileLeftOf = leftOf.equals(targetTile.getPosition());

        if (targetTileLeftOf) {
            return MoveAction.ROTATE_LEFT;
        }

        return MoveAction.ROTATE_RIGHT;
    }

    private MoveAction getMoveToBestExploredTile() {
        Tile targetTile = getBestExploredTile();
        Position nextPosition = getNextPosition();

        boolean targetTileAhead = nextPosition.equals(targetTile.getPosition());

        if (targetTileAhead) {
            return MoveAction.MOVE_FORWARD;
        }

        Position leftOf = currentPosition.getPosInDirection(currentDirection.leftOf());
        boolean targetTileLeftOf = leftOf.equals(targetTile.getPosition());

        if (targetTileLeftOf) {
            return MoveAction.ROTATE_LEFT;
        }

        return MoveAction.ROTATE_RIGHT;
    }

    private Position getNextPosition() {
        return movementModule.getForwardPosition(currentPosition, currentDirection);
    }

    private MemoryTile getCurrentTile() {
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        return (MemoryTile) environment.getTileMap()[x][y];
    }

    private MemoryTile getBestUnexploredTile() {
        List<Tile> unexploredTiles = getUnexploredNeighboringTiles(getCurrentTile());

        // we shuffle the tiles, otherwise it will always return the tile from
        // the same direction if all/most tiles are considered equally good
//        if (Math.random() < randomness) {
            Collections.shuffle(unexploredTiles);
//        }

        unexploredTiles.sort((t1, t2) -> getNonPassableNeighbors(t2).size() - getNonPassableNeighbors(t1).size());

        return (MemoryTile) unexploredTiles.get(0);
    }

    private Tile getBestExploredTile() {
        for (Direction direction : directionPriority) {
            MemoryTile tile = (MemoryTile) getFacingTile(getCurrentTile(), direction);

            if (tile.isExplored() && tile.isPassable()) {
                return tile;
            }
        }

        return getCurrentTile();
    }

    private Tile getFacingTile(Tile tile, Direction direction) {
        int x = tile.getPosition().getX() + direction.getMoveX();
        int y = tile.getPosition().getY() + direction.getMoveY();

        return environment.getTileMap()[x][y];
    }

    private boolean hasNeighboringExploredTiles() {
        return getExploredNeighboringTiles(getCurrentTile()).size() > 0;
    }

    private boolean hasNeighboringUnexploredTiles() {
        return getUnexploredNeighboringTiles(getCurrentTile()).size() > 0;
    }

    private List<Tile> getNonPassableNeighbors(Tile tile) {
        return environment.getNeighborsAndFilter(tile, (neighbor) -> !neighbor.isPassable());
    }

    private List<Tile> getPassableNeighbors(Tile tile) {
        return environment.getNeighborsAndFilter(tile,
                t -> t.isPassable() || t.getType() == TileType.DOOR || t.getType() == TileType.WINDOW
        );
    }

    private List<Tile> getUnexploredNeighboringTiles(Tile tile) {
        return environment.getNeighborsAndFilter(tile,
                (neighbor) -> !((MemoryTile) neighbor).isExplored() && neighbor.isPassable()
        );
    }

    private List<Tile> getExploredNeighboringTiles(Tile tile) {
        return environment.getNeighborsAndFilter(tile,
                neighbor -> ((MemoryTile) neighbor).isExplored() && neighbor.isPassable()
        );
    }

    private boolean pathExists(Tile start, Tile end) {
        return new BFS().pathExists(start, end);
    }

    class BFS {

        private Queue<Tile> queue;
        private List<Tile> visited;

        BFS() {
            this.queue = new LinkedList<>();
            this.visited = new ArrayList<>();
        }

        boolean pathExists(Tile start, Tile goal) {
            this.queue = new LinkedList<>();
            this.visited = new ArrayList<>();
            queue.add(start);

            return foundPathToGoal(goal);
        }

        private boolean foundPathToGoal(Tile goal) {
            while (!queue.isEmpty()) {
                Tile current = queue.poll();

                if (current.equals(goal)) {
                    return true;
                }

                visited.add(current);

                for (Tile neighbour : getPassableNeighbors(current)) {
                    if (neighbour.equals(goal)) {
                        return true;
                    }

                    addUnvisitedTiles(neighbour);
                }
            }

            return false;
        }

        private void addUnvisitedTiles(Tile tile) {
            if (!tileIsVisited(tile) && !tile.equals(getCurrentTile())) {
                queue.add(tile);
                visited.add(tile);
            }
        }

        private boolean tileIsVisited(Tile tile) {
            return visited.contains(tile);
        }

    }
}
