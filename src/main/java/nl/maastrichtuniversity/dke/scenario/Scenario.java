package nl.maastrichtuniversity.dke.scenario;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.spawn.UniformSpawnModule;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private @Getter @Setter String name;
    private @Getter @Setter int gameMode;

    private @Getter @Setter double scaling;
    private @Getter @Setter double timeStep ;

    private @Getter @Setter List<Agent> guards;
    private @Getter @Setter List<Agent> intruders;

    private @Getter @Setter Environment environment;

    private @Getter @Setter int numGuards;
    private @Getter @Setter int numIntruders;

    private @Getter @Setter double baseSpeedIntruder;
    private @Getter @Setter double sprintSpeedIntruder;
    private @Getter @Setter double baseSpeedGuard;

    public Scenario(Environment environment) {
        this.guards = new ArrayList<>();
        this.intruders = new ArrayList<>();
        this.environment = environment;
    }

    public Scenario(String name, int gameMode, double scaling, double timeStep, List<Agent> agents, Environment environment) {
        this.name = name;
        this.guards = agents;
        this.gameMode = gameMode;
        this.scaling = scaling;
        this.timeStep = timeStep;
        this.environment = environment;
    }

    public void createAgents() {

        for (int i = 0; i < numGuards; i++)
            guards.add(new Guard(
                    new UniformSpawnModule(environment), baseSpeedGuard));

        for (int i = 0; i < numIntruders; i++)
            intruders.add(new Intruder(
                    new UniformSpawnModule(environment), baseSpeedIntruder));


    }


}
