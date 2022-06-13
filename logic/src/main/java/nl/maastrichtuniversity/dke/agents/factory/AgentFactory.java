package nl.maastrichtuniversity.dke.agents.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Fleet;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.ActionTimer;
import nl.maastrichtuniversity.dke.agents.modules.approximation.ApproximationModule;
import nl.maastrichtuniversity.dke.agents.modules.interaction.InteractionModule;
import nl.maastrichtuniversity.dke.agents.modules.exploration.BrickAndMortar;
import nl.maastrichtuniversity.dke.agents.modules.policy.PolicyModule;
import nl.maastrichtuniversity.dke.agents.modules.reward.RewardModule;
import nl.maastrichtuniversity.dke.agents.modules.sound.ListeningModule;
import nl.maastrichtuniversity.dke.agents.modules.memory.MemoryModule;
import nl.maastrichtuniversity.dke.agents.modules.pathfind.Dijkstra;
import nl.maastrichtuniversity.dke.agents.modules.sound.NoiseModule;
import nl.maastrichtuniversity.dke.agents.modules.movement.MovementModule;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationModule;
import nl.maastrichtuniversity.dke.agents.modules.runningAway.RunningAway;
import nl.maastrichtuniversity.dke.agents.modules.smell.SmellModule;
import nl.maastrichtuniversity.dke.agents.modules.spawn.UniformSpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.RayCast;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationType;
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
    private int hearingDistanceInteraction;
    private int smellingDistance;

    private int numberOfMarkers;

    public AgentFactory() {
    }

    public Fleet<Guard> buildGuards(int numOfAgents) {
        Fleet<Guard> agents = new Fleet<>();

        for (int i = 0; i < numOfAgents; i++) {
            agents.add(buildGuard());
        }
        if (DebugSettings.FACTORY) {
            log.info("Created {} Guards", numOfAgents);
        }

        return agents;
    }

    public Fleet<Intruder> buildIntruders(int numOfAgents) {
        Fleet<Intruder> agents = new Fleet<>();

        for (int i = 0; i < numOfAgents; i++) {
            agents.add(buildIntruder());
        }
        if (DebugSettings.FACTORY) {
            log.info("Created {} intruders", numOfAgents);
        }

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
        intruder.setRunningAway(new RunningAway(scenario));

        return intruder;
    }

    public void insertModules(Agent agent) {
        agent.setSpawnModule(new UniformSpawnModule(scenario))
                .setActionTimer(new ActionTimer(scenario))
                .setMovement(new MovementModule(
                        scenario,
                        agent.getActionTimer(),
                        agent instanceof Guard ? baseSpeedGuards : baseSpeedIntruders,
                        agent instanceof Guard ? sprintSpeedIntruders : sprintSpeedIntruders))
                .setVisionModule(new RayCast(scenario, viewingDistance))
                .setCommunicationModule(new CommunicationModule(scenario, getMarkers()))
                .setNoiseModule(new NoiseModule(scenario, hearingDistanceWalking, hearingDistanceSprinting,
                        hearingDistanceInteraction))
                .setMemoryModule(new MemoryModule(scenario))
                .setApproximationModule(new ApproximationModule(scenario))
                .setListeningModule(new ListeningModule(scenario, agent.getApproximationModule()))
                .setSmellModule(new SmellModule(scenario, smellingDistance))
                .setExplorationModule(new BrickAndMortar(agent.getMemoryModule().getMap(), agent.getMovement()))
                .setInteractionModule(new InteractionModule(scenario))
                .setPathFinderModule(new Dijkstra(agent.getMemoryModule().getMap()))
                .setRewardModule(new RewardModule(scenario))
                .setPolicyModule(new PolicyModule("intruder-flee.bin"));
    }

    private List<CommunicationType> getMarkers() {
        List<CommunicationType> markers = new ArrayList<>();
        for (int i = 0; i < numberOfMarkers; i++) {
            markers.add(CommunicationType.VISION_RED);
            markers.add(CommunicationType.VISION_BLUE);
            markers.add(CommunicationType.VISION_GREEN);
            markers.add(CommunicationType.SMELL);
            markers.add(CommunicationType.SOUND);
        }
        return markers;
    }

}
