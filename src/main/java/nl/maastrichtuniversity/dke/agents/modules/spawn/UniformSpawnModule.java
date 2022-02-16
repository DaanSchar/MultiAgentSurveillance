package nl.maastrichtuniversity.dke.agents.modules.spawn;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.util.Vector;
import nl.maastrichtuniversity.dke.areas.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UniformSpawnModule extends AgentModule implements ISpawnModule {

    public UniformSpawnModule(Environment scenario) {
        super(scenario);
    }

    /**
     * @return a random position in a randomly selected spawn area with uniform probability
     */
    public Vector getSpawnPosition(Agent agent) {
        List<Area> areas = new ArrayList<>();

//        if (agent instanceof Guard){
//            areas = scenario.getStaticEnvironment().get("spawnAreaGuards");
//        }else if(agent instanceof Intruder) {
//            areas = scenario.getStaticEnvironment().get("spawnAreaIntruders");
//        }else{
//            areas = null;
//        }

        Area spawnArea = areas.get(new Random().nextInt(areas.size()));

        Vector position = spawnArea.getPosition();
        double spawnX = position.getX() + new Random().nextDouble() * spawnArea.getWidth();
        double spawnY = position.getY() + new Random().nextDouble() * spawnArea.getHeight();

        return new Vector(spawnX, spawnY);
    }

    public Vector getSpawnDirection() {
        return null;
    }

}
