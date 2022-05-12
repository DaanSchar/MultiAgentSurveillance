package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import nl.maastrichtuniversity.dke.util.Distribution;

import java.util.List;


@Slf4j
public class Guard extends Agent {

    public Guard() {
        super();
    }

    @Override
    public void move() {
        if (seesIntruder()) {
            chasing();
        } else if (hearSoundAtCurrentPosition()) {
            Sound sound = super.getSoundAtCurrentPosition();
            moveToLocation(sound.getSource());
        } else {
            super.explore();
        }

        super.move();
    }

    private boolean seesIntruder() {
        return getVisibleIntruder() != null;
    }

    public void chasing() {
        Intruder intruder = getVisibleIntruder();

        if (intruder != null) {
            moveToLocation(intruder.getPosition());
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
}
