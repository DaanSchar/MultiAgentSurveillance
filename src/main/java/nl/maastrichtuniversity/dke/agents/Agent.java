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
    private final @Getter int id;

    private @Getter @Setter Vector position;
    private @Getter @Setter double baseSpeed;
    private @Getter @Setter double angle;

    private @Getter @Setter Vector direction;
    private @Getter @Setter double fov;

    private final ISpawnModule spawnModule;

    public Agent(ISpawnModule spawnModule, double baseSpeed, double fov) {
        this.position = null;
        this.spawnModule = spawnModule;
        this.baseSpeed = baseSpeed;
        this.id = agentCount++;
        this.fov = fov;

        logger.info("Created new " + this.getClass().getSimpleName() + " " + this.id + " with modules: " + spawnModule.getClass().getSimpleName());
    }

    /**
     * places the agent at a position determined by the spawn module
     */
    public void spawn() {
        this.position = spawnModule.getSpawnPosition();
        this.direction = spawnModule.getSpawnDirection();
        logger.info(this.getClass().getSimpleName() + " " + this.id + " spawned at " + this.position);
    }

    public void rotateLeft(){
        double x = angle + baseSpeed;
        if(x > 365){
            angle =  x - 365 ;
        }else{
            angle += baseSpeed;
        }
    }
    public void rotateRight(){
        double x = angle - baseSpeed;
        if(x < 0){
            angle = 365 + x;
        }else{
            angle -= baseSpeed;
        }
    }
    public void goForward(){
        position.add(new Vector((Math.cos(Math.toRadians(angle)) * baseSpeed), Math.sin(Math.toRadians(angle)) * baseSpeed));
    }
    public void goBackward(){
        position.add(new Vector((Math.sin(Math.toRadians(angle)) * baseSpeed), Math.cos(Math.toRadians(angle)) * baseSpeed));
    }

}
