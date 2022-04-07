package nl.maastrichtuniversity.dke.logic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.Fleet;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;

import java.io.File;

@Slf4j
public class Game {

    private static File mapFile;
    private static Game game;

    private static final File DEFAULT_MAP = new File("src/main/resources/maps/testmap.txt");

    /**
     * This method is used to get the singleton instance of the game.
     *
     * @return the only allowed instance of the game.
     */
    public static Game getInstance() {
        if (game == null) {
            setMapFile(DEFAULT_MAP);
        }

        return game;
    }

    public static void setMapFile(File mapFile) {
        Game.mapFile = mapFile;

        if (game == null) {
            log.info("Creating new game instance.");
            game = new Game();
        } else {
            log.info("Map file changed, resetting game.");
            game.reset();
        }
    }


    private final @Getter
    Scenario scenario;
    private @Getter
    double time;

    /**
     * Resets the game by re-reading the map file,
     * setting the time to 0 and re-initializing the agents.
     */
    public void reset() {
        game.time = 0.0;
        init();
    }

    /**
     * Initializes the game.
     */
    public void init() {
        scenario.getGuards().forEach(Guard::spawn);
    }

    public void update() {
        resetNoise();
        time += scenario.getTimeStep();

        updateGuards();
    }

    private void updateGuards() {
        Fleet<Guard> guards = scenario.getGuards();
        final double half = 0.5;
        double x = Math.random();
        if (x < half) {
            guards.forEach(Guard::explore);

        } else {
            guards.forEach(guard -> guard.dropMark(CommunicationType.VISION_BLUE));
        }
        guards.forEach(Guard::listen);


    }


    /**
     * Resets the noise map to empty.
     */
    public void resetNoise() {
        scenario.getSoundMap().clear();
    }

    /**
     * Private constructor to prevent instantiation.
     */
    protected Game() {
        this.scenario = new MapParser(mapFile).createScenario();
        this.time = 0.0;
        init();
    }


}