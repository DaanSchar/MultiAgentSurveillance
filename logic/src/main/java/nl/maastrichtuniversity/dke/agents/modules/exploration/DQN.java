package nl.maastrichtuniversity.dke.agents.modules.exploration;

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

public class DQN {

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
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



        for(int i = 0;i < 100;i++) {

            FileResourcesUtils app = new FileResourcesUtils();
            File file = new File("D:\\MultiAgentSurveillance\\core\\assets\\testmap.txt");
            Game game;
            Game.setMapFile(file);
            game = Game.getInstance();
            game.init();

            MDP<NeuralGameState, Integer, DiscreteSpace> mdp = new IntruderMdp(game);
            DQNPolicy<NeuralGameState> policy = DQNPolicy.load("logic\\src\\main\\resources\\RL_bins\\intruder-player-dqn100(4).bin");


            final QLearningDiscreteDense<NeuralGameState> dqn = new QLearningDiscreteDense<>(mdp, policy.getNeuralNet(), DQN);
            //start the training
            dqn.train();

            //useless on toy but good practice!
            mdp.close();

            dqn.getPolicy().save("logic\\src\\main\\resources\\RL_bins\\intruder-player-dqn100(4).bin");


        }
        //for (int i = 0; i < game.getScenario().getIntruders().size(); i++) {
        //    IntruderGame intruderGame = new IntruderGame(i);
        //    // create a IntruderGame Object with only ONE agent(possibly), and feed it to intruder mdp
        //    MDP<IntruderGame, Integer, DiscreteSpace> mdp = new IntruderMdp(intruderGame);
        //    final QLearningDiscreteDense<IntruderGame> dqn = new QLearningDiscreteDense<>(mdp, conf, DQN);

        //    //start the training
        //    dqn.train();

        //    //useless on toy but good practice!
        //    mdp.close();

        //    dqn.getPolicy().save("intruder-player-dqn" + i + ".zip");
        //}
    }

}

