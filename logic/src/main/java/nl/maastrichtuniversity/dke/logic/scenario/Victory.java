package nl.maastrichtuniversity.dke.logic.scenario;

import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;

import java.util.List;

public class Victory {
    private Scenario scenario;

    public Victory(Scenario scenario) {
        this.scenario = scenario;
    }

    //-The intruder wins if he is 3 seconds in any of the target
    // areas or visits the target area twice with a time difference
    // of at least 3 seconds
    public boolean checkIntruerVectory(int gamemode) {
        return checkTargetArea(scenario.getIntruders(), gamemode);
    }

    //-The guards win if the intruder is no more than
    // 0.5 meter away and in sight.
    public boolean checkGuardVectory() {
        for (Guard guard : scenario.getGuards()) {
            if (guard.getVisionModule().getVisibleAgents().size() > 1) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTargetArea(List<Intruder> agents, int gamemode) {

        List<Tile> tiles = scenario.getEnvironment().get(TileType.TARGET);
        int countIntrudersFoundTarget = 0;
        for (Tile tile : tiles) {
            for (Agent agent : agents) {
                if (agentStandingOnTile(agent, tile)) {
                    countIntrudersFoundTarget++;
                }
            }
            if (gamemode == 1 && countIntrudersFoundTarget == agents.size()) {
                return true;
            }
            if (gamemode == 0 && countIntrudersFoundTarget > 0) {
                return true;
            }
        }

        return false;

    }

    public boolean agentStandingOnTile(Agent agent, Tile tile) {
        return agent.getPosition().getX() == tile.getPosition().getX() &&
                agent.getPosition().getY() == tile.getPosition().getY();
    }


}
