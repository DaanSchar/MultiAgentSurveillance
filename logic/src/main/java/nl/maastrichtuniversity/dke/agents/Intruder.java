package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.agents.modules.exploration.Train;
import nl.maastrichtuniversity.dke.agents.modules.runningAway.IRunningAway;
import nl.maastrichtuniversity.dke.agents.modules.sound.SourceType;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Intruder extends Agent {

    private @Getter @Setter boolean isCaught;
    private @Setter IRunningAway runningAway;
    private boolean navigatedToBlueMark; // whether the agent has navigated to the mark that another agent dropped
    private boolean droppedBlueMark; // did it drop the mark already
    private @Getter Position guessTarget;
    private @Getter boolean reachGuessTarget;

    private @Getter
    boolean fleeing;

    public Intruder() {
        super();
        this.isCaught = false;
        this.navigatedToBlueMark = false;
        this.droppedBlueMark = false;
        this.guessTarget = null;
        this.reachGuessTarget = false;
    }

    @Override
    public void spawn() {

        super.spawn();
        guessTarget = this.getApproximationModule().getValidTargetGuess();
        this.isCaught = false;
    }

    @Override
    public void move() {
        if (hasReachedTarget()) {
            return;
        }
        if (this.getPosition().equals(guessTarget)) {
            reachGuessTarget = true;
        }
        if (isFleeing()) {
            if (!Train.isTraining()) {
                super.rlMove();
            }
        } else if (hasTarget()) {
            navigateToTarget();
        } else if (reachGuessTarget) {
            super.explore();
        } else {
            moveToPosition(guessTarget);
        }
        super.move();
    }

    @Override
    public void updateInternals() {
        determineTarget();
        super.updateInternals();
    }

    @Override
    protected boolean hearsSound() {
        List<Sound> sounds = getSoundsAtCurrentPosition();

        if (super.hearsSound()) {
            Sound sound = sounds.get(0);
            return sound.getSourceType() != SourceType.INTRUDER;
        }

        return false;
    }

    private void determineTarget() {
        this.fleeing = false;

        if (seesTargetArea()) {
            setTarget(getTargetTile().getPosition());
        } else if (seesGuard() || hearsSound()) {
            flee();
        }
    }

    private void flee() {
        this.fleeing = true;
    }

    private void avoidSoundSource() {
        List<Sound> sounds = super.getSoundsAtCurrentPosition();
        if (super.hearsSound()) {
            Sound sound = sounds.get(0);
            if (sound.getSourceType() != SourceType.INTRUDER) {
                setTarget(runningAway.avoidGuard(sound.getPosition(), this.getPosition()));
            }
        }
    }

    private boolean seesTargetArea() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();

        return containsTarget(obstacles);
    }

    public boolean seesGuard() {
        return getVisibleGuards().size() > 0;
    }

    private boolean seesIntruder() {
        return getVisibleIntruder() != null;
    }

    public List<Guard> getVisibleGuards() {
        List<Agent> visibleAgents = super.getVisibleAgents();
        List<Agent> visibleGuards = filterGuards(visibleAgents);

        return castAgentsToGuards(visibleGuards);
    }

    private Intruder getVisibleIntruder() {
        List<Agent> visibleAgents = super.getVisibleAgents();
        for (Agent agent : visibleAgents) {
            if (agent instanceof Intruder) {
                return (Intruder) agent;
            }
        }

        return null;
    }

    private List<Agent> filterGuards(List<Agent> agents) {
        return agents.stream()
                .filter(agent -> agent instanceof Guard)
                .collect(Collectors.toList());
    }

    private List<Guard> castAgentsToGuards(List<Agent> agents) {
        return agents.stream()
                .map(agent -> (Guard) agent)
                .collect(Collectors.toList());
    }

    private boolean containsTarget(List<Tile> obstacles) {
        for (Tile tile : obstacles) {
            if (isTarget(tile)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTarget(Tile tile) {
        return tile.getType().equals(TileType.TARGET);
    }

    private Tile getTargetTile() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();
        return obstacles.stream().filter(tile -> tile.getType().equals(TileType.TARGET)).findFirst().get();
    }

    private boolean seesBlueMark() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();

        return containsBlueMark(obstacles);
    }

    private boolean containsBlueMark(List<Tile> obstacles) {
        for (Tile tile : obstacles) {
            if (isBlueMark(tile)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBlueMark(Tile tile) {
        Position position = tile.getPosition();

        return super.getCommunicationModule().tileHasMark(position, CommunicationType.VISION_BLUE);
    }

    private Position getBlueMark() {
        return super.getCommunicationModule().getMark(CommunicationType.VISION_BLUE);
    }

    private void navigateToBlueMark() {
        Position target = getBlueMark();
        moveToPosition(target);
    }

    @Override
    public List<Double> getStateVector() {
        List<Double> observations = super.getStateVector();

        if (seesGuard()) {
            Guard visibleGuard = getVisibleGuards().get(0);
            observations.add((double) visibleGuard.getPosition().getX());
            observations.add((double) visibleGuard.getPosition().getY());
        } else if (hearsSound()) {
            Sound sound = getSoundsAtCurrentPosition().get(0);
            observations.add((double) sound.getSource().getX());
            observations.add((double) sound.getSource().getY());
        } else {
            observations.add(0.0);
            observations.add(0.0);
        }

        return observations;
    }
}
