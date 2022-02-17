package nl.maastrichtuniversity.dke.discrete;

import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.AgentFactory;

@Setter
public class ScenarioFactory {

    private int numberOfIntruders;
    private int numberOfGuards;

    private int baseSpeedGuards;
    private int baseSpeedIntruders;
    private int sprintSpeedGuards;
    private int sprintSpeedIntruders;

    private int viewingDistance;
    private int hearingDistanceWalking;
    private int hearingDistanceSprinting;
    private int smellingDistance;

    private String name;
    private int gameMode;
    private double scaling;
    private double timeStep;

    public Scenario build() {
        var scenario = new Scenario(
                name,
                gameMode,
                timeStep,
                scaling
        );

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

        return scenario;
    }

}
