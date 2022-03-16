package nl.maastrichtuniversity.dke.logic;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GameLoop {

    private static final Logger logger = LoggerFactory.getLogger(GameLoop.class);
    private @Getter Scenario scenario;
    private double time;

    public GameLoop(Scenario scenario) {
        this.scenario = scenario;
        this.time = 0.0;
        scenario.getGuards().forEach(Agent::spawn);
    }

    public void update() {
        time += scenario.getTimeStep();

        for (Agent agent : scenario.getGuards()) {
            moveAgentRandomly(agent);
        }

        for (Agent agent : scenario.getGuards()) {
            agent.listen();
        }
    }

    private void moveAgentRandomly(Agent agent) {
        int rotation = getRandomRotation();

        if (rotation == 0)
            agent.goForward(time);
        else
            agent.rotate(rotation, time);
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

    public void reset() {
        scenario = new MapParser(new File("map/testmap.txt")).createScenario();
    }

}