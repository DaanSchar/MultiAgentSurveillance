package nl.maastrichtuniversity.dke.logic;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

public class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private static File mapFile;
    private static Game game;

    private static final File DEFAULT_MAP = new File("src/main/resources/maps/testmap.txt");

    /**
     * This method is used to get the singleton instance of the game.
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
            logger.info("Creating new game instance.");
            game = new Game();
        } else {
            logger.info("Map file changed, resetting game.");
            game.reset();
        }
    }





    private @Getter Scenario scenario;
    private @Getter double time;

    /**
     * Resets the game by re-reading the map file,
     * setting the time to 0 and re-initializing the agents.
     */
    public void reset() {
        logger.info("Resetting game.");
        scenario = new MapParser(mapFile).createScenario();
        game.time = 0.0;
        init();
    }

    /**
     * Initializes the game.
     */
    public void init() {
        scenario.getGuards().forEach(Agent::spawn);
    }

    /**
     * Updates the state of the game.
     */
    public void update() {
        resetNoise();
        time += scenario.getTimeStep();

        for (int i = 0; i < scenario.getGuards().size(); i++) {
            var action = agentActions.get(i);
            var agent = scenario.getGuards().get(i);

            if (action == 0) {
                agent.goForward(time);
            } else {
                agent.rotate(action, time);
            }
        }

        agentActions.clear();

        for (Agent agent : scenario.getGuards()) {
            agent.listen();
        }
    }

    public void update(int i) {
        resetNoise();
        time += scenario.getTimeStep();

        for (Agent agent : scenario.getGuards()) {
            moveAgentRandomly(agent);
        }

        for (Agent agent : scenario.getGuards()) {
            agent.listen();
        }
    }

    /**
     * Resets the noise map to empty.
     */
    public void resetNoise(){
        scenario.getSoundMap().clear();
    }

    /**
     * Private constructor to prevent instantiation.
     */
    protected Game() {
        this.scenario = new MapParser(mapFile).createScenario();
        this.agentActions = new ArrayList<>();
        this.time = 0.0;
        init();
    }

    /**
     * Moves the agent randomly.
     * @param agent to be moved
     */
    private void moveAgentRandomly(Agent agent) {
        int rotation = getRandomAction();

        if (rotation == 0)
            agent.dropMark(CommunicationType.SMELL);

        else
            agent.goForward(time);
//            agent.rotate(rotation, time);

    }


    /**
     * Can be 0, -1 or 1.
     *
     * @return a random rotation
     */
    private int getRandomAction() {
        if (Math.random() < 0.5)
            return 0;

        if (Math.random() < 0.5)
            return 1;
        else
            return -1;
    }


    private final @Getter ArrayList<Integer> agentActions;



}