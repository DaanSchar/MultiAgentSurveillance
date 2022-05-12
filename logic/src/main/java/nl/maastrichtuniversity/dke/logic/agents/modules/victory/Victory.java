package nl.maastrichtuniversity.dke.logic.agents.modules.victory;

import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;

import java.util.List;

public class Victory implements IVictory {
    private Scenario scenario;

    public Victory(Scenario scenario) {
        this.scenario = scenario;
    }

    //Intruders win when at least one intruder goes into the target area
    public boolean checkIntruderVictory() {
        return checkTargetArea(scenario.getIntruders());
    }

    // Guards win whenever every intruder is caugth
    public boolean checkGuardVictory() {
        boolean victory = false;
        for (Intruder intruder : scenario.getIntruders()) {
            if (!intruder.isAlive()) {
                victory = true;
            } else {
                victory = false;
                break;
            }
        }
        return victory;
    }

    public boolean checkTargetArea(List<Intruder> agents) {
        List<Tile> tiles = scenario.getEnvironment().get(TileType.TARGET);
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
