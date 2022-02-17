package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.util.Position;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * agent class parent of guard and intruder
 *
 * @Author Parand
 */
@Getter
public class Agent {

    private static final Logger logger = LoggerFactory.getLogger(Agent.class);

    private static int agentCount;
    private final int id;


    private @Setter Position position;
    private @Setter Direction direction;

    private final ISpawnModule spawnModule;
    private final IMovement movement;
    private final IVisionModule visionModule;

    public Agent(ISpawnModule spawnModule, IMovement movement, IVisionModule visionModule) {
        this.spawnModule = spawnModule;
        this.visionModule = visionModule;
        this.movement = movement;
        this.id = agentCount++;

        // this should be in spawn module
        this.position = new Position(10, 10);
        this.direction = Direction.NORTH;

        logger.info("Created new " + this.getClass().getSimpleName() + " " + this.id + " with modules: " + spawnModule.getClass().getSimpleName());
    }

    /**
     * places the agent at a position determined by the spawn module
     */
    public void spawn() {
        this.position = spawnModule.getSpawnPosition(this);
        logger.info(this.getClass().getSimpleName() + " " + this.id + " spawned at " + this.position);
    }

    public void goForward(){
         position = movement.goForward(position, direction);
    }

    public void goBackward(){
        position = movement.goBackward(position, direction);
    }

    public void sprint(){
//        position = movement.sprint(position, direction);
//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        // your code here
//                        baseSpeed += 5;
//                    }
//                },
//                5000
//        );
    }

    public void rotate(int rotation){
        direction = movement.rotate(direction, rotation);
    }

//    public Tile getTile(){
//        Tile tile = new Tile(position);
//        return tile;
//    }

}
