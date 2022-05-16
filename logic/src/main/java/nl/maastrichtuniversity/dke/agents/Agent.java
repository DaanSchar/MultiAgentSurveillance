package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.interaction.InteractionModule;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.agents.modules.communication.ICommunicationModule;
import nl.maastrichtuniversity.dke.agents.modules.exploration.IExplorationModule;
import nl.maastrichtuniversity.dke.agents.modules.listening.IListeningModule;
import nl.maastrichtuniversity.dke.agents.modules.memory.IMemoryModule;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovementModule;
import nl.maastrichtuniversity.dke.agents.modules.noiseGeneration.SoundType;
import nl.maastrichtuniversity.dke.agents.modules.pathfind.PathFinderModule;
import nl.maastrichtuniversity.dke.agents.modules.noiseGeneration.INoiseModule;
import nl.maastrichtuniversity.dke.agents.modules.smell.ISmellModule;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Slf4j
@Accessors(chain = true)
public class Agent {

    private @Setter ISpawnModule spawnModule;
    private @Setter IMovementModule movement;
    private @Setter IVisionModule visionModule;
    private @Setter ICommunicationModule communicationModule;
    private @Setter INoiseModule noiseModule;
    private @Setter IListeningModule listeningModule;
    private @Setter IMemoryModule memoryModule;
    private @Setter ISmellModule smellModule;
    private @Setter IExplorationModule explorationModule;
    private @Setter InteractionModule interactionModule;
    private @Setter PathFinderModule pathFinderModule;

    private static int agentCount;
    private final int id;

    private @Setter Position position;
    private @Setter Direction direction;

    private Path path;
    private @Setter Position target;

    public Agent() {
        this.id = agentCount++;
    }

    public void spawn() {
        position = spawnModule.getSpawnPosition(this);
        direction = spawnModule.getSpawnDirection();
        memoryModule.setSpawnPosition(position);
        updateMemory();
    }

    public void move() {
        /* empty for now */
    }

    public void updateInternals() {
        view();
        updateMemory();
        updatePathToTarget();
    }

    public void explore() {
        if (explorationModule.isDoneExploring()) {
            explorationModule.reset();
        }

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
            case BREAK_WINDOW -> breakWindow();
            case TOGGLE_DOOR -> toggleDoor();
            default -> log.info("not performing MoveAction: {}", action);
        }
    }

    public void moveToPosition(Position target) {
        calculatePathTo(target);
        move(path.getNextMove(getPosition(), getDirection()));
    }

    public void toggleDoor() {
        Tile currentTile = memoryModule.getMap().getAt(getPosition());
        List<Tile> doorTiles = memoryModule.getMap().getNeighborsAndFilter(currentTile, neighbor -> neighbor.getType() == TileType.DOOR);

        if (doorTiles.size() > 0) {
            Position door = doorTiles.get(0).getPosition();
            interactionModule.toggleDoor(door);
            noiseModule.makeSound(door, SoundType.TOGGLE_DOOR);
        }
    }

    public void breakWindow() {
        Tile currentTile = memoryModule.getMap().getAt(getPosition());
        List<Tile> windowTiles = memoryModule.getMap().getNeighborsAndFilter(currentTile, neighbor -> neighbor.getType() == TileType.WINDOW);

        if (windowTiles.size() > 0) {
            Position window = windowTiles.get(0).getPosition();
            interactionModule.breakWindow(window);
            noiseModule.makeSound(window, SoundType.BREAK_WINDOW);
        }
    }

    public boolean dropMark(CommunicationType type) {
        CommunicationMark mark = new CommunicationMark(getPosition(), type, this);

        if (communicationModule.hasMark(type)) {
            communicationModule.dropMark(mark);
            return true;
        }

        return false;
    }

    public void moveRandomly() {
        MoveAction[] moves = {MoveAction.ROTATE_LEFT, MoveAction.ROTATE_RIGHT, MoveAction.MOVE_FORWARD};
        MoveAction randomMove = moves[new Random().nextInt(moves.length)];
        move(randomMove);
    }

    protected void calculatePathTo(Position target) {
        if (this.path == null || this.path.getFinalDestination() != target) {
            this.path = new Path(getPosition(), target, pathFinderModule, movement, memoryModule);
        }
    }

    protected boolean hasTarget() {
        return this.target != null;
    }

    protected boolean hasReachedTarget() {
        return getPosition().equals(this.target);
    }

    private void updatePathToTarget() {
        if (hasTarget()) {
            calculatePathTo(this.target);
        }
    }

    private void view() {
        visionModule.useVision(position, direction);
    }

    private void updateMemory() {
        memoryModule.update(visionModule, smellModule, getPosition());
    }

    private void rotate(MoveAction rotation) {
        direction = movement.rotate(direction, rotation);
    }

    private void moveForward() {
        position = movement.goForward(position, direction);
        noiseModule.makeSound(position, SoundType.WALK);
    }

    private void sprintForward() {
        position = movement.sprint(position, direction);
        noiseModule.makeSound(position, SoundType.SPRINT);
    }

    protected List<Agent> getVisibleAgents() {
        return this.getVisionModule().getVisibleAgents();
    }

    protected List<Sound> getSoundsAtCurrentPosition() {
        List<Sound> sounds = listeningModule.getSounds(getPosition());

        return sounds.stream().filter(sound -> {
            Position source = sound.getSource();
            return !source.equals(getPosition());
        }).collect(Collectors.toList());
    }

    protected boolean hearsSound() {
        return getSoundsAtCurrentPosition().size() > 0;
    }

    protected Tile getFacingTile() {
        Position facingPosition = getPosition().getPosInDirection(getDirection());
        return memoryModule.getMap().getAt(facingPosition);
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
