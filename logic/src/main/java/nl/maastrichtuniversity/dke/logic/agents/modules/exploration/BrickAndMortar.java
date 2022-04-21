package nl.maastrichtuniversity.dke.logic.agents.modules.exploration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.modules.movement.IMovementModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.*;

/**
 * implementation of a brick and mortar exploration algorithm.
 * source: Brick & Mortar, http://www.cs.ox.ac.uk/people/niki.trigoni/papers/ICRA2007.pdf
 */
@Slf4j
public class BrickAndMortar implements IExplorationModule {

    private final double randomness = 0.2;

    private final Environment environment;

    private List<Direction> directionPriority;

    private @Getter boolean isDoneExploring;

    private final IMovementModule movementModule;

    private Position currentPosition;
    private Direction currentDirection;

    public BrickAndMortar(Environment environment, IMovementModule movementModule) {
        this.environment = environment;
        this.movementModule = movementModule;
        this.isDoneExploring = false;

        shufflePriorityOfDirections();
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

    private void shufflePriorityOfDirections() {
        this.directionPriority = Direction.getAllDirections();
        Collections.shuffle(directionPriority);
    }

    private void markingStep() {
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

        return MoveAction.ROTATE_LEFT;
    }

    private MoveAction getMoveToBestExploredTile() {
        Tile targetTile = getBestExploredTile();
        Position nextPosition = getNextPosition();

        if (nextPosition.equals(targetTile.getPosition())) {
            return MoveAction.MOVE_FORWARD;
        }

        return MoveAction.ROTATE_LEFT;
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
        if (Math.random() < randomness) {
            Collections.shuffle(unexploredTiles);
        }

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
        return environment.getNeighborsAndFilter(tile, Tile::isPassable);
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

            Tile current;
            while (!queue.isEmpty()) {
                current = queue.poll();
                if (current.equals(goal)) {
                    return true;
                }

                visited.add(current);

                for (Tile neighbour : getPassableNeighbors(current)) {
                    if (neighbour.equals(goal)) {
                        return true;
                    }
                    // not including the tile which the agent is currently on, as we are trying to find a path
                    // between 2 tiles, would the current tile be marked as visited.
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
