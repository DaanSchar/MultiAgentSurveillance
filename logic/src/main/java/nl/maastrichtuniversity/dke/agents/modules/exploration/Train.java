package nl.maastrichtuniversity.dke.agents.modules.exploration;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.Game;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.Encodable;


import java.io.File;
import java.io.IOException;

@Slf4j
public final class Train<OBSERVATION extends Encodable> {

    public static int OBSERVATION_SIZE = 64;

    private static final String pathWhenTraining = "core/assets/policies/bins/";
    private static final String pathWhenPlaying = "policies/bins/";
    private static boolean training = false;

    private final QLearningConfiguration qlConf;
    private final MDP<OBSERVATION, Integer, DiscreteSpace> mdp;

    public Train(MDP<OBSERVATION, Integer, DiscreteSpace> mdp,int stepsPerEpoch, int totalGames) {
        this.qlConf = getQLConfiguration(stepsPerEpoch, totalGames);
        this.mdp = mdp;
    }

    public void train(int repetitions) {
        training = true;

        for (int i = 0; i < repetitions; i++) {
            log.info("Starting training iteration {}. training for {} games", i, (qlConf.getMaxStep() / qlConf.getMaxEpochStep()));
            final QLearningDiscreteCustom<OBSERVATION> dqn;

            if (i == 0) {
                log.info("Creating new DQN");
                dqn = getNewDqn();
            } else {
                log.info("Loading previous DQN");
                dqn = loadDqn("intruder-flee.bin");
            }

            dqn.train();
            save(dqn, "intruder-flee_custom.bin");
        }
    }

    private QLearningConfiguration getQLConfiguration(int stepsPerEpoch, int totalGames) {
        return QLearningConfiguration.builder()
                .seed(1L)
                .maxEpochStep(stepsPerEpoch)
                .maxStep(stepsPerEpoch * totalGames)
                .updateStart(0)
                .rewardFactor(1.0)
                .gamma(0.99)
                .errorClamp(1.0)
                .batchSize(16)
                .minEpsilon(0.2)
                .epsilonNbStep(128)
                .expRepMaxSize(128 * 16)
                .doubleDQN(true)
                .build();
    }

    private QLearningDiscreteCustom<OBSERVATION> getNewDqn() {
        return new QLearningDiscreteCustom<>(this.mdp, qlConf);
    }

    private QLearningDiscreteCustom<OBSERVATION> loadDqn(String binName) {
        DQNPolicy<OBSERVATION> policy = null;

        try {
            policy = DQNPolicy.load(getPathToBins() + "/" + binName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new QLearningDiscreteCustom<>(this.mdp, policy.getNeuralNet(), qlConf);
    }

    private void save(QLearningDiscreteCustom<OBSERVATION> dqn, String binName) {
        try {
            dqn.getPolicy().save(getPathToBins() + "/" + binName);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
