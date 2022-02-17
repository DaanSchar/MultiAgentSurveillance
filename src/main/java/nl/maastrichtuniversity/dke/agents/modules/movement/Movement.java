package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.TileType;
import nl.maastrichtuniversity.dke.util.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * move the agent to the desirable position
 *
 * @Author Parand
 */
public class Movement extends AgentModule implements IMovement {

    private static final Logger logger = LoggerFactory.getLogger(Movement.class);
    private @Getter @Setter int baseSpeed, sprintSpeed;

    public Movement(Scenario scenario,int baseSpeed, int sprintSpeed) {
        super(scenario);
    }

    @Override
    public Direction rotate(Direction currentDirection, int rotation) {
        if (currentDirection == Direction.NORTH) {
            if (rotation == 1)
                return Direction.EAST;
            else if (rotation == -1)
                return Direction.WEST;
        }

        else if (currentDirection == Direction.EAST) {
            if (rotation == 1)
                return Direction.SOUTH;
            else if (rotation == -1)
                return Direction.NORTH;
        }

        else if (currentDirection == Direction.SOUTH) {
            if (rotation == 1)
                return Direction.WEST;
            else if (rotation == -1)
                return Direction.EAST;
        }

        else if (currentDirection == Direction.WEST) {
                if (rotation == 1)
                    return Direction.NORTH;
                else if (rotation == -1)
                    return Direction.SOUTH;
        }

        return currentDirection;
    }

    @Override
    public Position goForward(Position position, Direction direction) {
        Position newPos = position.add(new Position(direction.getMoveX(), direction.getMoveY()));
        if(checkCollision(newPos)){
            return position;
        }
            return newPos;
    }

    @Override
    public Position sprint(Position position, Direction direction) {
        Position newPos = position.add(new Position(direction.getMoveX() * 2, direction.getMoveY() * 2));
        if (checkCollision(newPos)) {
            return position;
        }
            return newPos;

    }

    @Override
    public Position goBackward (Position position, Direction direction) {
        Position newPos = position.sub(new Position(direction.getMoveX(), direction.getMoveY()));
        if (checkCollision(newPos)) {
            return position;
        }
        return newPos;
    }

    /**
     *  check if there is a collision between agent and an object
     * @param position the position want to be checked
     * @return the area with collision or null if there is no collision
     */
    private boolean checkCollision(Position position){
        var tileMap = scenario.getEnvironment().getTileMap();
        var tile = tileMap[position.getX()][position.getY()];

        if (tile.getType() == TileType.EMPTY) {
            return false;
        } else {
            return !tile.isOpened();
        }
    }
}



