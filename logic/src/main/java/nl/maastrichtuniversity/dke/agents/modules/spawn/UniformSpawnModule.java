package nl.maastrichtuniversity.dke.agents.modules.spawn;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;
import java.util.Random;


public class UniformSpawnModule extends AgentModule implements ISpawnModule {

    public UniformSpawnModule(Scenario scenario) {
        super(scenario);
    }

    /**
     * @return a random position in a randomly selected spawn area with uniform probability
     */
    public Position getSpawnPosition(Agent agent) {
        List<Tile> areas;

        if (agent instanceof Guard) {
            areas = scenario.getEnvironment().get(TileType.SPAWN_GUARDS);
        } else if (agent instanceof Intruder) {
            areas = scenario.getEnvironment().get(TileType.SPAWN_INTRUDERS);
        } else {
            areas = null;
        }

        Tile spawnTile = areas.get(new Random().nextInt(areas.size()));

        return new Position(spawnTile.getPosition().getX(), spawnTile.getPosition().getY());
    }

    public Direction getSpawnDirection() {
        int random = new Random().nextInt(4);

        return Direction.getAllDirections().get(random);
    }

}
