package nl.maastrichtuniversity.dke.ai;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;

import java.io.IOException;

@Slf4j
public class Train {

    public static void main(String[] args) {
        log.info("Starting training");
        long startTime = System.currentTimeMillis();

        String networkName = "network-" + startTime;
        String networkPath = "src/main/resources/networks/";

        var env = new Environment();
        var dql = getDQL(env);

        dql.train();
        env.close();

        log.info("Training finished network {}", networkName);
        log.info("Time taken: {}", System.currentTimeMillis() - startTime);

        try {
            dql.getNeuralNet().save(networkPath + networkName + ".zip");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
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
