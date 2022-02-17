package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.util.Position;
import nl.maastrichtuniversity.dke.util.Vector;

public interface IMovement {
     Direction rotate(Direction direction);
     Position goForward(Position position, Direction direction);
     Position sprint(Position position, Direction direction);
     Position goBackward(Position position, Direction direction);
}
