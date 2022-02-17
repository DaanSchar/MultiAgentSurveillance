package nl.maastrichtuniversity.dke.agents;

import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.movement.Movement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.UniformSpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.discrete.Environment;

import java.util.ArrayList;
import java.util.List;

public class AgentFactory {

    public static Intruder createIntruder(Environment environment, double baseSpeed, double sprintSpeed, double viewingDistance, double hearingDistanceWalking, double hearingDistanceSprinting, double smellingDistance) {
        return new Intruder(
                new UniformSpawnModule(environment),
                new Movement(environment),
                new VisionModule(environment, viewingDistance),
                baseSpeed,
                sprintSpeed
        );
    }

    public static Guard createGuard(Environment environment, double baseSpeed, double sprintSpeed, double viewingDistance, double hearingDistanceWalking, double hearingDistanceSprinting, double smellingDistance) {
        return new Guard(
                new UniformSpawnModule(environment),
                new Movement(environment),
                new VisionModule(environment, viewingDistance),
                baseSpeed,
                sprintSpeed
        );
    }

    public static List<Intruder> createIntruders(int numOfAgents, Environment environment, double baseSpeed, double sprintSpeed, double viewingDistance, double hearingDistanceWalking, double hearingDistanceSprinting, double smellingDistance) {
        ArrayList<Intruder> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(createIntruder(environment, baseSpeed, sprintSpeed, viewingDistance, hearingDistanceWalking, hearingDistanceSprinting, smellingDistance));

        return agents;
    }

    public static List<Guard> createGuards(int numOfAgents, Environment environment, double baseSpeed, double sprintSpeed, double viewingDistance, double hearingDistanceWalking, double hearingDistanceSprinting, double smellingDistance) {
        ArrayList<Guard> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(createGuard(environment, baseSpeed, sprintSpeed, viewingDistance, hearingDistanceWalking, hearingDistanceSprinting, smellingDistance));

        return agents;
    }

}
