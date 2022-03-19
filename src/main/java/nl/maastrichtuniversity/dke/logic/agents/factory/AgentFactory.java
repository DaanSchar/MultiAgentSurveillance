package nl.maastrichtuniversity.dke.logic.agents.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.agents.modules.listening.ListeningModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.memory.MemoryModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration.NoiseModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.movement.Movement;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.smell.SmellModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.spawn.UniformSpawnModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.util.DebugSettings;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Slf4j
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


    public AgentFactory() {}

    public List<Guard> buildGuards(int numOfAgents) {
        ArrayList<Guard> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(buildGuard());

        if (DebugSettings.FACTORY) log.info("Created {} Guards", numOfAgents);

        return agents;
    }

    public List<Intruder> buildIntruders(int numOfAgents) {
        ArrayList<Intruder> agents = new ArrayList<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add(buildIntruder());

        if (DebugSettings.FACTORY) log.info("Created {} intruders", numOfAgents);

        return agents;
    }

    public Guard buildGuard() {
        var guard = new Guard();
        guard.setSpawnModule(new UniformSpawnModule(scenario));
        guard.setMovement(new Movement(scenario, baseSpeedGuards, 0));
        guard.setVisionModule(new VisionModule(scenario, viewingDistance));
        List<CommunicationType> markersGar = new ArrayList<>();
        for(int i = 0; i < numberOfMarkers; i++){
            markersGar.add(CommunicationType.VISIONRED);
            markersGar.add(CommunicationType.VISIONBLUE);
            markersGar.add(CommunicationType.VISIONGREEN);
            markersGar.add(CommunicationType.SMELL);
            markersGar.add(CommunicationType.SOUND);
        }

        guard.setCommunicationModule(new CommunicationModule(scenario, markersGar, smellingDistance));
        guard.setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting));
        guard.setMemoryModule(new MemoryModule(scenario));
        guard.setListeningModule(new ListeningModule(scenario));
        guard.setSmellModule(new SmellModule(scenario));

        return guard;
    }

    public Intruder buildIntruder() {
        var intruder = new Intruder();
        intruder.setSpawnModule(new UniformSpawnModule(scenario));
        intruder.setMovement(new Movement(scenario, baseSpeedIntruders, sprintSpeedIntruders));
        intruder.setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting));
        intruder.setVisionModule(new VisionModule(scenario, viewingDistance));
        List<CommunicationType> markersIntru = new ArrayList<>();
        for(int i = 0; i < numberOfMarkers; i++){
            markersIntru.add(CommunicationType.VISIONRED);
            markersIntru.add(CommunicationType.VISIONBLUE);
            markersIntru.add(CommunicationType.VISIONGREEN);
            markersIntru.add(CommunicationType.SMELL);
            markersIntru.add(CommunicationType.SOUND);
        }
        intruder.setCommunicationModule(new CommunicationModule(scenario, markersIntru,smellingDistance));
        intruder.setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting));
        intruder.setMemoryModule(new MemoryModule(scenario));
        intruder.setListeningModule(new ListeningModule(scenario));
        intruder.setSmellModule(new SmellModule(scenario));

        return intruder;
    }
}

