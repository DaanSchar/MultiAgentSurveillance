package nl.maastrichtuniversity.dke.agents;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Collection of Agents.
 *
 * @param <T> type of agent
 */
@Slf4j
public class Fleet<T extends Agent> extends ArrayList<T> {

    public Fleet() {
    }

    /**
     * @return List of tiles covered/explored by the fleet.
     */
    public List<Tile> getCoveredTiles() {
        HashSet<Tile> tiles = new HashSet<>();

        for (Agent agent : this) {
            tiles.addAll((agent.getMemoryModule()).getCoveredTiles());
        }

        return new ArrayList<>(tiles);
    }

    /**
     * @return environment explored by all agents.
     */
    public Environment getMemoryMap() {
        List<Environment> memories = new ArrayList<>();
        for (Agent agent : this) {
            memories.add(agent.getMemoryModule().getMap());
        }

        Tile[][] tileMap = new Tile[memories.get(0).getWidth()][memories.get(0).getHeight()];

        for (int i = 0; i < memories.get(0).getHeight(); i++) {
            for (int j = 0; j < memories.get(0).getWidth(); j++) {
                for (Environment environment : memories) {
                    boolean isUnknown = environment.getTileMap()[j][i].getType().equals(TileType.UNKNOWN);
                    if (!isUnknown) {
                        tileMap[j][i] = environment.getTileMap()[j][i];
                        break;
                    }
                    tileMap[j][i] = new Tile(new Position(j, i), TileType.UNKNOWN);
                }
            }
        }

        return new Environment(tileMap.length, tileMap[0].length, tileMap);
    }

    public T getAt(Position position) {
        for (T agent : this) {
            if (agent.getPosition().equals(position)) {
                return agent;
            }
        }

        return null;
    }

    @Override
    public T get(int index) {
        T agent = super.get(index);

        if (agent instanceof Intruder) {
            return checkIfIsCaughtAndGet(agent);
        }

        return agent;
    }

    private T checkIfIsCaughtAndGet(T agent) {
        Intruder intruder = (Intruder) agent;

        if (intruder.isCaught()) {
            return null;
        }

        return (T) intruder;
    }
}
