package nl.maastrichtuniversity.dke.logic.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.ICommunicationModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.exploration.IExplorationModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.listening.IListeningModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.memory.IMemoryModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.movement.IMovementModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration.INoiseModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.smell.ISmellModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.spawn.ISpawnModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import nl.maastrichtuniversity.dke.util.DebugSettings;

import java.awt.*;

/**
 * agent class parent of guard and intruder
 *
 * @Author Parand
 */
@Getter
@Slf4j
@Accessors(chain = true)
public class Agent {

    private static int agentCount;
    private final int id;

    private @Setter Position position;
    private @Setter Direction direction;

    private @Setter ISpawnModule spawnModule;
    private @Setter IMovementModule movement;
    private @Setter IVisionModule visionModule;
    private @Setter ICommunicationModule communicationModule;
    private @Setter INoiseModule noiseModule;
    private @Setter IListeningModule listeningModule;
    private @Setter IMemoryModule memoryModule;
    private @Setter ISmellModule smellModule;
    private @Setter IExplorationModule explorationModule;

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

        if (DebugSettings.FACTORY)
            log.info(this.getClass().getSimpleName() + " " + this.id + " spawned at " + this.position + " facing " + this.direction);
    }

    public void explore() {
        MoveAction nextMove = explorationModule.explore(getPosition(), getDirection());
        move(nextMove);
    }

    public void move(MoveAction action) {
        switch (action) {
            case MOVE_FORWARD -> moveForward(Game.getInstance().getTime());
            case ROTATE_LEFT -> rotate(MoveAction.ROTATE_LEFT, Game.getInstance().getTime());
            case ROTATE_RIGHT -> rotate(MoveAction.ROTATE_RIGHT, Game.getInstance().getTime());
            default -> log.info("not performing MoveAction: {}", action);
        }

        updateMemory();
    }

    /**
     * @param type a type of communication device want to drop
     * @return if the type was available and agent droped it
     */
    public boolean dropMark(CommunicationType type) {
        if (communicationModule.hasMark(type)) {
            if (type.equals(CommunicationType.VISION_BLUE))
                communicationModule.dropMark(new CommunicationMark(getPosition(), type, this, Color.BLUE));
            else if (type.equals(CommunicationType.VISION_RED))
                communicationModule.dropMark(new CommunicationMark(getPosition(), type, this, Color.RED));
            else if (type.equals(CommunicationType.VISION_GREEN))
                communicationModule.dropMark(new CommunicationMark(getPosition(), type, this, Color.GREEN));
            else
                //here you cant see so its just background color
                communicationModule.dropMark(new CommunicationMark(getPosition(), type, this, new Color(173, 237, 153)));
            updateMemory();

            return true;
        }
        return false;
    }

    public void listen() {
        listeningModule.getDirection(this.position);
    }

    public void updateMemory() {
        memoryModule.update(visionModule, listeningModule, smellModule, getPosition());
    }

    public Agent newInstance() {
        return new Agent(direction, position, id, spawnModule, movement, visionModule, noiseModule, communicationModule, memoryModule, listeningModule, smellModule);
    }

    private void rotate(MoveAction rotation, double time) {
        direction = movement.rotate(direction, rotation, time);
    }

    private void moveForward(double time) {
        position = movement.goForward(position, direction, time);
        visionModule.useVision(position, direction);
        var list = visionModule.getObstacles();
        noiseModule.makeWalkingSound(position);
    }

    private Agent(Direction direction, Position position, int id, ISpawnModule spawnModule, IMovementModule movement,
                  IVisionModule visionModule, INoiseModule noiseModule, ICommunicationModule communicationModule,
                  IMemoryModule memoryModule, IListeningModule listeningModule, ISmellModule smellModule) {

        this.spawnModule = spawnModule;
        this.visionModule = visionModule;
        this.movement = movement;
        this.noiseModule = noiseModule;
        this.communicationModule = communicationModule;
        this.memoryModule = memoryModule;
        this.listeningModule = listeningModule;
        this.smellModule = smellModule;
        this.position = position;
        this.direction = direction;
        this.id = id;
    }


}
