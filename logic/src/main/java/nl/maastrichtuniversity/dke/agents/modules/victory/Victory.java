package nl.maastrichtuniversity.dke.agents.modules.victory;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;

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

    // Guards win whenever every intruder is caught
    public boolean checkGuardVictory() {
        for (Intruder intruder : scenario.getIntruders()) {
            if (!intruder.isCaught()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkTargetArea(List<Intruder> agents) {
//        List<Tile> tiles = scenario.getEnvironment().get(TileType.TARGET);
//        for (Tile tile : tiles) {
//            for (Agent agent : agents) {
//                if (agent.getPosition().getX() == tile.getPosition().getX()
//                        && agent.getPosition().getY() == tile.getPosition().getY()) {
//                    return true;
//                }
//            }
//        }
        return false;
    }
}
