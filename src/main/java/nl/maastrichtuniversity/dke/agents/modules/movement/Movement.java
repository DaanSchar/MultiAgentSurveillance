package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.areas.Area;
import nl.maastrichtuniversity.dke.areas.Circle;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.StaticEnvironment;
import nl.maastrichtuniversity.dke.util.Position;
import nl.maastrichtuniversity.dke.util.Vector;

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
//        if(checkCollision(position.add(direction)) != null){
//            return positionCollision(checkCollision(position.add(direction)), position, direction);
//        }
        return position.add(new Position(direction.getMoveX(), direction.getMoveY()));
    }

    @Override
    public Position sprint(Position position, Direction direction) {
//        if(checkCollision(position.add(direction.mul(2))) != null){
//            return positionCollision(checkCollision(position.add(direction.mul(2))), position, direction);
//        }
        return position.add(new Position(direction.getMoveX() * 2, direction.getMoveY() * 2));
    }

    @Override
    public Position goBackward(Position position, Direction direction) {
//        if(checkCollision(position.add(direction.mul(-1)))!= null){
//            return positionCollision(checkCollision(position.add(direction.mul(-1))), position, direction);
//        }
        return position.sub(new Position(direction.getMoveX(), direction.getMoveY()));
    }

    /**
     *  check if there is a collision between agent and an object
     * @param position
     * @return the area with collision or null if there is no collision
     */
    private Area checkCollision(Position position ){
        Circle agent = new Circle(position.getX(), position.getY(), 0.5);
        for(Area area: scenario.getObjects()){
            if(area.isCollidingWith(agent)){
                return area;
            }
        }
        return null;
    }

    /**
     * calculate the new position after collision
     * @param area the area agent have collision with
     * @param position position of the agent
     * @param direction direction of agent
     * @return the new position of agent
     */
    private Position positionCollision(Area area, Position position, Direction direction ){
        return position;
    }

}
