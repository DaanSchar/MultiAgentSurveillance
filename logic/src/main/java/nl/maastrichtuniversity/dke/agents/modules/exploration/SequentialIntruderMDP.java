package nl.maastrichtuniversity.dke.agents.modules.exploration;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.Game;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

@Slf4j
public class SequentialIntruderMDP implements MDP<SequentialGameState, Integer, DiscreteSpace>{

    private final Game game;
    private int intruderIndex;
    private final int[] actionList;

    private final ArrayObservationSpace<SequentialGameState> gameArrayObservationSpace;
    private final int observationSize = Train.OBSERVATION_SIZE;
    private final DiscreteSpace discreteSpace = new DiscreteSpace(5);

    public SequentialIntruderMDP(Game game) {
        this.game = game;
        this.gameArrayObservationSpace = new ArrayObservationSpace<>(new int[]{observationSize});
        this.actionList = new int[game.getScenario().getIntruders().size()];
        this.intruderIndex = 0;
    }

    @Override
    public StepReply<SequentialGameState> step(Integer action) {
        double reward = 0;

        SequentialGameState observation = new SequentialGameState(
                new double[game.getScenario().getGuards().get(0).getPolicyModule().getInputSize()][2]
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
            observation = new SequentialGameState(game.getScenario().getIntruders().getCurrentAgent().toTimeArray());
        }

        return new StepReply<>(observation, reward, isDone(), null);
    }

    @Override
    public ObservationSpace<SequentialGameState> getObservationSpace() {
        return gameArrayObservationSpace;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return discreteSpace;
    }

    @Override
    public SequentialGameState reset() {
        game.reset();
        return new SequentialGameState(game.getScenario().getIntruders().getCurrentAgent().toTimeArray());
    }

    @Override
    public void close() {

    }

    private boolean allIntrudersCaught() {
        return game.getScenario().getIntruders().size() == 0;
    }

    @Override
    public boolean isDone() {
        return game.isDone();
    }

    @Override
    public MDP<SequentialGameState, Integer, DiscreteSpace> newInstance() {
        return null;
    }

}
