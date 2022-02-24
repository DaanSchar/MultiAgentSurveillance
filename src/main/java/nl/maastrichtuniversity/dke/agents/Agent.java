package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.modules.communication.ICommunicationModule;
import nl.maastrichtuniversity.dke.agents.modules.noiseGeneration.INoiseModule;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.discrete.CommunicationMark;
import nl.maastrichtuniversity.dke.util.Position;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

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
    private final ICommunicationModule communicationModule;
    private final INoiseModule noiseModule;

    public Agent(ISpawnModule spawnModule, IMovement movement, IVisionModule visionModule, INoiseModule noiseModule,  ICommunicationModule communicationModule) {
        this.spawnModule = spawnModule;
        this.visionModule = visionModule;
        this.movement = movement;
        this.noiseModule = noiseModule;
        this.communicationModule = communicationModule;
        this.id = agentCount++;

        // this should be in spawn module
        this.position = new Position(50, 50);
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
         var list = visionModule.getObstacles(position, direction);
         if(list.size() > 0){
             logger.info("Obstacle detected: {}", list);
         }
        noiseModule.makeWalkingSound(position);
    }

    public void goBackward(){
        position = movement.goBackward(position, direction);
    }
    public void dropMark(Position position , Color color){
        communicationModule.addMark(position.getX(),position.getY(),new CommunicationMark(position,color));
    }



//    public void go(Direction direction){
//        if(dir)
//    }


    public void rotate(int rotation){
        logger.info("current direction = " + direction);
        direction = movement.rotate(direction, rotation);
        logger.info("new direction = " + direction);
    }

    public void sprint(){
//        position = movement.sprint(position, direction);
//        baseSpeed -= 5;
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



//    public Tile getTile(){
//        Tile tile = new Tile(position);
//        return tile;
//    }

}
