package nl.maastrichtuniversity.dke.scenario;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.movement.Movement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.UniformSpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.VisionModule;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Scenario {

    private String name;
    private int gameMode;

    private double scaling;
    private double timeStep ;

    private List<Agent> guards;
    private List<Agent> intruders;

    private Environment environment;

    private int numGuards;
    private int numIntruders;

    private double baseSpeedIntruder;
    private double sprintSpeedIntruder;
    private double baseSpeedGuard;

    public Scenario() {
        this.guards = new ArrayList<>();
        this.intruders = new ArrayList<>();
        this.environment = new Environment();
    }

    public void createAgents() {
        for (int i = 0; i < numGuards; i++)
            guards.add(new Guard(
                    new UniformSpawnModule(environment),
                    new Movement(environment),
                    new VisionModule(environment, 90),
                    baseSpeedGuard)
            );
        for (int i = 0; i < numIntruders; i++)
            intruders.add(new Intruder(
                    new UniformSpawnModule(environment),
                    new Movement(environment),
                    new VisionModule(environment, 90),
                    baseSpeedIntruder)
            );
    }


}
