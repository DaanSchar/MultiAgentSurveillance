package nl.maastrichtuniversity.dke.logic.agents.modules.victory;

import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;

import java.util.List;

public class Victory implements IVictory {
    private Scenario scenario;

    public Victory(Scenario scenario) { this.scenario = scenario; }

    public boolean checkIntruderVictory() {
        return checkTargetArea(scenario.getIntruders());
    }

    public boolean checkGuardVictory() {
        for (Guard guard : scenario.getGuards()) {
            if (guard.getVisionModule().getVisibleAgents().size() > 1) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTargetArea(List<Intruder> agents) {
        List<Tile> tiles= scenario.getEnvironment().get(TileType.TARGET);
        for (Tile tile : tiles) {
            for (Agent agent : agents) {
                if (agent.getPosition().getX() == tile.getPosition().getX()
                        && agent.getPosition().getY() == tile.getPosition().getY()) {
                    return true;
                }
            }
        }
        return false;
    }
}
