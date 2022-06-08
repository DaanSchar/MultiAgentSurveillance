package nl.maastrichtuniversity.dke.agents.modules.exploration;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.QLearning;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscrete;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQNFactory;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.network.dqn.IDQN;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.Encodable;
import org.deeplearning4j.rl4j.util.DataManagerTrainingListener;
import org.deeplearning4j.rl4j.util.IDataManager;

public class QLearningDiscreteRecurrent<OBSERVATION extends Encodable> extends QLearningDiscrete<OBSERVATION> {

    @Deprecated
    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, IDQN dqn, QLearning.QLConfiguration conf,
                                      IDataManager dataManager) {
        this(mdp, dqn, conf);
        addListener(new DataManagerTrainingListener(dataManager));
    }

    @Deprecated
    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, IDQN dqn, QLearning.QLConfiguration conf) {
        super(mdp, dqn, conf.toLearningConfiguration(), conf.getEpsilonNbStep());
    }

    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, IDQN dqn, QLearningConfiguration conf) {
        super(mdp, dqn, conf, conf.getEpsilonNbStep());
    }

    @Deprecated
    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, DQNFactory factory,
                                      QLearning.QLConfiguration conf, IDataManager dataManager) {
        this(mdp, factory.buildDQN(mdp.getObservationSpace().getShape(), mdp.getActionSpace().getSize()), conf,
                dataManager);
    }

    @Deprecated
    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, DQNFactory factory,
                                      QLearning.QLConfiguration conf) {
        this(mdp, factory.buildDQN(mdp.getObservationSpace().getShape(), mdp.getActionSpace().getSize()), conf);
    }

    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, DQNFactory factory,
                                      QLearningConfiguration conf) {
        this(mdp, factory.buildDQN(mdp.getObservationSpace().getShape(), mdp.getActionSpace().getSize()), conf);
    }

    @Deprecated
    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, DQNFactoryStdDense.Configuration netConf,
                                      QLearning.QLConfiguration conf, IDataManager dataManager) {

        this(mdp, new DQNFactoryStdDense(netConf.toNetworkConfiguration()), conf, dataManager);
    }

    @Deprecated
    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, DQNFactoryStdDense.Configuration netConf,
                                      QLearning.QLConfiguration conf) {
        this(mdp, new DQNFactoryStdDense(netConf.toNetworkConfiguration()), conf);
    }

    public QLearningDiscreteRecurrent(MDP<OBSERVATION, Integer, DiscreteSpace> mdp, DQNRecurrentNetworkConfiguration netConf,
                                      QLearningConfiguration conf) {
        this(mdp, new DQNFactoryStdRnn(netConf), conf);
    }

}
