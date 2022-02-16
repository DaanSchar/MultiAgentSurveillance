package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.areas.Area;
import nl.maastrichtuniversity.dke.areas.Circle;
import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.util.Vector;

/**
 * move the agent to the desirable position
 *
 * @Author Parand
 */
public class Movement extends AgentModule implements IMovement {

    public Movement(Environment environment) {
        super(environment);
    }

    @Override
    public Vector rotate(Vector direction, double rotationSpeed) {
        return direction.rotate(rotationSpeed);
    }

    @Override
    public Vector goForward(Vector position, Vector direction) {
        if(checkCollision(position.add(direction)) != null){
            return positionCollision(checkCollision(position.add(direction)), position, direction);
        }
        return position.add(direction);
    }

    @Override
    public Vector sprint(Vector position, Vector direction) {
        if(checkCollision(position.add(direction.mul(2))) != null){
            return positionCollision(checkCollision(position.add(direction.mul(2))), position, direction);
        }
        return position.add(direction.mul(2));
    }

    @Override
    public Vector goBackward(Vector position, Vector direction) {
        if(checkCollision(position.add(direction.mul(-1)))!= null){
            return positionCollision(checkCollision(position.add(direction.mul(-1))), position, direction);
        }
        return position.add(direction.mul(-1));
    }

    /**
     *  check if there is a collision between agent and an object
     * @param position
     * @return the area with collision or null if there is no collision
     */
    private Area checkCollision(Vector position ){
//        Circle agent = new Circle(position.getX(), position.getY(), 0.5);
//        for(Area area: scenario.getObjects()){
//            if(area.isCollidingWith(agent)){
//                return area;
//            }
//        }
        return null;
    }

    /**
     * calculate the new position after collision
     * @param area the area agent have collision with
     * @param position position of the agent
     * @param direction direction of agent
     * @return the new position of agent
     */
    private Vector positionCollision(Area area, Vector position, Vector direction ){
        return position;
    }

}
