package nl.maastrichtuniversity.dke.logic.agents;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.ICommunicationModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.listening.IListeningModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.memory.IMemoryModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration.INoiseModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.logic.agents.modules.smell.ISmellModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import nl.maastrichtuniversity.dke.logic.agents.modules.spawn.ISpawnModule;
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

    private @Setter ISpawnModule spawnModule;
    private @Setter IMovement movement;
    private @Setter IVisionModule visionModule;
    private @Setter ICommunicationModule communicationModule;
    private @Setter INoiseModule noiseModule;
    private @Setter IListeningModule listeningModule;
    private @Setter IMemoryModule memoryModule;
    private @Setter ISmellModule smellModule;

    public Agent() {
        this.id = agentCount++;
    }

    /**
     * places the agent at a position determined by the spawn module
     */
    public void spawn() {
        position = spawnModule.getSpawnPosition(this);
        direction = spawnModule.getSpawnDirection();
        memoryModule.setSpawnPosition(position);
        updateMemory();

        logger.info(this.getClass().getSimpleName() + " " + this.id + " spawned at " + this.position + " facing " + this.direction);
    }

    public void goForward(double time){
         position = movement.goForward(position, direction, time);
         visionModule.useVision(position,direction);
         var list = visionModule.getObstacles();
         noiseModule.makeWalkingSound(position);
         updateMemory();
    }

    public double[] getStateVector() {
        var obstacles = visionModule.getObstacles();
        var size = ((VisionModule)visionModule).getViewingDistance() * 3 + 2;
        var stateVector = new double[size];

        for (int i = 0; i < size; i++) {
            if (i < obstacles.size()) { stateVector[i] = obstacles.get(i).getType().getValue(); }
            else { stateVector[i] = 0; }
        }

        return stateVector;
    }

    /**
     * @param type a type of communication device want to drop
     * @return if the type was available and agent droped it
     */
    public boolean dropMark(CommunicationType type){
        if(communicationModule.hasMark(type)){
            if(type.equals(CommunicationType.VISIONBLUE))
                communicationModule.dropMark(new CommunicationMark(this.position, type, this, Color.BLUE));
            else if(type.equals(CommunicationType.VISIONRED))
                communicationModule.dropMark(new CommunicationMark(this.position, type, this, Color.RED));
            else if(type.equals(CommunicationType.VISIONGREEN))
                communicationModule.dropMark(new CommunicationMark(this.position, type, this, Color.GREEN));
            else
                communicationModule.dropMark(new CommunicationMark(this.position, type, this, null));
            updateMemory();

            return true;
        }
        return false;
    }

    public void listen(){
        listeningModule.getDirection(this.position);
    }

    public void updateMemory() {
        memoryModule.update(visionModule, listeningModule, smellModule, getPosition());
    }

    public Agent newInstance() {
        return new Agent(direction, position, id, spawnModule, movement, visionModule, noiseModule, communicationModule, memoryModule,listeningModule, smellModule);
    }

    /** 1 is left
    -1 is right */
    public void rotate(int rotation, double time){
        direction = movement.rotate(direction, rotation, time);
        updateMemory();
    }

    private Agent(Direction direction,Position position,int id,ISpawnModule spawnModule, IMovement movement,
                  IVisionModule visionModule, INoiseModule noiseModule,  ICommunicationModule communicationModule,
                  IMemoryModule memoryModule,IListeningModule listeningModule, ISmellModule smellModule){

        this.spawnModule = spawnModule;
        this.visionModule = visionModule;
        this.movement = movement;
        this.noiseModule = noiseModule;
        this.communicationModule = communicationModule;
        this.memoryModule = memoryModule;
        this.listeningModule = listeningModule;
        this.smellModule = smellModule;
        this.id = id;

        // this should be in spawn module
        this.position = position;
        this.direction = direction;
    }


}
