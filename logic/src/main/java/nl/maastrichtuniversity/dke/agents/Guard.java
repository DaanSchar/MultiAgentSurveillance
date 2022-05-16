package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.noiseGeneration.SoundType;
import nl.maastrichtuniversity.dke.scenario.Sound;

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
    }

    @Override
    public void move() {
        if (hasTarget()) {
            if (hasReachedTarget()) {
                setTarget(null);
            } else {
                moveToPosition(getTarget());
            }
        } else {
            super.explore();
        }
        super.move();
    }

    @Override
    public void updateInternals() {
        determineTarget();
        super.updateInternals();
    }

    private void determineTarget() {
        if (seesIntruder()) {
            chaseIntruder();
            catchIntruder();
        }  else if (hearsSound()) {
            moveToSoundSource();
        }
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

    private boolean seesIntruder() {
        return getVisibleIntruder() != null;
    }

    public void chaseIntruder() {
        Intruder intruder = getVisibleIntruder();

        if (intruder != null) {
            getNoiseModule().makeSound(getPosition(), SoundType.YELL);
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

    public void catchIntruder() {
        Intruder intruder = getVisibleIntruder();

        if (intruder == null) {
            return;
        }

        if (this.getPosition().distance(intruder.getPosition()) <= catchDistance) {
            intruder.setCaught(true);
        }
    }

}
