package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.areas.Area;
import nl.maastrichtuniversity.dke.areas.Circle;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.StaticEnvironment;
import nl.maastrichtuniversity.dke.util.Vector;

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
        return position.add(direction);
    }

    @Override
    public Vector sprint(Vector position, Vector direction) {
        return position.add(direction.mul(2));
    }

    @Override
    public Vector goBackward(Vector position, Vector direction) {
        return position.add(direction.mul(-1));
    }
}
