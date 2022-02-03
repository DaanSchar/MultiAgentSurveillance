package nl.maastrichtuniversity.dke;

import java.util.List;

public class Scenario {

    private String name;
    private int gameMode;

    private double scaling;
    private double timeStep;

    private List<Agent> agents;
    private Environment environment;

    public Scenario(String name, int gameMode, double scaling, double timeStep, List<Agent> agents, Environment environment) {
        this.name = name;
        this.agents = agents;
        this.gameMode = gameMode;
        this.scaling = scaling;
        this.timeStep = timeStep;
        this.environment = environment;
    }

    public String getName() {
        return name;
    }

    public double getScaling() {
        return scaling;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public int getGameMode() {
        return gameMode;
    }

    public List<Agent> getAgents() {
        return agents;
    }
}
