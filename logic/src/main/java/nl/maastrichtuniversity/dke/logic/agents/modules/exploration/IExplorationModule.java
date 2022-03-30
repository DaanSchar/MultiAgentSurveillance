package main.java.nl.maastrichtuniversity.dke.logic.agents.modules.exploration;

import main.java.nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import main.java.nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface IExplorationModule {

    MoveAction explore(Position currentPosition, Direction currentDirection);

    boolean isDoneExploring();

}
