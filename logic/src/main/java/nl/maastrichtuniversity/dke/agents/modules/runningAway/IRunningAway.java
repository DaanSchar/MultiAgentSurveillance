package nl.maastrichtuniversity.dke.agents.modules.runningAway;

import nl.maastrichtuniversity.dke.scenario.util.Position;

public interface IRunningAway {
    Position avoidGuard(Position guardPosition, Position currentPosition);
}
