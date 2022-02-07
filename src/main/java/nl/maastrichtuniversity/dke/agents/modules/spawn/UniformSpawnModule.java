package nl.maastrichtuniversity.dke.agents.modules.spawn;

import nl.maastrichtuniversity.dke.scenario.Environment;
import nl.maastrichtuniversity.dke.util.Vector;
import nl.maastrichtuniversity.dke.areas.Area;

import java.util.List;
import java.util.Random;

public class UniformSpawnModule implements ISpawnModule {

    private Environment environment;

    public UniformSpawnModule(Environment environment) {
        this.environment = environment;
    }

    /**
     * @return a random position in a randomly selected spawn area with uniform probability
     */
    public Vector getSpawnPosition() {
        List<Area> areas = environment.getSpawnAreaGuards();
        Area spawnArea = areas.get(new Random().nextInt(areas.size()));

        Vector position = spawnArea.getPosition();
        double spawnX = position.getX() + new Random().nextDouble() * spawnArea.getWidth();
        double spawnY = position.getY() + new Random().nextDouble() * spawnArea.getHeight();

        return new Vector(spawnX, spawnY);
    }

}
