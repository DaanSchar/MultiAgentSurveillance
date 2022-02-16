package nl.maastrichtuniversity.dke.scenario;

import nl.maastrichtuniversity.dke.agents.modules.AgentFactory;
import nl.maastrichtuniversity.dke.areas.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Scanner;

/**
 * reads a map file to return a scenario object.
 *
 * @Author Daan
 */
public class MapParser {

    private static final Logger logger = LoggerFactory.getLogger(MapParser.class);

    private Scanner scanner;
    private StaticEnvironment staticEnvironment;
    private DynamicEnvironment dynamicEnvironment;

    private Scenario scenario;

    private int numberOfGuards;
    private int numberOfIntruders;
    private double baseSpeedIntruder;
    private double baseSpeedGuard;
    private double sprintSpeedIntruder;
    private String name;
    private int gameMode;
    private double timeStep;
    private double fov;


    public MapParser(String file) {
        try {
            scanner = new Scanner(new File(file));
        } catch (Exception e) {
            logger.error("Error while parsing file: " +  file);
            e.printStackTrace();
        }
    }

    /**
     * Creates a scenario object from the given input file
     * @return scenario
     */
    public Scenario createScenario() {
        dynamicEnvironment = new DynamicEnvironment();
        staticEnvironment = new StaticEnvironment();

        while (scanner.hasNextLine())
            createFieldFromLine(scanner.nextLine());

        scenario = new Scenario();
        createAgents();

        scenario.setDynamicEnvironment(dynamicEnvironment);
        scenario.setStaticEnvironment(staticEnvironment);


        return scenario;
    }

    public void createAgents() {
        AgentFactory agentFactory = new AgentFactory();

        this.dynamicEnvironment.setGuards(agentFactory.createGuards(
                this.numberOfGuards,
                this.scenario,
                this.baseSpeedGuard,
                this.baseSpeedGuard,
                this.fov
        ));
        this.dynamicEnvironment.setIntruders(agentFactory.createIntruders(
                this.numberOfIntruders,
                this.scenario,
                this.baseSpeedIntruder,
                this.sprintSpeedIntruder,
                this.fov
        ));
    }

    private void createFieldFromLine(String line) {
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter("=");

        while(lineScanner.hasNext()) {
            String key = lineScanner.next().trim();
            String value = lineScanner.next().trim();

            createScenarioField(key, value);
        }
    }

    private void createScenarioField(String key, String value) {
        String[] values = value.split(" ");

        switch (key) {
            case "name" -> name = value;
            case "gameFile" -> logger.error("GameFile not implemented yet");
            case "gameMode" -> gameMode = (Integer.parseInt(value));
            case "height" -> staticEnvironment.setHeight(Integer.parseInt(value));

            case "width" -> staticEnvironment.setWidth(Double.parseDouble(value));
            case "scaling" -> staticEnvironment.setScaling(Double.parseDouble(value));
            case "numGuards" -> numberOfGuards = (Integer.parseInt(value));
            case "numIntruders" -> numberOfIntruders = (Integer.parseInt(value));
            case "baseSpeedIntruder" -> baseSpeedIntruder = (Double.parseDouble(value));
            case "sprintSpeedIntruder" -> sprintSpeedIntruder = (Double.parseDouble(value));
            case "baseSpeedGuard" -> baseSpeedGuard = (Double.parseDouble(value));
            case "timeStep" -> timeStep = (Double.parseDouble(value));
            case "targetArea" -> staticEnvironment.get("targetArea").add( parseRectangle(values));
            case "spawnAreaIntruders" -> staticEnvironment.get("spawnAreaIntruders").add( parseRectangle(values));
            case "spawnAreaGuards" -> staticEnvironment.get("spawnAreaGuards").add( parseRectangle(values));
            case "wall" -> staticEnvironment.get("wall").add( parseRectangle(values));
            case "teleport" -> staticEnvironment.get("teleport").add( parseRectangle(values));
            case "shaded" -> staticEnvironment.get("shaded").add( parseRectangle(values));
            case "texture" -> logger.error("Texture not implemented yet");
            case "window" -> dynamicEnvironment.getWindows().add( parseRectangle(values));
            case "door" -> dynamicEnvironment.getDoors().add( parseRectangle(values));
            case "sentrytower" -> staticEnvironment.get("sentrytower").add( parseRectangle(values));
            case "fov" -> fov = (Double.parseDouble(value));
            default -> logger.error("Unknown value: " + key);
        }
    }

    private Rectangle parseRectangle(String[] values) {
        return new Rectangle(
                Integer.parseInt(values[0]),
                Integer.parseInt(values[1]),
                Integer.parseInt(values[2]),
                Integer.parseInt(values[3])
        );
    }

}
