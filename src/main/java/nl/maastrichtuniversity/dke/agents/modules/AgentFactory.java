package nl.maastrichtuniversity.dke.agents.modules;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.movement.Movement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.UniformSpawnModule;
import nl.maastrichtuniversity.dke.scenario.Environment;

public class AgentFactory {

    public Agent createIntruder(double baseSpeed, double sprintSpeed, Environment environment) {
        return new Intruder(
                new UniformSpawnModule(environment),
                new Movement(environment),
                new baseSpeed,
                sprintSpeed
        );
    }

}
