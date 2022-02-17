package nl.maastrichtuniversity.dke.discrete;

import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.AgentFactory;

@Setter
public class ScenarioFactory {

    private int numberOfIntruders;
    private int numberOfGuards;

    private double baseSpeedGuards;
    private double baseSpeedIntruders;
    private double sprintSpeedGuards;
    private double sprintSpeedIntruders;

    private int viewingDistance;
    private int hearingDistanceWalking;
    private int hearingDistanceSprinting;
    private int smellingDistance;

    public void build() {
        var scenario = new Scenario();
        scenario.setGuards(AgentFactory.createGuards(
                numberOfGuards,
                scenario,
                baseSpeedGuards,
                sprintSpeedGuards,
                viewingDistance,
                hearingDistanceWalking,
                hearingDistanceSprinting,
                smellingDistance
        ));
        scenario.setIntruders(AgentFactory.createIntruders(
                numberOfIntruders,
                scenario,
                baseSpeedIntruders,
                sprintSpeedIntruders,
                viewingDistance,
                hearingDistanceWalking,
                hearingDistanceSprinting,
                smellingDistance
        ));
    }

}
