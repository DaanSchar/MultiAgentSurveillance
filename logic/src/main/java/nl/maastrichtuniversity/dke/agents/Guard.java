package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.sound.SoundType;
import nl.maastrichtuniversity.dke.agents.modules.sound.SourceType;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;


@Slf4j
public class Guard extends Agent {

    private final @Getter int catchDistance = 1;

    public Guard() {
        super();
    }

    @Override
    public void spawn() {
        super.spawn();
//        setTarget(((MemoryModule) getMemoryModule()).getRandomPosition());
//        setTarget(new Position(50, 29));
//        setTarget(new Position(16, 15));
    }

    @Override
    public void move() {
        if (hasTarget()) {
            navigateToTarget();
        } else {
            super.explore();
        }
        super.move();
    }

    @Override
    public void updateInternals() {
        determineTarget();
//        super.rlMove();
        super.updateInternals();
    }

    private void determineTarget() {
        if (seesIntruder()) {
            chaseIntruder();
            catchIntruder();
        }  else if (hearsSound() && !seesGuard()) {
            moveToSoundSource();
        }
    }

    @Override
    protected boolean hearsSound() {
        List<Sound> sounds = getSoundsAtCurrentPosition();

        if (super.hearsSound()) {
            Sound sound = sounds.get(0);
            return sound.getSourceType() != SourceType.GUARD;
        }

        return false;
    }

    private void moveToSoundSource() {
        if (hearsSound()) {
            List<Sound> sounds = super.getSoundsAtCurrentPosition();

            if (sounds.size() > 0) {
                Sound sound = sounds.get(0);
                setTarget(getListeningModule().guessPositionOfSource(sound));
            }
        }
    }

    public boolean seesIntruder() {
        return getVisibleIntruder() != null;
    }

    public void chaseIntruder() {
        Intruder intruder = getVisibleIntruder();

        if (intruder != null) {
            getNoiseModule().makeSound(getPosition(), SoundType.YELL, SourceType.GUARD);
            setTarget(intruder.getPosition());
        }
    }

    private Intruder getVisibleIntruder() {
        List<Agent> visibleAgents = getVisibleAgents();

        for (Agent agent : visibleAgents) {
            if (agent instanceof Intruder) {
                return (Intruder) agent;
            }
        }

        return null;
    }

    public boolean seesGuard() {
        return getVisibleGuards() != null;
    }

    private Guard getVisibleGuards() {
        List<Agent> visibleAgents = getVisibleAgents();

        for (Agent agent : visibleAgents) {
            if (agent instanceof Guard && !agent.equals(this)) {
                return (Guard) agent;
            }
        }

        return null;
    }

    public void catchIntruder() {
        Intruder intruder = getVisibleIntruder();

        if (intruder == null) {
            return;
        }

        Position intruderPosition = intruder.getPosition();

        if (this.getPosition().distance(intruderPosition) <= catchDistance) {
            getInteractionModule().catchIntruder(intruderPosition);
        }
    }

}
