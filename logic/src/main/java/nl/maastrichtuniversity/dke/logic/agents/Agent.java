package nl.maastrichtuniversity.dke.logic.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;

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

    public void spawn() {
        position = spawnModule.getSpawnPosition(this);
        direction = spawnModule.getSpawnDirection();
        memoryModule.setSpawnPosition(position);
        updateMemory();
    }

    public void update() {
        listen();
        view();
        updateMemory();
    }

    public void explore() {
        MoveAction nextMove = explorationModule.explore(getPosition(), getDirection());
        move(nextMove);
    }

    public void move(MoveAction action) {
        switch (action) {
            case MOVE_FORWARD -> moveForward();
            case SPRINT_FORWARD -> sprintForward();
            case ROTATE_LEFT -> rotate(MoveAction.ROTATE_LEFT);
            case ROTATE_RIGHT -> rotate(MoveAction.ROTATE_RIGHT);
            case STAND_STILL -> { /* do nothing */ }
            default -> log.info("not performing MoveAction: {}", action);
        }
    }

    public boolean dropMark(CommunicationType type) {
        if (communicationModule.hasMark(type)) {
            communicationModule.dropMark(
                    new CommunicationMark(
                            getPosition(),
                            type,
                            this
                    )
            );
            return true;
        }
        return false;
    }

    private void view() {
        visionModule.useVision(position, direction);
    }

    private void listen() {
        listeningModule.getDirection(this.position);
    }

    private void updateMemory() {
        memoryModule.update(visionModule, listeningModule, smellModule, getPosition());
    }

    private void rotate(MoveAction rotation) {
        direction = movement.rotate(direction, rotation);
    }

    private void moveForward() {
        position = movement.goForward(position, direction);
        noiseModule.makeWalkingSound(position);
    }

    private void sprintForward() {
        position = movement.sprint(position, direction);
        noiseModule.makeSprintingSound(position);
    }

    protected List<Direction> getDirectionsOfSounds() {
        return this.getListeningModule().getDirection(getPosition());
    }

    protected List<Agent> getVisibleAgents() {
        return this.getVisionModule().getVisibleAgents();
    }


    public Agent newInstance() {
        return new Agent(direction, position, id, spawnModule, movement, visionModule, noiseModule,
                communicationModule, memoryModule, listeningModule, smellModule);
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
