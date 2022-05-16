package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.agents.modules.memory.IMemoryModule;
import nl.maastrichtuniversity.dke.agents.modules.memory.MemoryModule;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovementModule;
import nl.maastrichtuniversity.dke.agents.modules.pathfind.PathFinderModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
public class Path {

    private final @Getter Position finalDestination;
    private final PathFinderModule pathFinderModule;
    private final IMovementModule movementModule;
    private final IMemoryModule memoryModule;
    private final @Getter Queue<Position> path;

    public Path(Position currentPosition, Position finalDestination,
                PathFinderModule pathFinderModule, IMovementModule movementModule, IMemoryModule memoryModule) {
        this.finalDestination = finalDestination;
        this.pathFinderModule = pathFinderModule;
        this.movementModule = movementModule;
        this.memoryModule = memoryModule;
        this.path = calculatePath(currentPosition, finalDestination);
    }

    /**
     * Determines the next move action based on the current position and the next position in the path.
     *
     * @param currentPosition  position of the agent
     * @param currentDirection direction of the agent
     * @return next move action
     */
    public MoveAction getNextMove(Position currentPosition, Direction currentDirection) {
        if (path.isEmpty()) {
            return MoveAction.STAND_STILL;
        }

        return moveToNextPositionInRoute(currentPosition, currentDirection);
    }

    private MoveAction moveToNextPositionInRoute(Position currentPosition, Direction currentDirection) {
        Position nextPosition = path.peek();

        if (nextPosition.equals(currentPosition)) {
            path.poll();
            nextPosition = path.peek();
        }

        if (nextPosition == null) {
            return MoveAction.STAND_STILL;
        }

        MoveAction nextMove = determineMoveAction(currentPosition, nextPosition, currentDirection);

        if (nextMove == null) {
            path.clear();
            path.addAll(calculatePath(currentPosition, finalDestination));
            return moveToNextPositionInRoute(currentPosition, currentDirection);
        }

        return nextMove;
    }

    private MoveAction determineMoveAction(Position currentPosition, Position nextPosition,
                                           Direction currentDirection) {
        Position facingPosition = getPositionInDirection(currentPosition, currentDirection);
        Position leftFacingPosition = getPositionInDirection(currentPosition, currentDirection.leftOf());
        Position rightFacingPosition = getPositionInDirection(currentPosition, currentDirection.rightOf());
        Position backPosition = getPositionInDirection(currentPosition, currentDirection.opposite());

        if (nextPosition.equals(facingPosition)) {
            path.poll();
            return MoveAction.MOVE_FORWARD;
        } else if (nextPosition.equals(leftFacingPosition)) {
            return MoveAction.ROTATE_LEFT;
        } else if (nextPosition.equals(rightFacingPosition)) {
            return MoveAction.ROTATE_RIGHT;
        } else if (nextPosition.equals(backPosition)) {
            return MoveAction.ROTATE_RIGHT;
        } else {

            if (tileAt(nextPosition).getType() == TileType.DOOR) {
                return MoveAction.TOGGLE_DOOR;
            }
            if (tileAt(nextPosition).getType() == TileType.WINDOW) {
                return MoveAction.BREAK_WINDOW;
            }

            return null;
        }
    }

    private Position getPositionInDirection(Position currentPosition, Direction direction) {
        return movementModule.getForwardPosition(currentPosition, direction);
    }

    private Tile tileAt(Position position) {
        return memoryModule.getMap().getAt(position);
    }

    private Queue<Position> calculatePath(Position currentPosition, Position finalDestination) {
        List<Position> path = pathFinderModule.getShortestPath(currentPosition, finalDestination);
        return new LinkedList<>(path);
    }

}
