package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.ai.NeuralGameState;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.agents.modules.exploration.ExplorationModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.exploration.IExplorationModule;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;



@Slf4j
public class Guard extends Agent {

    private IExplorationModule explorationModule;

    public Guard() {
        super();
    }

    public void explore() {
        if (explorationModule == null) { explorationModule = new ExplorationModule(getMemoryModule().getMap(), getMovement()); }

        if (!explorationModule.isDoneExploring()) {
            MoveAction nextMove = explorationModule.explore(getPosition(), getDirection());

            performAction(nextMove);
        }
    }

    private void performAction(MoveAction action) {
        switch(action) {
            case MOVE_FORWARD -> super.goForward(Game.getInstance().getTime());
            case ROTATE_LEFT -> super.rotate(MoveAction.ROTATE_LEFT.getValue(), Game.getInstance().getTime());
            case ROTATE_RIGHT -> super.rotate(MoveAction.ROTATE_RIGHT.getValue(), Game.getInstance().getTime());
            default -> log.error("Unknown action: {}", action);
        }
    }











    // neural-network/RL stuff

    private MultiLayerNetwork explorationNetwork;

    private void performActionFromNetwork() {
        int action = getActionFromNetwork();


        if (action == 0) {
            goForward(Game.getInstance().getTime());
        }  else {
            rotate(action, Game.getInstance().getTime());
        }
    }

    private void loadNetwork(String network) {
        try {
            this.explorationNetwork = MultiLayerNetwork.load(new File(network), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return int, the action that the network outputs.
     */
    private int getActionFromNetwork() {
        var input = getStateVector();
        var output = getNetworkOutput(input);
        var action = getIndexOfLargestValue(output);

        if (Math.random() < 0.01) {
            action = (int) Math.round(Math.random() * 3);
        }

        return action - 1;
    }


    /**
     * Get the output of the network for the given input
     * @param input double[], the input vector.
     * @return double[], the output vector.
     */
    private double[] getNetworkOutput(double[] input) {
        NeuralGameState state = new NeuralGameState(input);
        INDArray output = explorationNetwork.output(state.getMatrix(), false);

        return output.data().asDouble();
    }

    /**
     * Get the index of the largest value in the given array.
     * @param values double[], the array to search in.
     * @return int, the index of the largest value.
     */
    private int getIndexOfLargestValue(double[] values) {
        int index = 0;

        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[index]) {
                index = i;
            }
        }

        return index;
    }

}
