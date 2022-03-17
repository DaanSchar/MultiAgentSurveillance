package nl.maastrichtuniversity.dke.reinforcement;


import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static nl.maastrichtuniversity.dke.logic.scenario.environment.TileType.EMPTY;

public class Reward {
    //1 EMPTY tile
    //2 everything else

    /**
     * compare previous map of individual agent to new map, for each extra known tile add a point
     *
     * @return the reward from each agent
     */
    public static double calculateReward(Scenario scenario) {
        List<Tile> allDiscoveredTiles = new LinkedList<>();
        for(Agent a:scenario.getGuards()){
            allDiscoveredTiles.addAll(a.getMemoryModule().getDiscoveredTiles());
        }

        HashSet<Tile> uniqueTiles = new HashSet<Tile>(allDiscoveredTiles);
        double duplicates = allDiscoveredTiles.size()-uniqueTiles.size();

        double reward = 0;

        for (Agent a : scenario.getGuards()) {
            for (Tile t : a.getMemoryModule().getDiscoveredTiles()) {
                if (t.getType() == EMPTY) {
                    reward++;
                } else reward += 2;
            }
        }

        return reward - duplicates/2;
    }


}
