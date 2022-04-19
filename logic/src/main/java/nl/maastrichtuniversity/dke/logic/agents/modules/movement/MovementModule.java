package nl.maastrichtuniversity.dke.logic.agents.modules.movement;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.agents.util.exceptions.ActionIsNotRotationException;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TeleportTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

@Slf4j
public class MovementModule extends AgentModule implements IMovementModule {

    private @Getter @Setter double baseSpeed, sprintSpeed;
    private MovesTimeTracker timeTracker;

    public MovementModule(Scenario scenario, double baseSpeed, double sprintSpeed) {
        super(scenario);
        this.baseSpeed = baseSpeed;
        this.sprintSpeed = sprintSpeed;
        this.timeTracker = new MovesTimeTracker(0);// TODO: add timestepsize here
    }

    @Override
    public Direction rotate(Direction currentDirection, MoveAction action, double time) {
        try {
            checkIfActionIsRotation(action);
            return getNewRotatedDirection(currentDirection, action);
        } catch (ActionIsNotRotationException e) {
            log.error(e.getMessage());
            return currentDirection;
        }
    }

    @Override
    public Position goForward(Position currentPosition, Direction direction, double time) {
        Position facingPosition = getFacingPosition(currentPosition, direction);

        if (!agentCanMoveTo(facingPosition)) {
            return currentPosition;
        }
        if (!timeTracker.canPerformAction(baseSpeed)) {
            return currentPosition;
        }
        if (isTeleportTile(facingPosition)) {
            return getTeleportDestination(facingPosition);
        }

        return facingPosition;
    }

    @Override
    public Position sprint(Position position, Direction direction) {
        Position newPos = position.add(new Position(direction.getMoveX() * 2, direction.getMoveY() * 2));
        if (agentCanMoveTo(newPos)) {
            return newPos;
        }
        return position;
    }

    private boolean agentCanMoveTo(Position position) {
        if (!positionIsInMap(position)) {
            return false;
        }
        Tile tile = getTileAt(position);

        return tile.isPassable();
    }

    private boolean isTeleportTile(Position position) {
        Tile tile = getTileAt(position);
        return tile.getType() == TileType.TELEPORT;
    }

    private Position getTeleportDestination(Position position) {
        Tile tile = getTileAt(position);
        return ((TeleportTile) tile).getTargetPosition();
    }

    private Position getFacingPosition(Position position, Direction direction) {
        return position.add(new Position(direction.getMoveX(), direction.getMoveY()));
    }

    private boolean positionIsInMap(Position position) {
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        boolean withinXBound = position.getX() >= 0 && position.getX() < tileMap.length;
        boolean withinYBound = position.getY() >= 0 && position.getY() < tileMap[0].length;

        return withinXBound && withinYBound;
    }

    private Tile getTileAt(Position position) {
        return scenario.getEnvironment().getTileMap()[position.getX()][position.getY()];
    }

    private void checkIfActionIsRotation(MoveAction action) throws ActionIsNotRotationException {
        if (action != MoveAction.ROTATE_LEFT && action != MoveAction.ROTATE_RIGHT) {
            throw new ActionIsNotRotationException();
        }
    }

    private Direction getNewRotatedDirection(Direction currentDirection, MoveAction action) {
        switch (currentDirection) {
            case NORTH -> {
                return action == MoveAction.ROTATE_RIGHT ? Direction.EAST : Direction.WEST;
            }
            case SOUTH -> {
                return action == MoveAction.ROTATE_RIGHT ? Direction.WEST : Direction.EAST;
            }
            case EAST -> {
                return action == MoveAction.ROTATE_RIGHT ? Direction.SOUTH : Direction.NORTH;
            }
            case WEST -> {
                return action == MoveAction.ROTATE_RIGHT ? Direction.NORTH : Direction.SOUTH;
            }
            default -> {
                return currentDirection;
            }
        }
    }
}

class MovesTimeTracker {

    private double lastTimeMoved;
    private final double timeStepSize;

    public MovesTimeTracker(double timeStepSize) {
        this.timeStepSize = timeStepSize;
    }

    public boolean canPerformAction(double speed) {
        int currentTimeStep = Game.getInstance().getCurrentTimeStep();
        double currentTime = currentTimeStep * timeStepSize;
        double timeSinceLastTimeMoved = currentTime - lastTimeMoved;

        return timeSinceLastTimeMoved >= (1 / speed);
    }

}



