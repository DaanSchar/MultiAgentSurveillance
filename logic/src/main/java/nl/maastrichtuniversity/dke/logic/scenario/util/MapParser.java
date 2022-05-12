package nl.maastrichtuniversity.dke.logic.scenario.util;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.factory.AgentFactory;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.factory.EnvironmentFactory;
import nl.maastrichtuniversity.dke.logic.scenario.factory.ScenarioFactory;
import nl.maastrichtuniversity.dke.util.DebugSettings;

import java.io.File;
import java.util.Scanner;

@Slf4j
public class MapParser {

    private Scanner scanner;

    private ScenarioFactory scenarioFactory;
    private EnvironmentFactory envBuilder;
    private AgentFactory agentFactory;
    private RandomMapGenerator randomMapGenerator;

    public MapParser(File file) {
        try {
            scanner = new Scanner(file);
            scenarioFactory = new ScenarioFactory();
            agentFactory = new AgentFactory();
        } catch (Exception e) {
            log.error("Error while parsing file: ");
            e.printStackTrace();
        }
    }

    public MapParser() {
        scenarioFactory = new ScenarioFactory();
        agentFactory = new AgentFactory();
    }

    public Scenario createScenario() {
        envBuilder = new EnvironmentFactory();
        randomMapGenerator = new RandomMapGenerator();

        setDefaultValues();

        while (scanner.hasNextLine()) {
            createFieldFromLine(scanner.nextLine());
        }

        scenarioFactory.setEnvironment(randomMapGenerator.build());
        return scenarioFactory.build(agentFactory);
    }

    public Scenario createDefaultScenario(Environment environment) {
        setDefaultValues();
        scenarioFactory.setEnvironment(environment);
        return scenarioFactory.build(agentFactory);
    }

    private void createFieldFromLine(String line) {
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter("=");

        while (lineScanner.hasNext()) {
            String key = lineScanner.next().trim();
            String value = lineScanner.next().trim();

            createScenarioField(key, value);
        }
    }

    private void createScenarioField(String key, String value) {
        String[] values = value.split(" ");

        switch (key) {
            case "name" -> scenarioFactory.setName(value);
            case "gameFile" -> log("GameFile not implemented yet");
            case "gameMode" -> scenarioFactory.setGameMode(Integer.parseInt(value));
            case "height" -> envBuilder.setHeight(Integer.parseInt(value));
            case "width" -> envBuilder.setWidth(Integer.parseInt(value));
            case "scaling" -> scenarioFactory.setScaling(Double.parseDouble(value));
            case "numGuards" -> scenarioFactory.setNumberOfGuards(Integer.parseInt(value));
            case "numIntruders" -> scenarioFactory.setNumberOfIntruders((int) Double.parseDouble(value));
            case "baseSpeedIntruder" -> agentFactory.setBaseSpeedIntruders((int) Double.parseDouble(value));
            case "sprintSpeedIntruder" -> agentFactory.setSprintSpeedIntruders((int) Double.parseDouble(value));
            case "baseSpeedGuard" -> agentFactory.setBaseSpeedGuards((int) Double.parseDouble(value));
            case "timeStep" -> scenarioFactory.setTimeStep(Double.parseDouble(value));
            case "targetArea" -> addArea(values, TileType.TARGET);
            case "spawnAreaIntruders" -> addArea(values, TileType.SPAWN_INTRUDERS);
            case "spawnAreaGuards" -> addArea(values, TileType.SPAWN_GUARDS);
            case "wall" -> addArea(values, TileType.WALL);
            case "teleport" -> addTeleport(value);
            case "shaded" -> addArea(values, TileType.SHADED);
            case "texture" -> log("Texture not implemented yet");
            case "window" -> addArea(values, TileType.WINDOW);
            case "door" -> addArea(values, TileType.DOOR);
            case "sentrytower" -> addArea(values, TileType.SENTRY);
            case "distanceViewing" -> agentFactory.setViewingDistance(Integer.parseInt(value));
            case "distanceHearingWalking" -> agentFactory.setHearingDistanceWalking(Integer.parseInt(value));
            case "distanceHearingSprinting" -> agentFactory.setHearingDistanceSprinting(Integer.parseInt(value));
            case "distanceHearingInteraction" -> agentFactory.setHearingDistanceInteraction((Integer.parseInt(value)));
            case "distanceSmelling", "smellingDistance" -> agentFactory.setSmellingDistance(Integer.parseInt(value));
            case "numberOfMarkers", "numberMarkers" -> agentFactory.setNumberOfMarkers(Integer.parseInt(value));
            default -> log.error("Unknown value: " + key);
        }
    }

    private void setDefaultValues() {
        scenarioFactory.setGameMode(1);
        scenarioFactory.setScaling(0.07);
        scenarioFactory.setNumberOfGuards(1);
        agentFactory.setBaseSpeedIntruders(5);
        agentFactory.setSprintSpeedIntruders(10);
        agentFactory.setBaseSpeedGuards(5);
        scenarioFactory.setTimeStep(1.0);
        agentFactory.setViewingDistance(Math.max(
                agentFactory.getBaseSpeedGuards(),
                agentFactory.getBaseSpeedIntruders()) * 2
        );
        agentFactory.setHearingDistanceWalking(6);
        agentFactory.setHearingDistanceSprinting(10);
        agentFactory.setHearingDistanceInteraction(8);
        agentFactory.setSmellingDistance(7);
        agentFactory.setNumberOfMarkers(5);
    }

    private void addArea(String[] values, TileType type) {
        envBuilder.addArea(
                Integer.parseInt(values[0]),
                Integer.parseInt(values[1]),
                Integer.parseInt(values[2]),
                Integer.parseInt(values[3]),
                type
        );

    }

    private void addTeleport(String value) {
        String[] values = value.split(" ");
        envBuilder.addTeleportArea(
                Integer.parseInt(values[0]),
                Integer.parseInt(values[1]),
                Integer.parseInt(values[2]),
                Integer.parseInt(values[3]),
                Integer.parseInt(values[4]),
                Integer.parseInt(values[5]),
                (int) Double.parseDouble(values[6])
        );

        envBuilder.addTile(Integer.parseInt(values[4]), Integer.parseInt(values[5]), TileType.DESTINATION_TELEPORT);
    }

    private void log(String message) {
        if (DebugSettings.FACTORY) {
            log.info(message);
        }
    }

}
