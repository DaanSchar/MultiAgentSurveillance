package nl.maastrichtuniversity.dke.agents.modules.exploration;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.Game;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

@Slf4j
public class IntruderMDP implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private final Game game;
    private int intruderIndex;
    private final int[] actionList;

    private final ArrayObservationSpace<NeuralGameState> gameArrayObservationSpace;
    private final int observationSize = Train.OBSERVATION_SIZE;

    // action space for the 5 moves agent can do : MOVE_FORWARD,SPRINT_FORWARD,ROTATE_LEFT,ROTATE_RIGHT,STAND_STILL.
    // also added place marker, break glass and toggle door to move actions.
    private final DiscreteSpace discreteSpace = new DiscreteSpace(5);


    public IntruderMDP(Game game) {
        this.game = game;
        this.gameArrayObservationSpace = new ArrayObservationSpace<>(new int[]{observationSize});
        this.actionList = new int[game.getScenario().getIntruders().size()];
        this.intruderIndex = 0;
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
        NeuralGameState observation = new NeuralGameState(
                new double[game.getScenario().getGuards().get(0).getPolicyModule().getInputSize()]
        );

        actionList[intruderIndex] = action;
        intruderIndex++;

        if (intruderIndex == game.getScenario().getIntruders().size()) {
            intruderIndex = 0;
            game.getScenario().getIntruders().executeActions(actionList);
            game.update();
            reward = game.getScenario().getIntruders().getFleeReward(); // changed to Flee Reward
        }

        if (allIntrudersCaught()) {
            reward -= 2000;
        }

        if (game.getScenario().getIntruders().size() > 0) {
            observation = new NeuralGameState(game.getScenario().getIntruders().getCurrentAgent().toArray());
        }

        return new StepReply<>(observation, reward, isDone(), null);
    }

    private boolean allIntrudersCaught() {
        return game.getScenario().getIntruders().size() == 0;
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
