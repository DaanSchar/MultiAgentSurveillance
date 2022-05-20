package nl.maastrichtuniversity.dke.agents.modules;

import nl.maastrichtuniversity.dke.scenario.Scenario;

public class ActionTimer extends AgentModule {

    private double initialTime = -1;
    private double timeOfLastAction;

    public ActionTimer(Scenario scenario) {
        super(scenario);
        this.timeOfLastAction = initialTime;
    }

    public boolean performAction(double actionSpeed) {
        if (enoughTimeHasElapsedSinceLastAction(actionSpeed)) {
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
