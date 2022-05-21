package nl.maastrichtuniversity.dke.agents.modules;

import lombok.Getter;
import nl.maastrichtuniversity.dke.scenario.Scenario;

public class ActionTimer extends AgentModule {

    private double initialTime = -1;
    private double timeOfLastAction;
    private @Getter int totalActions;

    public ActionTimer(Scenario scenario) {
        super(scenario);
        this.totalActions = 0;
        this.timeOfLastAction = initialTime;
    }

    public boolean performAction(double actionSpeed) {
        if (enoughTimeHasElapsedSinceLastAction(actionSpeed)) {
            this.totalActions++;
            this.timeOfLastAction = getCurrentTime();
            return true;
        }

        return false;
    }

    private boolean enoughTimeHasElapsedSinceLastAction(double speed) {
        return getElapsedTimeSinceLastMove() >= (1.0 / speed);
    }

    private double getCurrentTime() {
        return scenario.getCurrentTime();
    }

    private double getElapsedTimeSinceLastMove() {
        return getCurrentTime() - timeOfLastAction;
    }
}
