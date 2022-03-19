package nl.maastrichtuniversity.dke.ai;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.gui.GameWindow;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

/**
 * This is the environment that is used in the reinforcement learning.
 * It is kind of like a playground for the agents to play in.
 */
@Slf4j
public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private final DiscreteSpace actionSpace = new DiscreteSpace(Network.NUM_OUTPUTS);
    private Game game;

    private GameWindow window;

    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        var agentActions = game.getAgentActions();
        var totalAgents = game.getScenario().getGuards().size();
        var agents = game.getScenario().getGuards();

        Agent agent;

        if (agentActions.size() == totalAgents) {
            game.update();
            agent = agents.get(0);
        } else {
            game.getAgentActions().add(integer-1);
            agent = agents.get(agentActions.size()-1);
        }

        if (window != null) window.draw();

        return new StepReply<>(
                new NeuralGameState(agent.getStateVector()),
                Reward.calculateReward(game.getScenario()),
                isDone(),
                null
        );
    }

    @Override
    public boolean isDone() {
        var scenario = game.getScenario();
        int totalTiles = scenario.getEnvironment().getHeight() * scenario.getEnvironment().getWidth();

        return scenario.getGuards().getCoveredTiles().size() == totalTiles;
    }

    @Override
    public NeuralGameState reset() {
        if (game == null) { game = Game.getInstance(); }

        double totalCoveredTiles = game.getScenario().getGuards().getCoveredTiles().size();
        double totalTiles = game.getScenario().getEnvironment().getHeight() * game.getScenario().getEnvironment().getWidth();

        log.info("Total coverage: {}%", (totalCoveredTiles / totalTiles) * 100);
        game.reset();

        return new NeuralGameState(game.getScenario().getGuards().get(0).getStateVector());
    }

    @Override
    public MDP<NeuralGameState, Integer, DiscreteSpace> newInstance() { return new Environment(); }

    @Override
    public ObservationSpace<NeuralGameState> getObservationSpace() { return new GameObservationSpace(); }

    @Override
    public DiscreteSpace getActionSpace() { return actionSpace; }

    @Override
    public void close() {}
}
