package nl.maastrichtuniversity.dke.scenario;

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

    private Logger logger = LoggerFactory.getLogger(MapParser.class);
    private final String ROOT = "src/main/resources/maps/";

    private Scanner scanner;
    private Scenario scenario;

    public MapParser(String file) {
        try {
            scanner = new Scanner(new File(ROOT + file));
            scenario = new Scenario(new Environment());
        } catch (Exception e) {
            logger.error("Error while parsing file: " + ROOT + file);
            e.printStackTrace();
        }
    }

    /**
     * Creates a scenario object from the given input file
     * @return scenario
     */
    public Scenario parse() {
        while (scanner.hasNextLine())
            parseLine(scanner.nextLine());

        scenario.createAgents();
        return scenario;
    }

    private void parseLine(String line) {
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter("=");

        while(lineScanner.hasNext()) {
            String key = lineScanner.next().trim();
            String value = lineScanner.next().trim();

            createVariables(key, value);
        }
    }

    private void createVariables(String key, String value) {
        String[] values = value.split(" ");
        Environment environment = scenario.getEnvironment();

        switch (key) {
            case "name" -> scenario.setName(value);
            case "gameFile" -> logger.error("GameFile not implemented yet");
            case "gameMode" -> scenario.setGameMode(Integer.parseInt(value));
            case "height" -> environment.setHeight(Integer.parseInt(value));
            case "width" -> scenario.getEnvironment().setWidth(Double.parseDouble(value));
            case "scaling" -> scenario.setScaling(Double.parseDouble(value));
            case "numGuards" -> scenario.setNumGuards(Integer.parseInt(value));
            case "numIntruders" -> scenario.setNumIntruders(Integer.parseInt(value));
            case "baseSpeedIntruder" -> scenario.setBaseSpeedIntruder(Double.parseDouble(value));
            case "sprintSpeedIntruder" -> scenario.setSprintSpeedIntruder(Double.parseDouble(value));
            case "baseSpeedGuard" -> scenario.setBaseSpeedGuard(Double.parseDouble(value));
            case "timeStep" -> scenario.setTimeStep(Double.parseDouble(value));
            case "targetArea" -> environment.getTargetArea().add(parseRectangle(values));
            case "spawnAreaIntruders" -> environment.getSpawnAreaIntruders().add(parseRectangle(values));
            case "spawnAreaGuards" -> environment.getSpawnAreaGuards().add(parseRectangle(values));
            case "wall" -> environment.getWalls().add(parseRectangle(values));
            case "teleport" -> environment.getTeleportPortals().add(parseRectangle(values));
            case "shaded" -> environment.getShadedAreas().add(parseRectangle(values));
            case "texture" -> logger.error("Texture not implemented yet");
            case "windows" -> environment.getWindows().add(parseRectangle(values));
            case "doors" -> environment.getDoors().add(parseRectangle(values));
            case "sentrytowers" -> environment.getSentryTowers().add(parseRectangle(values));
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
