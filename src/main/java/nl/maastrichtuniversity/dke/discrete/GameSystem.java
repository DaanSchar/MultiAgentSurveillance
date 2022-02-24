package nl.maastrichtuniversity.dke.discrete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class GameSystem {

    private static final Logger logger = LoggerFactory.getLogger(GameSystem.class);
    private final Scenario scenario;

    private boolean hasMoved = false;

    public GameSystem(Scenario scenario) {
        this.scenario = scenario;
    }

    public void update(double time) {
        var agent = scenario.getGuards().get(0);
        if (Math.random() < 0.50) {
            int rotate;
            double random = new Random().nextDouble();

            if (Math.random() < 0.5)
                rotate = 1;
            else
                rotate = -1;

            agent.rotate(rotate);
        } else {
            agent.goForward();
            System.out.println("size " + scenario.getSoundMap().size());
        }
        hasMoved = !hasMoved;
        resetNoise();

    }

    public void resetNoise(){
        scenario.getSoundMap().clear();
    }

}
