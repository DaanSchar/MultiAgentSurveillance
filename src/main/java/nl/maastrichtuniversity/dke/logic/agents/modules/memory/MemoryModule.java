package nl.maastrichtuniversity.dke.logic.agents.modules.memory;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
public class MemoryModule extends AgentModule implements IMemoryModule {

    private final Environment map;
    private final List<Tile> discoveredTiles;
    private final List<Agent> agents;

    private @Setter Position startPosition;

    public MemoryModule(Scenario scenario) {
        super(scenario);

        int width = scenario.getEnvironment().getWidth();
        int height = scenario.getEnvironment().getHeight();
        this.map = new Environment(width, height, new Tile[width][height]);
        this.discoveredTiles = new LinkedList<>();

        initEnvironment();

        agents = new ArrayList<>();
    }

    private void initEnvironment() {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                map.getTileMap()[x][y] = new Tile(new Position(x, y), TileType.UNKNOWN);
            }
        }
    }

    //TODO: add sound to update method in memory
    public void update(IVisionModule vision) {
        discoveredTiles.clear();
        for(Tile tile: vision.getObstacles()) {
            int x = tile.getPosition().getX();
            int y = tile.getPosition().getY();

            if(map.getTileMap()[x][y].getType()==TileType.UNKNOWN){
                discoveredTiles.add(tile);
            }

            map.getTileMap()[x][y] = tile;
        }
        for(Agent agentSee: vision.getAgents()){
            if(agents.get(agentSee.getId()) != null){
                agents.get(agentSee.getId()).setPosition(agentSee.getPosition());
                agents.get(agentSee.getId()).setDirection(agentSee.getDirection());
            }else{
                agents.add(agentSee.getId(),agentSee.newInstance());
            }

        }

    }




}
