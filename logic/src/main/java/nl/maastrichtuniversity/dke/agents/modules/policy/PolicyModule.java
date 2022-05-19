package nl.maastrichtuniversity.dke.agents.modules.policy;


import lombok.Getter;
import nl.maastrichtuniversity.dke.agents.modules.exploration.NeuralGameState;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.util.FileResourcesUtils;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class PolicyModule implements IPolicyModule {
    DQNPolicy<NeuralGameState> policy;
    @Getter
    public int inputSize;


    public PolicyModule(String path, int inputSize) {
        path = "D:\\MultiAgentSurveillance\\logic\\src\\main\\resources\\RL_bins\\"+path;
        this.inputSize = inputSize;
        try {
            policy = DQNPolicy.load(path);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public MoveAction nextMove(double[] input) {
        Integer action = policy.nextAction(Nd4j.expandDims(Nd4j.create(input), 0));
        return MoveAction.values()[action];
    }


}