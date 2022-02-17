package nl.maastrichtuniversity.dke.discrete;

public class GameSystem {

    private final Scenario scenario;

    public GameSystem(Scenario scenario) {
        this.scenario = scenario;
    }

    public void update() {
        var agent = scenario.getGuards().get(0);
        agent.goForward();
    }

}
