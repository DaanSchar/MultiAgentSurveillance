package nl.maastrichtuniversity.dke.logic.agents.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Fleet;
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

    public Fleet<Guard> buildGuards(int numOfAgents) {
        Fleet<Guard> agents = new Fleet<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add((Guard)buildAgent("guard"));

        if (DebugSettings.FACTORY) log.info("Created {} Guards", numOfAgents);

        return agents;
    }

    public Fleet<Intruder> buildIntruders(int numOfAgents) {
        Fleet<Intruder> agents = new Fleet<>();

        for (int i = 0; i < numOfAgents; i++)
            agents.add((Intruder) buildAgent("intruder"));

        if (DebugSettings.FACTORY) log.info("Created {} intruders", numOfAgents);

        return agents;
    }

<<<<<<<<< Temporary merge branch 1
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

        guard.setCommunicationModule(new CommunicationModule(scenario, markersGar));
        guard.setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting));
        guard.setMemoryModule(new MemoryModule(scenario));
        guard.setListeningModule(new ListeningModule(scenario));
        guard.setSmellModule(new SmellModule(scenario, smellingDistance));
=========
>>>>>>>>> Temporary merge branch 2

    public Agent buildAgent(String type) {
        Agent agent = type.equals("guard") ? new Guard() : new Intruder();
        agent.setSpawnModule(new UniformSpawnModule(scenario));
        agent.setMovement(new Movement(
                scenario,
                type.equals("guard") ? baseSpeedGuards : baseSpeedIntruders,
                type.equals("guard") ? 0 : sprintSpeedIntruders)
        );
        agent.setVisionModule(new VisionModule(scenario, viewingDistance));
        agent.setCommunicationModule(new CommunicationModule(scenario, getMarkers(),smellingDistance));
        agent.setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting));
        agent.setMemoryModule(new MemoryModule(scenario));
        agent.setListeningModule(new ListeningModule(scenario));
        agent.setSmellModule(new SmellModule(scenario));

        return agent;
    }

    private List<CommunicationType> getMarkers() {
        List<CommunicationType> markers = new ArrayList<>();
        for(int i = 0; i < numberOfMarkers; i++){
            markers.add(CommunicationType.VISIONRED);
            markers.add(CommunicationType.VISIONBLUE);
            markers.add(CommunicationType.VISIONGREEN);
            markers.add(CommunicationType.SMELL);
            markers.add(CommunicationType.SOUND);
        }
<<<<<<<<< Temporary merge branch 1
        intruder.setCommunicationModule(new CommunicationModule(scenario, markersIntru));
        intruder.setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting));
        intruder.setMemoryModule(new MemoryModule(scenario));
        intruder.setListeningModule(new ListeningModule(scenario));
        intruder.setSmellModule(new SmellModule(scenario,smellingDistance));

        return intruder;
=========
        return markers;
>>>>>>>>> Temporary merge branch 2
    }

}

