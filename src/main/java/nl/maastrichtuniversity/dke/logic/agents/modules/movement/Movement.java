package nl.maastrichtuniversity.dke.logic.agents.modules.movement;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.agents.util.exceptions.ActionIsNotRotationException;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TeleportTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;


/**
 * move the agent to the desirable position
 *
 * @Author Parand
 */
@Slf4j
public class Movement extends AgentModule implements IMovement {

    private @Getter @Setter double baseSpeed, sprintSpeed;
    private double lastTimeMoved;

    public Movement(Scenario scenario,double baseSpeed, double sprintSpeed) {
        super(scenario);
        this.baseSpeed = baseSpeed;
        this.sprintSpeed = sprintSpeed;
    }

    @Override
    public Direction rotate(Direction currentDirection, MoveAction action, double time) {
        lastTimeMoved = time;
        try {
            checkIfActionIsRotation(action);
            return getNewDirection(currentDirection, action);
        } catch (ActionIsNotRotationException e) {
            log.error(e.getMessage());
            return currentDirection;
        }
    }

    @Override
    public Position goForward(Position position, Direction direction, double time) {
//        if (isTimeToMove(time)) {
        Position newPos = position.add(new Position(direction.getMoveX(), direction.getMoveY()));
        lastTimeMoved = time;


        if (isColliding(newPos)) {
            return position;
        }
        var tileMap = scenario.getEnvironment().get(TileType.TELEPORT);
        for (Tile t : tileMap) {
            if (newPos.equals(t.getPosition())) {
                newPos = ((TeleportTile) t).getTargetPosition();
            }
        }

            return newPos;
//        }
//        return position;
    }

    private boolean isTimeToMove(double time) {
        return time - lastTimeMoved > 1.0/(baseSpeed/10.0);
    }


    private boolean isPathClosed(Position currPosition, Direction direction) {
        int x;
        int y;
        for (int i = 0; i < baseSpeed; i++) {
            x = currPosition.getX() + direction.getMoveX() * i;
            y = currPosition.getY() + direction.getMoveY() * i;
            if (scenario.getEnvironment().getTileMap()[x][y].getType() == TileType.WALL) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Position sprint(Position position, Direction direction) {
        Position newPos = position.add(new Position(direction.getMoveX() * 2, direction.getMoveY() * 2));
        if (isColliding(newPos)) {
            return position;
        }
            return newPos;

    }

    /**
     *  check if there is a collision between agent and an object
     * @param position the position want to be checked
     * @return the area with collision or null if there is no collision
     */
    private boolean isColliding(Position position){
        var tileMap = scenario.getEnvironment().getTileMap();
        Tile tile;

        try {
            tile = tileMap[position.getX()][position.getY()];
        } catch (IndexOutOfBoundsException e) {
            log.info("Cannot move as it is trying to walk off of the map");
            return true;
        }

        return !tile.getType().isPassable();

//  Not working: to be implemented!
//        else {
//            return !tile.isOpened();
//        }
    }

    private Direction getNewDirection(Direction currentDirection, MoveAction action) {
        switch (currentDirection) {
            case NORTH -> { return action == MoveAction.ROTATE_RIGHT ? Direction.EAST : Direction.WEST; }
            case SOUTH -> { return action == MoveAction.ROTATE_RIGHT ? Direction.WEST : Direction.EAST; }
            case EAST ->  { return action == MoveAction.ROTATE_RIGHT ? Direction.SOUTH : Direction.NORTH; }
            case WEST ->  { return action == MoveAction.ROTATE_RIGHT ? Direction.NORTH : Direction.SOUTH; }
            default ->    { return currentDirection; }
        }
    }

    private void checkIfActionIsRotation(MoveAction action) throws ActionIsNotRotationException{
        if (action != MoveAction.ROTATE_LEFT && action != MoveAction.ROTATE_RIGHT) {
            throw new ActionIsNotRotationException();
        }
    }
}



