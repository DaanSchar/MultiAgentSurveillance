package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.util.Vector;

public interface IMovement {
     Vector rotate(Vector direction, double speed);
     Vector goForward(Vector position, Vector direction);
     Vector sprint(Vector position, Vector direction);
     Vector goBackward(Vector position, Vector direction);
}
