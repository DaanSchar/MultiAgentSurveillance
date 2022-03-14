package nl.maastrichtuniversity.dke.agents;

import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.modules.listening.ListeningModule;
import nl.maastrichtuniversity.dke.agents.modules.memory.MemoryModule;
import nl.maastrichtuniversity.dke.agents.modules.noiseGeneration.NoiseModule;
import nl.maastrichtuniversity.dke.agents.modules.movement.Movement;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationModule;
import nl.maastrichtuniversity.dke.agents.modules.spawn.UniformSpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.agents.modules.listening.ListeningModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Setter
public class AgentFactory {

    private Scenario scenario;

    private int baseSpeedGuards;

    private int baseSpeedIntruders;
    private int sprintSpeedIntruders;

    private int viewingDistance;
    private int hearingDistanceWalking;
    private int hearingDistanceSprinting;
    private int smellingDistance;

    private int numberOfMarkers;

    private static Logger logger = LoggerFactory.getLogger(AgentFactory.class);

    public AgentFactory() {}

    public List<Guard> buildGuards(int numOfAgents) {
        ArrayList<Guard> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(buildGuard());

        logger.info("Created {} Guards", numOfAgents);

        return agents;
    }

    public List<Intruder> buildIntruders(int numOfAgents) {
        ArrayList<Intruder> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(buildIntruder());

        logger.info("Created {} intruders", numOfAgents);

        return agents;
    }

    public Guard buildGuard() {
        var guard = new Guard();
        guard.setSpawnModule(new UniformSpawnModule(scenario));
        guard.setMovement(new Movement(scenario, baseSpeedGuards, 0));
        guard.setVisionModule(new VisionModule(scenario, viewingDistance));
        guard.setCommunicationModule(new CommunicationModule(scenario, numberOfMarkers));
        guard.setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting));
        guard.setMemoryModule(new MemoryModule(scenario));
        guard.setListeningModule(new ListeningModule(scenario));

        return guard;
    }

    public Intruder buildIntruder() {
        var intruder = new Intruder();
        intruder.setSpawnModule(new UniformSpawnModule(scenario));
        intruder.setMovement(new Movement(scenario, baseSpeedIntruders, sprintSpeedIntruders));
        intruder.setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting));
        intruder.setVisionModule(new VisionModule(scenario, viewingDistance));
        intruder.setCommunicationModule(new CommunicationModule(scenario, numberOfMarkers));
        intruder.setMemoryModule(new MemoryModule(scenario));
        intruder.setListeningModule(new ListeningModule(scenario));

        return intruder;
    }
}

