package main.java.nl.maastrichtuniversity.dke.logic.agents.modules.movement;

import main.java.nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import main.java.nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface IMovementModule {
     Direction rotate(Direction currentDirection, MoveAction rotation, double time);
     Position goForward(Position position, Direction direction, double time);

     @Deprecated
     Position sprint(Position position, Direction direction);
}
