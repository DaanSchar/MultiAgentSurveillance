package nl.maastrichtuniversity.dke.logic.agents.modules.movement;

import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface IMovementModule {
    Direction rotate(Direction currentDirection, MoveAction rotation);

    Position goForward(Position position, Direction direction);

    Position getForwardPosition(Position position, Direction direction);

    Position sprint(Position position, Direction direction);
}
