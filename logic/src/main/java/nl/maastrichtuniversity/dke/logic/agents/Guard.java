package nl.maastrichtuniversity.dke.logic.agents;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class Guard extends Agent {

    @Getter
    private final int catchDistance = 1;

    public Guard() {
        super();
    }

    @Override
    public void update() {
        if (getVisibleIntruder() != null) {
            if (!getVisibleIntruder().isCaught()) {
                chasing();
                catching();
            }
        } else {
            super.explore();
        }
        super.update();
    }

    public boolean seesIntruder() {
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
