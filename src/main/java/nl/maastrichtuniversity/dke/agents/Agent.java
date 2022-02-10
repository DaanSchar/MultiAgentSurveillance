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
    private @Getter @Setter int angle;

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

    public void update(int x){
        // 1 for up, 2 for right, 3 for down, 4 for left
        if(x == 1){
            position = new Vector( position.getX() + (Math.cos(angle) * baseSpeed), position.getY() + (Math.sin(angle) * baseSpeed));
        }
        if(x == 3) {
            position = new Vector(position.getX() + (Math.sin(angle) * baseSpeed), position.getY() + (Math.cos(angle) * baseSpeed));
        }
//        if(x == 2){
//            angle
//        }

    }




}
