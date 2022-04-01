package nl.maastrichtuniversity.dke.logic.agents.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Fleet;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.agents.modules.exploration.BrickAndMortar;
import nl.maastrichtuniversity.dke.logic.agents.modules.listening.ListeningModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.memory.MemoryModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration.NoiseModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.movement.MovementModule;
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

        for (int i = 0; i < numOfAgents; i++) { agents.add(buildGuard()); }
        if (DebugSettings.FACTORY) log.info("Created {} Guards", numOfAgents);

        return agents;
    }

    public Fleet<Intruder> buildIntruders(int numOfAgents) {
        Fleet<Intruder> agents = new Fleet<>();

        for (int i = 0; i < numOfAgents; i++) { agents.add(buildIntruder()); }
        if (DebugSettings.FACTORY) log.info("Created {} intruders", numOfAgents);

        return agents;
    }

    public Guard buildGuard() {
        Guard guard = new Guard();
        insertModules(guard);

        return guard;
    }

    public Intruder buildIntruder() {
        Intruder intruder = new Intruder();
        insertModules(intruder);

        return intruder;
    }

    public void insertModules(Agent agent) {
        agent.setSpawnModule(new UniformSpawnModule(scenario))
                .setMovement(new MovementModule(
                        scenario,
                        agent instanceof Guard ? baseSpeedGuards : baseSpeedIntruders,
                        agent instanceof Guard ? 0 : sprintSpeedIntruders))
                .setVisionModule(new VisionModule(scenario, viewingDistance))
                .setCommunicationModule(new CommunicationModule(scenario, getMarkers()))
                .setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting))
                .setMemoryModule(new MemoryModule(scenario))
                .setListeningModule(new ListeningModule(scenario))
                .setSmellModule(new SmellModule(scenario, smellingDistance))
                .setExplorationModule(new BrickAndMortar(agent.getMemoryModule().getMap(), agent.getMovement()));
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
        return markers;
    }

}