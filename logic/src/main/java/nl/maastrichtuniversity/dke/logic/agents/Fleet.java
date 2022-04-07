package nl.maastrichtuniversity.dke.logic.agents;

import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Collection of Agents
 *
 * @param <T> type of agent
 */
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
     * @return environment explored by all agents
     */
    public Environment getMemoryMap(){
        List<Environment> memories = new ArrayList<>();
        for (Agent agent : this) {
            memories.add(agent.getMemoryModule().getMap());
        }

        Tile[][] tileMap = new Tile[memories.get(0).getWidth()][memories.get(0).getHeight()];

        for (int i = 0;i<memories.get(0).getHeight();i++){
            for (int j = 0;j<memories.get(0).getWidth();j++){
                for(Environment environment:memories){
                    Boolean isUnknown = environment.getTileMap()[j][i].getType().equals(TileType.UNKNOWN);
                    if (!isUnknown){
                        tileMap[j][i] = environment.getTileMap()[j][i];
                        break;
                    }
                    tileMap[j][i] = new Tile(new Position(j,i),TileType.UNKNOWN);
                }

            }
        }
        Environment env = new Environment(tileMap.length,tileMap[0].length,tileMap);
        return env;
    }

}
