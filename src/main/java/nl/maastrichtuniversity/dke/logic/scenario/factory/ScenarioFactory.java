package nl.maastrichtuniversity.dke.logic.scenario.factory;

import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.factory.AgentFactory;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

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
