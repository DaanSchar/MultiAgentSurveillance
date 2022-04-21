package nl.maastrichtuniversity.dke.logic.agents.modules.spawn;

import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

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
            areas = scenario.getEnvironment().get(TileType.SPAWN_GUARDS);
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
