package nl.maastrichtuniversity.dke.agents.modules.exploration;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscrete;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.network.dqn.DQNFactory;
import org.deeplearning4j.rl4j.network.dqn.IDQN;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.Encodable;

public class QLearningDiscreteCustom<OBSERVATION extends Encodable> extends QLearningDiscrete<OBSERVATION> {

    public QLearningDiscreteCustom(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, QLearningConfiguration conf) {
        this(mdp, new DQNNetwork(), conf);
    }

    public QLearningDiscreteCustom(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, DQNFactory factory,
                                   QLearningConfiguration conf) {
        this(mdp, factory.buildDQN(mdp.getObservationSpace().getShape(), mdp.getActionSpace().getSize()), conf);
    }

    public QLearningDiscreteCustom(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, IDQN dqn, QLearningConfiguration conf) {
        super(mdp, dqn, conf, conf.getEpsilonNbStep());
    }

}
