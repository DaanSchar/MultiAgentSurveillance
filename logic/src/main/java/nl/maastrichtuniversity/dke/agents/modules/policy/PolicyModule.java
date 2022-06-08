package nl.maastrichtuniversity.dke.agents.modules.policy;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.exploration.Train;
import nl.maastrichtuniversity.dke.agents.modules.exploration.NeuralGameState;
import nl.maastrichtuniversity.dke.agents.util.MoveAction;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;

@Slf4j
public class PolicyModule implements IPolicyModule {

    private final @Getter DQNPolicy<NeuralGameState> policy;

    public PolicyModule(String path) {
        this.policy = getPolicy(Train.getPathToBins() + path);
    }

    public MoveAction nextMove(double[] input) {
        Integer action = policy.nextAction(Nd4j.expandDims(Nd4j.create(input), 0));
        return MoveAction.values()[action];
    }

    public int getInputSize() {
        MultiLayerNetwork network = (MultiLayerNetwork) this.policy.getNeuralNet().getNeuralNetworks()[0];
        Layer inputLayer = network.getLayer(0);
        return (int) inputLayer.getParam("W").shape()[0];
    }

    private DQNPolicy<NeuralGameState> getPolicy(String path) {
        DQNPolicy<NeuralGameState> policy = null;

        try {
            policy = DQNPolicy.load(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return policy;
    }

}
