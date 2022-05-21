package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.agents.modules.exploration.DQN;
import nl.maastrichtuniversity.dke.agents.modules.runningAway.IRunningAway;
import nl.maastrichtuniversity.dke.agents.modules.sound.SourceType;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Intruder extends Agent {

    private @Getter @Setter boolean isCaught;
    private @Setter IRunningAway runningAway;
    private boolean navigatedToBlueMark; // whether the agent has navigated to the mark that another agent dropped
    private boolean droppedBlueMark; // did it drop the mark already

    private @Getter boolean fleeing;

    public Intruder() {
        super();
        this.isCaught = false;
        this.navigatedToBlueMark = false;
        this.droppedBlueMark = false;
    }

    @Override
    public void spawn() {
        super.spawn();
        this.isCaught = false;
    }

    @Override
    public void move() {
        if (isFleeing()) {
            if (DQN.isTraining()) super.rlMove();
        } else if (hasTarget()) {
            moveToTarget();
        } else {
            super.explore();
        }
        super.move();
    }

    private void moveToTarget() {
        if (hasReachedTarget()) {
            setTarget(null);
        } else {
            moveToPosition(getTarget());
        }
    }

    @Override
    public void updateInternals() {
        this.fleeing = false;

        if (seesTargetArea()) {
            setTarget(getTargetTile().getPosition());
        } else if (seesGuard() || hearsSound()) {
            flee();
        }

        super.updateInternals();
    }

    private void flee() {
        this.fleeing = true;
    }

    private void avoidSoundSource() {
        if (hearsSound()) {
            List<Sound> sounds = super.getSoundsAtCurrentPosition();

            if (sounds.size() > 0) {
                Sound sound = sounds.get(0);
                setTarget(runningAway.avoidGuard(sound.getPosition(), this.getPosition()));
            }
        }
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

}
