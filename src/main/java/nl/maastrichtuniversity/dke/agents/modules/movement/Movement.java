package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.TileType;
import nl.maastrichtuniversity.dke.util.Position;

/**
 * move the agent to the desirable position
 *
 * @Author Parand
 */
public class Movement extends AgentModule implements IMovement {

    public Movement(Scenario scenario) {
        super(scenario);
    }

    @Override
    public Direction rotate(Direction direction) {
        return direction; //need work
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
        if (scenario.getEnvironment().getTileMap()[position.getX()][position.getY()].getType() == TileType.EMPTY) {
            return false;
        }
        return true;
    }
}



