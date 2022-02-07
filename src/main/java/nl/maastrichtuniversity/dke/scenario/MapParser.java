package nl.maastrichtuniversity.dke.scenario;

import nl.maastrichtuniversity.dke.areas.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Scanner;

public class MapParser {

    private Logger logger = LoggerFactory.getLogger(MapParser.class);
    private final String ROOT = "src/main/resources";

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
            case "name":
                scenario.setName(value);
                break;
            case "gameFile":
                logger.error("GameFile not implemented yet");
                break;
            case "gameMode":
                scenario.setGameMode(Integer.parseInt(value));
                break;
            case "height":
                environment.setHeight(Integer.parseInt(value));
                break;
            case "width":
                scenario.getEnvironment().setWidth(Double.parseDouble(value));
                break;
            case "scaling":
                scenario.setScaling(Double.parseDouble(value));
                break;
            case "numGuards":
                scenario.setNumGuards(Integer.parseInt(value));
                break;
            case "numIntruders":
                scenario.setNumIntruders(Integer.parseInt(value));
                break;
            case "baseSpeedIntruder":
                scenario.setBaseSpeedIntruder(Double.parseDouble(value));
                break;
            case "sprintSpeedIntruder":
                scenario.setSprintSpeedIntruder(Double.parseDouble(value));
                break;
            case "baseSpeedGuard":
                scenario.setBaseSpeedGuard(Double.parseDouble(value));
                break;
            case "timeStep":
                scenario.setTimeStep(Double.parseDouble(value));
                break;
            case "targetArea":
                environment.setTargetArea(parseRectangle(values));
                break;
            case "spawnAreaIntruders":
                environment.getSpawnAreaIntruders().add(parseRectangle(values));
                break;
            case "spawnAreaGuards":
                environment.getSpawnAreaGuards().add(parseRectangle(values));
                break;
            case "wall":
                environment.getWalls().add(parseRectangle(values));
                break;
            case "teleport":
                environment.getTeleportPortals().add(parseRectangle(values));
                break;
            case "shaded":
                environment.getShadedAreas().add(parseRectangle(values));
                break;
            case "texture":
                logger.error("Texture not implemented yet");
                break;
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
