package nl.maastrichtuniversity.dke.agents.modules;

import nl.maastrichtuniversity.dke.scenario.Scenario;

public class ActionTimer extends AgentModule{

    private double timeOfLastAction;

    public ActionTimer(Scenario scenario) {
        super(scenario);
        this.timeOfLastAction = -1;
    }

    public boolean performAction(double actionSpeed) {
        if (enoughTimeHasElapsedSinceLastAction(actionSpeed)) {
            this.timeOfLastAction = getCurrentTime();
            return true;
        }

        return false;
    }

    public boolean canPerformAction(double actionSpeed) {
        return enoughTimeHasElapsedSinceLastAction(actionSpeed);
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
