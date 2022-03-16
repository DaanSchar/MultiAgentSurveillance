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

    private final DiscreteSpace actionSpace = new DiscreteSpace(Network.NUM_INPUTS);
    private GameLoop gameLoop;

    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        return new StepReply<>(
                new NeuralGameState(gameLoop.getScenario().getStateVector()),
                0.0,
                isDone(),
                null
        );
    }

    private void updateEnvironment() {
        gameLoop.update();
    }

    @Override
    public boolean isDone() {
        var scenario = gameLoop.getScenario();
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
        gameLoop.reset();
        return new NeuralGameState(gameLoop.getScenario().getStateVector());
    }

    @Override
    public MDP<NeuralGameState, Integer, DiscreteSpace> newInstance() {
        return new Environment();
    }

    @Override
    public ObservationSpace<NeuralGameState> getObservationSpace() { return new GameObservationSpace(); }

    @Override
    public DiscreteSpace getActionSpace() { return actionSpace; }

    @Override
    public void close() {  }
}
