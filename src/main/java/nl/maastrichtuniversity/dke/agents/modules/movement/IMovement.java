package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.util.Position;

public interface IMovement {
     Direction rotate(Direction currentDirection, int rotation);
     Position goForward(Position position, Direction direction);
     Position sprint(Position position, Direction direction);
     Position goBackward(Position position, Direction direction);
}
