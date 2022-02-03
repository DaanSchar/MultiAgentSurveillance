package nl.maastrichtuniversity.dke;

import java.util.List;

public class Scenario {

    private String name;
    private int gameMode;

    private double scaling;
    private double timeStep;

    private List<Agent> agents;

    // private Environment environment;

    public Scenario(String name, int gameMode, double scaling, double timeStep, List<Agent> agents) {
        this.name = name;
        this.agents = agents;
    }





}
