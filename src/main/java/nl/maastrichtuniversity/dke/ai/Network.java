package nl.maastrichtuniversity.dke.ai;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.nd4j.linalg.learning.config.RmsProp;

public class Network {

    public static final int NUM_INPUTS = 61;
    public static final double LOW_VALUE = 0;
    public static final double HIGH_VALUE = 2;
    private static final int stepsPerEpoch = 100;

    public QLearningConfiguration buildConfig() {
        return QLearningConfiguration.builder()
                .seed(123L)
                .maxEpochStep(stepsPerEpoch)
                .maxStep(stepsPerEpoch * NetworkSettings.getMinEpochs())
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
                .numHiddenNodes(300)
                .numLayers(NetworkSettings.getNumLayers())
                .learningRate(NetworkSettings.getLearningRate())
                .build();
        return new DQNFactoryStdDense(build);
    }

}
