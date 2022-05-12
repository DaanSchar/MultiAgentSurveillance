package nl.maastrichtuniversity.dke.logic.agents;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import nl.maastrichtuniversity.dke.util.Distribution;

import java.util.List;


@Slf4j
public class Guard extends Agent {

    @Getter
    private final int catchDistance = 1;

    public Guard() {
        super();
    }

    @Override
    public void move() {
//        if (seesIntruder()) {
//            chasing();
        if (hearSoundAtCurrentPosition()) {
            List<Sound> sounds = super.getSoundsAtCurrentPosition();
            if (sounds.size() > 0) {
                Sound sound = sounds.get(0);
                moveToLocation(sound.getSource());
            }
        } else {
//            super.explore();
        }

        super.move();
    }

    private boolean seesIntruder() {
        return getVisibleIntruder() != null;
    }

    public void chasing() {
        Intruder intruder = getVisibleIntruder();

        if (intruder != null) {
            getNoiseModule().yell(getPosition());
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

    public void catching() {
        Intruder intruder = getVisibleIntruder();
        if (intruder == null) {
            return;
        }

        if (this.getPosition().distance(intruder.getPosition()) <= catchDistance) {
            intruder.setCaught(true);
        }
    }



}
