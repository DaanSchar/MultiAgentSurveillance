package nl.maastrichtuniversity.dke.agents.modules.pathfind;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.ActionTimer;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
public class PathNavigator {

    private final @Getter Position finalDestination;
    private final PathFinderModule pathFinderModule;
    private final @Getter Queue<Position> path;
    private final Environment environment;
    private final ActionTimer actionTimer;

    public PathNavigator(Position currentPosition, Position finalDestination, PathFinderModule pathFinderModule, ActionTimer actionTimer) {
        this.finalDestination = finalDestination;
        this.pathFinderModule = pathFinderModule;
        this.actionTimer = actionTimer;
        this.path = calculatePath(currentPosition, finalDestination);
        this.environment = pathFinderModule.getEnvironment();
    }

    /**
     * Determines the next MoveAction based on the current position and the next position in the path.
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

    private MoveAction determineMoveAction(Position position, Position nextPosition, Direction direction) {
        Position frontFacingPosition = getPositionInDirection(position, direction);
        Position leftFacingPosition = getPositionInDirection(position, direction.leftOf());
        Position rightFacingPosition = getPositionInDirection(position, direction.rightOf());
        Position backPosition = getPositionInDirection(position, direction.opposite());

        if (nextPosition.equals(frontFacingPosition)) {

            if (closedDoorAt(frontFacingPosition)) {
                return MoveAction.TOGGLE_DOOR;
            }

            if (closedWindowAt(frontFacingPosition)) {
                return MoveAction.BREAK_WINDOW;
            }

            // path.poll();
            return MoveAction.MOVE_FORWARD;
        } else if (nextPosition.equals(leftFacingPosition)) {
            return MoveAction.ROTATE_LEFT;
        } else if (nextPosition.equals(rightFacingPosition)) {
            return MoveAction.ROTATE_RIGHT;
        } else if (nextPosition.equals(backPosition)) {
            return MoveAction.ROTATE_RIGHT;
        } else {
            return null;
        }
    }

    private boolean closedDoorAt(Position position) {
        Tile tile = environment.getAt(position);

        if (tile.getType() == TileType.DOOR) {
            return !tile.isPassable();
        }

        return false;
    }

    private boolean closedWindowAt(Position position) {
        Tile tile = environment.getAt(position);

        if (tile.getType() == TileType.WINDOW) {
            return !tile.isPassable();
        }

        return false;
    }

    private Position getPositionInDirection(Position currentPosition, Direction direction) {
        return currentPosition.getPosInDirection(direction);
    }

    private Queue<Position> calculatePath(Position currentPosition, Position finalDestination) {
        List<Position> path = pathFinderModule.getShortestPath(currentPosition, finalDestination);
        return new LinkedList<>(path);
    }

}
