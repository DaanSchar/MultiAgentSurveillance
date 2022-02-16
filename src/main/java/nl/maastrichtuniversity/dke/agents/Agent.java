package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.util.Vector;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class Agent {

    private static final Logger logger = LoggerFactory.getLogger(Agent.class);

    private static int agentCount;
    private final int id;

    private @Setter Vector position;
    private @Setter double baseSpeed;
    private @Setter Vector direction;
    private @Setter double sprintSpeed;

    private final ISpawnModule spawnModule;
    private final IMovement movement;
    private final IVisionModule visionModule;

    public Agent(ISpawnModule spawnModule, IMovement movement, IVisionModule visionModule, double baseSpeed) {
        this.spawnModule = spawnModule;
        this.visionModule = visionModule;
        this.movement = movement;
        this.baseSpeed = baseSpeed;
        this.id = agentCount++;

        logger.info("Created new " + this.getClass().getSimpleName() + " " + this.id + " with modules: " + spawnModule.getClass().getSimpleName());
    }

    /**
     * places the agent at a position determined by the spawn module
     */
    public void spawn() {
        this.position = spawnModule.getSpawnPosition();
        logger.info(this.getClass().getSimpleName() + " " + this.id + " spawned at " + this.position);
    }

    public void goForward(){
        movement.goForward(position, direction);
    }

    public void goBackward(){
        movement.goBackward(position, direction);
    }

    public void sprint(){
        movement.sprint(position, direction);
        baseSpeed -= 5;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        baseSpeed += 5;
                    }
                },
                5000
        );
    }

    public void rotate(){
        movement.rotate(direction,baseSpeed);
    }

}
