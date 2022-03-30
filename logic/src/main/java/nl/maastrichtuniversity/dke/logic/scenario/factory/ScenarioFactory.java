package main.java.nl.maastrichtuniversity.dke.logic.scenario.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.java.nl.maastrichtuniversity.dke.logic.agents.factory.AgentFactory;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import main.java.nl.maastrichtuniversity.dke.util.DebugSettings;

@Setter
@Getter
@Slf4j
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

        if (DebugSettings.FACTORY) log.info("Scenario {} created", name);

        return scenario;
    }

}
