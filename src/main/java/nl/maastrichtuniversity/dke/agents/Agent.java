package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.util.Vector;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Agent {

    Logger logger = LoggerFactory.getLogger(Agent.class);

    private static int agentCount;
    private @Getter int id;

    private @Getter @Setter Vector position;
    private @Getter @Setter double baseSpeed;

    private ISpawnModule spawnModule;

    public Agent(ISpawnModule spawnModule, double baseSpeed) {
        this.position = null;
        this.spawnModule = spawnModule;
        this.baseSpeed = baseSpeed;
        this.id = agentCount++;

        System.out.println("you should be able to find this");

        logger.info("Created new " + this.getClass().getSimpleName() + " " + this.id + " with modules: " + spawnModule.getClass().getSimpleName());
    }

    /**
     * places the agent at a position determined by the spawn module
     */
    public void spawn() {
        this.position = spawnModule.getSpawnPosition();
        logger.info(this.getClass().getSimpleName() + " " + this.id + " spawned at " + this.position);
    }

}
