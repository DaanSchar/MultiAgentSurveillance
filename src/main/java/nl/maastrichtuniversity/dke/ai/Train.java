package nl.maastrichtuniversity.dke.ai;

import nl.maastrichtuniversity.dke.gui.GameWindow;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Train {

    private static final Logger logger = LoggerFactory.getLogger(Train.class);

    public static void main(String[] args) {
        logger.info("Starting training");
        long startTime = System.currentTimeMillis();

        String networkName = "network-" + startTime;
        String networkPath = "src/main/resources/networks/";

        var env = new Environment();
        var dql = getDQL(env);
        new GameWindow();

        dql.train();
        env.close();

        logger.info("Training finished network {}", networkName);
        logger.info("Time taken: {}", System.currentTimeMillis() - startTime);

        try {
            dql.getNeuralNet().save(networkPath + networkName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static QLearningDiscreteDense<NeuralGameState> getDQL(Environment env) {
        Network network = new Network();

        return new QLearningDiscreteDense<>(
                env,
                network.buildDQNFactory(),
                network.buildConfig()
        );
    }

}
