package nl.maastrichtuniversity.dke.agents.modules.exploration;

import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public interface IExplorationModule {

    MoveAction explore(Position currentPosition, Direction currentDirection);

    boolean isDoneExploring();

    void reset();

}
