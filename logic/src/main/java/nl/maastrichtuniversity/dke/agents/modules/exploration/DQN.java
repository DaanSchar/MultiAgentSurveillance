package nl.maastrichtuniversity.dke.agents.modules.exploration;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.Game;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.nd4j.linalg.learning.config.Nadam;


import java.io.File;
import java.io.IOException;

@Slf4j
public final class DQN {

    private static final String pathWhenTraining = "logic/src/main/resources/bins/";
    private static final String pathWhenPlaying = "policies/bins/";
    private static boolean training = false;

    public static void main(String[] args) {
        DQN dqn = new DQN();
        dqn.train(5);
    }

    private final QLearningConfiguration qlConf;
    private final DQNDenseNetworkConfiguration networkConf;

    public DQN() {
        this.qlConf = getQLConfiguration(1000, 100);
        this.networkConf = getNetwork();
    }

    public void train(int repetitions) {
        training = true;

        for (int i = 0; i < repetitions; i++) {
            log.info("Starting training iteration {}. training for {} games", i, (qlConf.getMaxStep() / qlConf.getMaxEpochStep()));
            final QLearningDiscreteDense<NeuralGameState> dqn;

            if (i == 0) {
                dqn = getNewDqn();
            } else {
                dqn = loadDqn("intruder-fleeing-starter2.bin");
            }

            dqn.train();
            save(dqn, getPathToBins() + "/intruder-fleeing-starter.bin2");
        }
    }

    private MDP<NeuralGameState, Integer, DiscreteSpace> getMDP() {
        Game.setMapFile(getMap("hardMap1"));
        return new IntruderMdp(Game.getInstance());
    }

    private QLearningDiscreteDense<NeuralGameState> getNewDqn() {
        MDP<NeuralGameState, Integer, DiscreteSpace> mdp = getMDP();
        return new QLearningDiscreteDense<>(mdp, networkConf, qlConf);
    }

    private QLearningDiscreteDense<NeuralGameState> loadDqn(String binName) {
        MDP<NeuralGameState, Integer, DiscreteSpace> mdp = getMDP();
        DQNPolicy<NeuralGameState> policy = null;

        try {
            policy = DQNPolicy.load(getPathToBins() + "/" + binName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new QLearningDiscreteDense<>(mdp, policy.getNeuralNet(), qlConf);
    }

    private void save(QLearningDiscreteDense<NeuralGameState> dqn, String binName) {
        try {
            dqn.getPolicy().save(getPathToBins() + "/" + binName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private QLearningConfiguration getQLConfiguration(int stepsPerEpoch, int totalGames) {
        return QLearningConfiguration.builder()
                .seed(1L)
                .maxEpochStep(stepsPerEpoch)
                .maxStep(stepsPerEpoch * totalGames)
                .updateStart(0)
                .rewardFactor(1.0)
                .gamma(0.5)
                .errorClamp(1.0)
                .batchSize(16)
                .minEpsilon(0.5)
                .epsilonNbStep(128)
                .expRepMaxSize(128 * 16)
                .build();
    }

    private DQNDenseNetworkConfiguration getNetwork() {
        return DQNDenseNetworkConfiguration.builder()
                .updater(new Nadam(Math.pow(10, -3.5)))
                .numHiddenNodes(200)
                .numLayers(2)
                .build();
    }

    private File getMap(String mapName) {
        return new File("core/assets/maps/" + mapName + ".txt");
    }

    public static boolean isTraining() {
        return training;
    }

    public static String getPathToBins() {
        if (training) {
            return pathWhenTraining;
        } else {
            return pathWhenPlaying;
        }
    }

}
