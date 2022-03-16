package nl.maastrichtuniversity.dke.reinforcement;

import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import java.io.File;

public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private Scenario scenario;


    @Override
    public ObservationSpace<NeuralGameState> getObservationSpace() {
        return null;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return null;
    }

    @Override
    public NeuralGameState reset() {
        scenario = getNewGame();

        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        return null;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public MDP<NeuralGameState, Integer, DiscreteSpace> newInstance() {
        return new Environment();
    }

    private Scenario getNewGame() {
        return new MapParser(new File("maps/testmap.txt")).createScenario();
    }
}
