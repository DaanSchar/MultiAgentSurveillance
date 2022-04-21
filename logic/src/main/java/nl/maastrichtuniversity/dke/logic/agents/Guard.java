package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Guard extends Agent {

    public Guard() {
        super();
    }

    @Override
    public void update() {
        super.explore();
        super.update();
    }

    public void chasing() {
        for (Agent agent : getVisionModule().getVisibleAgents()) {
            if (agent instanceof Intruder) {
                agent.getDirection();
            }
        }
    }

}
