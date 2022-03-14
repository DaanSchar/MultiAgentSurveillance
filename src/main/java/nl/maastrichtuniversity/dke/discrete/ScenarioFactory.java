package nl.maastrichtuniversity.dke.discrete;

import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.AgentFactory;

@Setter
public class ScenarioFactory {

    private String name;
    private int gameMode;
    private double scaling;
    private double timeStep;

    private int numberOfGuards;
    private int numberOfIntruders;

    private Environment environment;

    public Scenario build(AgentFactory agentFactory) {
        var scenario = new Scenario(
                name,
                gameMode,
                timeStep,
                scaling,
                environment
        );

        agentFactory.setScenario(scenario);
        scenario.setGuards(agentFactory.buildGuards(numberOfGuards));
        scenario.setIntruders(agentFactory.buildIntruders(numberOfIntruders));

        return scenario;
    }

}
