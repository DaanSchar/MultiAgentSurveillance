package nl.maastrichtuniversity.dke.agents.modules.movement;

import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public interface IMovementModule {
    Direction rotate(Direction currentDirection, MoveAction rotation);

    Position goForward(Position position, Direction direction);

    Position getForwardPosition(Position position, Direction direction);

    Position sprint(Position position, Direction direction);

    boolean agentCanMoveTo(Position position);
}