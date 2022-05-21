package nl.maastrichtuniversity.dke.agents.modules.exploration;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.util.FileResourcesUtils;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.nd4j.linalg.learning.config.Nadam;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class DQN {

    public static final String PATH_TO_BINS = "logic/src/main/resources/bins/";

    public static void main(String[] args) throws IOException {
        int stepsPerEpoch = 1000;
        int maxGames = 10;// change to 10, for each map

        final QLearningConfiguration DQN = QLearningConfiguration.builder()
                .seed(1L)
                .maxEpochStep(stepsPerEpoch)
                .maxStep(stepsPerEpoch * maxGames)
                .updateStart(0)
                .rewardFactor(1.0)
                .gamma(0.5)
                .errorClamp(1.0)
                .batchSize(16)
                .minEpsilon(0.5)
                .epsilonNbStep(128)
                .expRepMaxSize(128 * 16)
                .build();


        final DQNDenseNetworkConfiguration conf = DQNDenseNetworkConfiguration.builder()
                .updater(new Nadam(Math.pow(10, -3.5)))
                .numHiddenNodes(20)
                .numLayers(6)
                .build();


        for (int i = 0; i < 50; i++) {
            File file = new File(Objects.requireNonNull(DQN.getClass().getClassLoader().getResource("maps/hardMap1.txt")).getFile());

            Game game;
            Game.setMapFile(file);
            game = Game.getInstance();
            game.init();

            final QLearningDiscreteDense<NeuralGameState> dqn;
            MDP<NeuralGameState, Integer, DiscreteSpace> mdp = new IntruderMdp(game);

            if (i == 0) {
                dqn = new QLearningDiscreteDense<>(mdp, conf, DQN);
            } else {
                DQNPolicy<NeuralGameState> policy = DQNPolicy.load(PATH_TO_BINS + "/intruder-fleeing-starter.bin");
                dqn = new QLearningDiscreteDense<>(mdp, policy.getNeuralNet(), DQN);
            }
            //start the training
            dqn.train();

            //useless on toy but good practice!
            mdp.close();

            dqn.getPolicy().save(PATH_TO_BINS + "/intruder-fleeing-starter.bin");
        }
    }

}

