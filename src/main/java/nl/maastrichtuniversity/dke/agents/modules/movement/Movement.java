package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.util.Vector;

public class Movement implements IMovement {
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
