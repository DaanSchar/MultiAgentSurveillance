package nl.maastrichtuniversity.dke.ai;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.nd4j.linalg.learning.config.RmsProp;

public class Network {

    public static final int NUM_INPUTS = 28800;
    public static final int NUM_OUTPUTS = 3;

    private static final int NUM_LAYERS = 2;
    private static final int NUM_HIDDEN_NODES = 128;

    public static final double LOW_VALUE_INPUT = 0;
    public static final double HIGH_VALUE_INPUT = 12;

    private static final int MIN_EPOCHS = 100;
    private static final int STEPS_PER_EPOCH = 1000;

    private static final double LEARNING_RATE = 0.01;

    public QLearningConfiguration buildConfig() {
        return QLearningConfiguration.builder()
                .seed(123L)
                .maxEpochStep(STEPS_PER_EPOCH)
                .maxStep(STEPS_PER_EPOCH * MIN_EPOCHS)
                .expRepMaxSize(1500000)
                .batchSize(128)
                .targetDqnUpdateFreq(500)
                .updateStart(10)
                .rewardFactor(0.01)
                .gamma(0.99)
                .errorClamp(1.0)
                .minEpsilon(0.1f)
                .epsilonNbStep(1000)
                .doubleDQN(true)
                .build();
    }

    public DQNFactoryStdDense buildDQNFactory() {
        DQNDenseNetworkConfiguration build = DQNDenseNetworkConfiguration.builder()
                .l2(0.001)
                .updater(new RmsProp(0.000025))
                .numHiddenNodes(NUM_HIDDEN_NODES)
                .numLayers(NUM_LAYERS)
                .learningRate(LEARNING_RATE)
                .build();
        return new DQNFactoryStdDense(build);
    }

}
