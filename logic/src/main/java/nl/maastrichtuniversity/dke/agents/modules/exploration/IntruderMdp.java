package nl.maastrichtuniversity.dke.agents.modules.exploration;

import nl.maastrichtuniversity.dke.Game;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

public class IntruderMdp implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private final Game game;
    private int intruder_idx;
    private final int[] actionList;

    private final ArrayObservationSpace<NeuralGameState> gameArrayObservationSpace;

    // action space for the 5 moves agent can do : MOVE_FORWARD,SPRINT_FORWARD,ROTATE_LEFT,ROTATE_RIGHT,STAND_STILL.
    // also added place marker, break glass and toggle door to move actions.
    private final DiscreteSpace discreteSpace = new DiscreteSpace(5);


    public IntruderMdp(Game game) {
        this.game = game;
        gameArrayObservationSpace = new ArrayObservationSpace<>(new int[]{game.observationSize()});
        actionList = new int[game.getScenario().getIntruders().size()];
        intruder_idx = 0;
    }

    @Override
    public ObservationSpace<NeuralGameState> getObservationSpace() {
        return gameArrayObservationSpace;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return discreteSpace;
    }

    @Override
    public NeuralGameState reset() {
        game.reset();
        return new NeuralGameState(game.getScenario().getIntruders().getCurrentAgent().toArray());
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<NeuralGameState> step(Integer action) {
        double reward = 0;
        actionList[intruder_idx] = action;
        intruder_idx++;

        if (intruder_idx == game.getScenario().getIntruders().size()) {

            intruder_idx =0;
            game.getScenario().getIntruders().executeActions(actionList);
            game.update();
            reward = game.getScenario().getIntruders().getReward();
        }

        NeuralGameState observation = new NeuralGameState(game.getScenario().getIntruders().getCurrentAgent().toArray());
        return new StepReply<>(observation, reward, isDone(), null);
    }


    @Override
    public boolean isDone() {
        return game.isDone();
    }

    @Override
    public MDP<NeuralGameState, Integer, DiscreteSpace> newInstance() {
        return null;
    }
}