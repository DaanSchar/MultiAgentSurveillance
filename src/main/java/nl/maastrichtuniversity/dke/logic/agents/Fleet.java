package nl.maastrichtuniversity.dke.logic.agents;

import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of Agents
 *
 * @param <T> type of agent
 */
public class Fleet<T extends Agent> extends ArrayList<T> {

    public Fleet() {}

    /**
     * @return List of tiles covered/explored by the fleet.
     */
    public List<Tile> getCoveredTiles() {
        ArrayList<Tile> coveredTiles = new ArrayList<>();

        for (Agent agent : this) {
            coveredTiles.addAll((agent.getMemoryModule()).getCoveredTiles());
        }

        return coveredTiles;
    }

}
