package nl.maastrichtuniversity.dke.reinforcement;

import nl.maastrichtuniversity.dke.logic.GameLoop;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import java.io.File;
import java.util.LinkedList;

/**
 * This is the environment that is used in the reinforcement learning.
 * It is kind of like a playground for the agents to play in.
 */
public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private Scenario scenario;
    private final DiscreteSpace actionSpace = new DiscreteSpace(Network.NUM_INPUTS);

    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        GameLoop system = new GameLoop(scenario);
        system.update(scenario.getTimeStep());

        return new StepReply<>(
                new NeuralGameState(scenario.getStateVector()),
                0.0,
                isDone(),
                null
        );
    }

    @Override
    public boolean isDone() {
        int numberOfTiles = scenario.getEnvironment().getHeight() * scenario.getEnvironment().getWidth();
        LinkedList<Tile> exploredTiles = new LinkedList<>();

        for (Agent a : scenario.getGuards()) {
            for (Tile[] tCol : a.getMemoryModule().getMap().getTileMap()) {
                for (Tile t : tCol) {
                    if (!exploredTiles.contains(t)) {
                        exploredTiles.add(t);
                    }
                    if (exploredTiles.size() == numberOfTiles) {
                        return true;
                    }
                }
            }
        }
        return exploredTiles.size() == numberOfTiles;
    }

    @Override
    public NeuralGameState reset() {
        scenario = getNewGame();
        return new NeuralGameState(scenario.getStateVector());
    }

    private Scenario getNewGame() { return new MapParser(new File("maps/testmap.txt")).createScenario(); }

    @Override
    public MDP<NeuralGameState, Integer, DiscreteSpace> newInstance() {
        return new Environment();
    }

    @Override
    public ObservationSpace<NeuralGameState> getObservationSpace() { return new GameObservationSpace(); }

    @Override
    public DiscreteSpace getActionSpace() { return actionSpace; }

    @Override
    public void close() {}
}
