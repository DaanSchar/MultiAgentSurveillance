package nl.maastrichtuniversity.dke.agents.modules.memory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.agents.modules.smell.ISmellModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class MemoryModule extends AgentModule implements IMemoryModule {

    private final Environment map;
    private final List<Tile> discoveredTiles;
    private final List<Agent> agents;
    private final List<List<Sound>> sounds;
    private final List<Direction> soundDirection;
    private final List<Position> smells;
    private @Setter Position position;
    private @Getter @Setter Position previousPosition;
    private @Getter boolean discoveredNewTiles;

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
        this.discoveredNewTiles = false;
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

    public void update(IVisionModule visionModule, ISmellModule smellModule, Position position) {
        setPreviousPosition(this.position);
        setPosition(position);
        updateVision(visionModule);
        updateSmell(smellModule);
    }

    public List<Sound> getCurrentSounds() {
        if (sounds.size() < 1) {
            return new ArrayList<>();
        }

        return sounds.get(sounds.size() - 1);
    }

    @Override
    public void toggledDoor(Position position) {
        MemoryTile door = (MemoryTile) map.getAt(position);

        if (door.getType() == TileType.DOOR) {
            door.setOpened(!door.isOpened());
        }
    }

    @Override
    public void brokeWindow(Position position) {
        MemoryTile window = (MemoryTile) map.getAt(position);

        if (window.getType() == TileType.WINDOW) {
            window.setBroken(true);
        }
    }

    public boolean discoveredNewTiles() {
        return discoveredNewTiles;
    }

    private void updateSmell(ISmellModule smellModule) {
        if (smellModule.getSmell(position)) {
            smells.add(position);
//            smells.addAll(smellModule.getDirection(position));
        }
    }

    private void updateVision(IVisionModule vision) {
        this.discoveredTiles.clear();
        this.discoveredNewTiles = false;

        for (Tile tile : vision.getVisibleTiles()) {
            int x = tile.getPosition().getX();
            int y = tile.getPosition().getY();

            if (map.getTileMap()[x][y].getType() == TileType.UNKNOWN) {
                discoveredTiles.add(tile);
                map.getTileMap()[x][y] = new MemoryTile(tile);
            }
        }

        if (discoveredTiles.size() > 0) {
            discoveredNewTiles = true;
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

    public Position getRandomPosition() {
        Environment environment = scenario.getEnvironment();
        List<Tile> possibleTiles = environment.filter(Tile::isPassable);
        Tile randomTile = possibleTiles.get((int) (Math.random() * possibleTiles.size()));
        return randomTile.getPosition();
    }

}
