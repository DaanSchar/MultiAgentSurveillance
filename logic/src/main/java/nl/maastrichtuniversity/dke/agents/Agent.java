package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.ActionTimer;
import nl.maastrichtuniversity.dke.agents.modules.approximation.IApproximationModule;
import nl.maastrichtuniversity.dke.agents.modules.interaction.InteractionModule;
import nl.maastrichtuniversity.dke.agents.modules.communication.Mark;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.agents.modules.communication.ICommunicationModule;
import nl.maastrichtuniversity.dke.agents.modules.exploration.IExplorationModule;
import nl.maastrichtuniversity.dke.agents.modules.policy.IPolicyModule;
import nl.maastrichtuniversity.dke.agents.modules.reward.IRewardModule;
import nl.maastrichtuniversity.dke.agents.modules.sound.IListeningModule;
import nl.maastrichtuniversity.dke.agents.modules.memory.IMemoryModule;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovementModule;
import nl.maastrichtuniversity.dke.agents.modules.sound.SoundType;
import nl.maastrichtuniversity.dke.agents.modules.pathfind.PathFinderModule;
import nl.maastrichtuniversity.dke.agents.modules.sound.INoiseModule;
import nl.maastrichtuniversity.dke.agents.modules.pathfind.PathNavigator;
import nl.maastrichtuniversity.dke.agents.modules.smell.ISmellModule;
import nl.maastrichtuniversity.dke.agents.modules.sound.SourceType;
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
import java.util.stream.Stream;

@Getter
@Slf4j
@Accessors(chain = true)
public class Agent {

    private static int agentCount;
    private final int id;

    private @Setter Position position;
    private @Setter Direction direction;

    private PathNavigator pathNavigator;
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

    public void rlMove() {
        MoveAction nextMove = policyModule.nextMove(toArray());
        move(nextMove);
    }

    public void updateInternals() {
        view();
        updateMemory();
        updatePathToTarget();
    }

    public void navigateToTarget() {
        if (hasReachedTarget()) {
            getPathNavigator().clear();
            setTarget(null);
        } else {
            moveToPosition(getTarget());
        }
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
        move(pathNavigator.getNextMove(getPosition(), getDirection()));
    }

    public void toggleDoor() {
        Tile currentTile = memoryModule.getMap().getAt(getPosition());
        List<Tile> doorTiles = memoryModule.getMap()
                .getNeighborsAndFilter(currentTile, neighbor -> neighbor.getType() == TileType.DOOR);

        if (doorTiles.size() > 0) {
            Position door = doorTiles.get(0).getPosition();
            interactionModule.toggleDoor(door);
            memoryModule.toggledDoor(door);
            noiseModule.makeSound(door, SoundType.TOGGLE_DOOR, SourceType.DOOR);
        }
    }

    public void breakWindow() {
        Tile currentTile = memoryModule.getMap().getAt(getPosition());
        List<Tile> windowTiles = memoryModule.getMap()
                .getNeighborsAndFilter(currentTile, neighbor -> neighbor.getType() == TileType.WINDOW);

        if (windowTiles.size() > 0) {
            Position window = windowTiles.get(0).getPosition();
            interactionModule.breakWindow(window);
            memoryModule.brokeWindow(window);
            noiseModule.makeSound(window, SoundType.BREAK_WINDOW, SourceType.WINDOW);
        }
    }

    public boolean dropMark(CommunicationType type) {
        Mark mark = new Mark(getPosition(), type, this);

        if (communicationModule.hasMark(type)) {
            communicationModule.dropMark(mark);
            return true;
        }

        return false;
    }

    public int getTotalActions() {
        return actionTimer.getTotalActions();
    }

    protected void calculatePathTo(Position target) {
        if (this.pathNavigator == null || this.pathNavigator.getFinalDestination() != target
                        || memoryModule.discoveredNewTiles()) {
            this.pathNavigator = new PathNavigator(getPosition(), target, pathFinderModule);
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
        noiseModule.makeSound(position, SoundType.WALK, getAgentSourceType());
    }

    private void sprintForward() {
        position = movement.sprint(position, direction);
        noiseModule.makeSound(position, SoundType.SPRINT, getAgentSourceType());
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

    private SourceType getAgentSourceType() {
        if (this instanceof Guard) {
            return SourceType.GUARD;
        } else {
            return SourceType.INTRUDER;
        }
    }

    public double[][] toTimeArray() {
        double[][] observations = new double[getPolicyModule().getInputSize()][5];

        return observations;
    }

    //TODO
    // implement toArray for all specified modules
    public double[] toArray() {

        double[] fullObservations = new double[getPolicyModule().getInputSize()];
        double isFleeing = 0;
        if (this instanceof Intruder) {
            if (((Intruder) this).isFleeing()) {
                isFleeing = 1;
            }
        }

        List<Double> observations = new ArrayList<>();
        observations.add(isFleeing);
        observations.addAll(getStateVector());
        Stream.of(visionModule.toArray(), listeningModule.toArray()).forEach(observations::addAll);

        double[] observationsArray = listToArray(observations);
        System.arraycopy(observationsArray, 0, fullObservations, 0, observationsArray.length);

        return fullObservations;
    }


    public List<Double> getStateVector() {
        List<Double> observations = new ArrayList<>();
        observations.add((double) position.getX());
        observations.add((double) position.getY());
        observations.add((double) direction.getMoveX());
        observations.add((double) direction.getMoveY());
        return observations;
    }

    private double[] listToArray(List<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

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
    private @Setter IPolicyModule policyModule;
    private @Setter IRewardModule rewardModule;
    private @Setter IApproximationModule approximationModule;
    private @Setter ActionTimer actionTimer;

    public Agent newInstance() {
        return new Agent(direction, position, id, spawnModule, movement, visionModule, noiseModule, communicationModule,
                memoryModule, listeningModule, smellModule, rewardModule, policyModule, actionTimer, approximationModule);
    }

    private Agent(Direction direction, Position position, int id, ISpawnModule spawnModule, IMovementModule movement,
                  IVisionModule visionModule, INoiseModule noiseModule, ICommunicationModule communicationModule,
                  IMemoryModule memoryModule, IListeningModule listeningModule, ISmellModule smellModule,
                  IRewardModule rewardModule, IPolicyModule policyModule, ActionTimer actionTimer, IApproximationModule approximationModule) {
        this.spawnModule = spawnModule;
        this.visionModule = visionModule;
        this.movement = movement;
        this.noiseModule = noiseModule;
        this.communicationModule = communicationModule;
        this.memoryModule = memoryModule;
        this.listeningModule = listeningModule;
        this.smellModule = smellModule;
        this.rewardModule = rewardModule;
        this.policyModule = policyModule;
        this.approximationModule = approximationModule;
        this.actionTimer = actionTimer;
        this.position = position;
        this.direction = direction;
        this.id = id;
    }

}
