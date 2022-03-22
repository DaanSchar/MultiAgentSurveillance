package nl.maastrichtuniversity.dke.logic.agents;

import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;

import java.util.ArrayList;
import java.util.HashSet;
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
        HashSet<Tile> tiles = new HashSet<>();

        for (Agent agent : this) {
            tiles.addAll((agent.getMemoryModule()).getCoveredTiles());
        }

        return tiles.stream().toList();
    }

    public boolean isDone() {
        for (Agent agent : this) {
            if (!agent.isDone()) {
                return false;
            }
        }

        return true;
    }

}
