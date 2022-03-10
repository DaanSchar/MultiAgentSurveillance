package nl.maastrichtuniversity.dke.discrete;

import nl.maastrichtuniversity.dke.agents.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class GameSystem {

    private static final Logger logger = LoggerFactory.getLogger(GameSystem.class);
    private final Scenario scenario;

    public GameSystem(Scenario scenario) {
        this.scenario = scenario;
    }

    public void update(double time) {

        for (Agent agent : scenario.getGuards()) {
            moveAgentRandomly(agent);
        }

        resetNoise();

    }

    private void moveAgentRandomly(Agent agent) {
        int rotation = getRandomRotation();

        if (rotation == 0)
            agent.goForward();
        else
            agent.rotate(rotation);
    }

    private int getRandomRotation() {
        if (Math.random() < 0.5)
            return 0;

        if (Math.random() < 0.5)
            return 1;
        else
            return -1;
    }

    public void resetNoise(){
        scenario.getSoundMap().clear();
    }

}
