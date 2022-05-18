package nl.maastrichtuniversity.dke.agents.modules.runningAway;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public class runningAway extends AgentModule implements IRunningAway {


    public runningAway(Scenario scenario) {
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
