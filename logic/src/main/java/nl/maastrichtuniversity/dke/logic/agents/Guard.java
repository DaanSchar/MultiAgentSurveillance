package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class Guard extends Agent {

    public Guard() {
        super();
    }

    @Override
    public void update() {
        if (seesIntruder()) {
            chasing();
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

}
