package nl.maastrichtuniversity.dke.logic.agents.modules.memory;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.listening.IListeningModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.smell.ISmellModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemoryModule extends AgentModule implements IMemoryModule {

    private final Environment map;
    private final List<Tile> discoveredTiles;
    private final List<Agent> agents;
    private final List<Position> sounds;
    private final List<Direction> soundDirection;
    private final List<Position> smells;
    private @Setter Position position;
    private @Getter @Setter Position previousPosition;

    public MemoryModule(Scenario scenario) {
        super(scenario);

        int width = scenario.getEnvironment().getWidth();
        int height = scenario.getEnvironment().getHeight();
        this.map = new Environment(width, height, new MemoryTile[width][height]);
        this.discoveredTiles = new LinkedList<>();
        this.smells = new ArrayList<>();
        this.sounds = new ArrayList<>();
        this.agents = new ArrayList<>();
        this.soundDirection = new ArrayList<>();

        initEnvironment();
    }

    public void setSpawnPosition(Position position) {
        map.getTileMap()[position.getX()][position.getY()] = new MemoryTile(position, TileType.SPAWN_GUARDS);
        setPreviousPosition(position);
        setPosition(position);

    }

    private void initEnvironment() {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                map.getTileMap()[x][y] = new MemoryTile(new Position(x, y), TileType.UNKNOWN);
            }
        }
    }

    public List<Tile> getCoveredTiles() {
        return map.stream()
                .filter(tile -> tile.getType() != TileType.UNKNOWN)
                .collect(Collectors.toList());
    }

    public void update(IVisionModule visionModule, IListeningModule listeningModule,
                       ISmellModule smellModule, Position position) {
        setPreviousPosition(this.position);
        setPosition(position);
        updateVision(visionModule);
        updateSound(listeningModule);
        updateSmell(smellModule);
    }

    private void updateSound(IListeningModule listeningModule) {
        if (listeningModule.getSound(position)) {
            sounds.add(position);
            soundDirection.addAll(listeningModule.getDirection(position));
        }
    }

    private void updateSmell(ISmellModule smellModule) {
        if (smellModule.getSmell(position)) {
            smells.add(position);
//            smells.addAll(smellModule.getDirection(position));
        }
    }

    //TODO: add sound to update method in memory
    private void updateVision(IVisionModule vision) {
        discoveredTiles.clear();

        for (Tile tile : vision.getVisibleTiles()) {
            int x = tile.getPosition().getX();
            int y = tile.getPosition().getY();

            if (map.getTileMap()[x][y].getType() == TileType.UNKNOWN) {
                discoveredTiles.add(tile);
                map.getTileMap()[x][y] = new MemoryTile(tile);
            }
        }
        for (Agent agentSee : vision.getVisibleAgents()) {
            if (agents.size() > 0) {
                if (agents.get(agentSee.getId()) != null) {
                    agents.get(agentSee.getId()).setPosition(agentSee.getPosition());
                    agents.get(agentSee.getId()).setDirection(agentSee.getDirection());
                } else {
                    agents.add(agentSee.getId(), agentSee.newInstance());
                }
            }
        }
    }

    public List<Tile> getVisitedNodes() {
        return getMap().stream().filter(x -> ((MemoryTile) x).isVisited()).collect(Collectors.toList());
    }


}
