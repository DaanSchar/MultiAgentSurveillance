package nl.maastrichtuniversity.dke.agents;

import nl.maastrichtuniversity.dke.agents.modules.movement.Movement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.UniformSpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;

import java.util.ArrayList;
import java.util.List;

public class AgentFactory {

    public static Intruder createIntruder(Scenario scenario, double baseSpeed, double sprintSpeed, double viewingDistance, double hearingDistanceWalking, double hearingDistanceSprinting, double smellingDistance) {
        return new Intruder(
                new UniformSpawnModule(scenario),
                new Movement(scenario),
                new VisionModule(scenario, viewingDistance),
                baseSpeed,
                sprintSpeed
        );
    }

    public static Guard createGuard(Scenario scenario, double baseSpeed, double sprintSpeed, double viewingDistance, double hearingDistanceWalking, double hearingDistanceSprinting, double smellingDistance) {
        return new Guard(
                new UniformSpawnModule(scenario),
                new Movement(scenario),
                new VisionModule(scenario, viewingDistance),
                baseSpeed,
                sprintSpeed
        );
    }

    public static List<Intruder> createIntruders(int numOfAgents, Scenario scenario, double baseSpeed, double sprintSpeed, double viewingDistance, double hearingDistanceWalking, double hearingDistanceSprinting, double smellingDistance) {
        ArrayList<Intruder> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(createIntruder(scenario, baseSpeed, sprintSpeed, viewingDistance, hearingDistanceWalking, hearingDistanceSprinting, smellingDistance));

        return agents;
    }

    public static List<Guard> createGuards(int numOfAgents, Scenario scenario, double baseSpeed, double sprintSpeed, double viewingDistance, double hearingDistanceWalking, double hearingDistanceSprinting, double smellingDistance) {
        ArrayList<Guard> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(createGuard(scenario, baseSpeed, sprintSpeed, viewingDistance, hearingDistanceWalking, hearingDistanceSprinting, smellingDistance));

        return agents;
    }

}
