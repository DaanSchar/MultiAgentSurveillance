package nl.maastrichtuniversity.dke.logic.agents.modules.movement;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
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
        this.baseSpeed = baseSpeed;
        this.sprintSpeed = sprintSpeed;

    }

    // 1 is left
    //-1 is right
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
        Position newPos = position.add(new Position(
                (int)(direction.getMoveX() * baseSpeed * scenario.getTimeStep()),
                (int)(direction.getMoveY() * baseSpeed * scenario.getTimeStep()))
        );
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
        Position newPos = position.sub(new Position(
                (int)(direction.getMoveX() * baseSpeed * scenario.getTimeStep()),
                (int)(direction.getMoveY() * baseSpeed * scenario.getTimeStep())
        ));

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
        Tile tile;

        try {
            tile = tileMap[position.getX()][position.getY()];
        } catch (IndexOutOfBoundsException e) {
            logger.info("Cannot move as it is trying to walk off of the map");
            return true;
        }

        if (tile.getType() == TileType.EMPTY) {
            return false;
        } else {
            return !tile.isOpened();
        }
    }
}



