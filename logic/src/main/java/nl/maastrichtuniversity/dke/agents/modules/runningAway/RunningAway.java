package nl.maastrichtuniversity.dke.agents.modules.runningAway;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public class RunningAway extends AgentModule implements IRunningAway {


    public RunningAway(Scenario scenario) {
        super(scenario);
    }

    @Override
    public Position avoidGuard(Position guardPosition, Position currentPosition) {
        Position newPos = currentPosition.add(currentPosition.sub(guardPosition));
        if (!scenario.getEnvironment().getAt(newPos).isPassable()) {
            newPos = new Position(newPos.getY(), newPos.getX());
        }
        return newPos;

    }

}
