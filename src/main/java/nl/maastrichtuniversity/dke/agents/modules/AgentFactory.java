package nl.maastrichtuniversity.dke.agents.modules;

import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.movement.Movement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.UniformSpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.StaticEnvironment;

import java.util.ArrayList;
import java.util.List;

public class AgentFactory {

    public Intruder createIntruder(Scenario scenario, double baseSpeed, double sprintSpeed, double fov) {
        return new Intruder(
                new UniformSpawnModule(scenario),
                new Movement(scenario),
                new VisionModule(scenario, fov),
                baseSpeed,
                sprintSpeed
        );
    }

    public Guard createGuard(Scenario scenario, double baseSpeed, double sprintSpeed, double fov) {
        return new Guard(
                new UniformSpawnModule(scenario),
                new Movement(scenario),
                new VisionModule(scenario, fov),
                baseSpeed,
                sprintSpeed
        );
    }

    public List<Intruder> createIntruders(int numOfAgents, Scenario scenario, double baseSpeed, double sprintSpeed, double fov) {
        ArrayList<Intruder> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(createIntruder(scenario, baseSpeed, sprintSpeed, fov));

        return agents;
    }

    public List<Guard> createGuards(int numOfAgents, Scenario scenario, double baseSpeed, double sprintSpeed, double fov) {
        ArrayList<Guard> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(createGuard(scenario, baseSpeed, sprintSpeed, fov));

        return agents;
    }

}
