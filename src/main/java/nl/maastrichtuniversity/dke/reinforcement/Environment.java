package nl.maastrichtuniversity.dke.reinforcement;

import nl.maastrichtuniversity.dke.logic.GameLoop;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import java.io.File;

/**
 * This is the environment that is used in the reinforcement learning.
 * It is kind of like a playground for the agents to play in.
 */
public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private Scenario scenario;
    private DiscreteSpace actionSpace = new DiscreteSpace(Network.NUM_INPUTS);

    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        GameLoop system = new GameLoop(scenario);
        system.update(scenario.getTimeStep());

        return null;
    }

    @Override
    public boolean isDone() {
        return false;
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
