package nl.maastrichtuniversity.dke.logic.agents;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.ai.NeuralGameState;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Guard extends Agent {

    private MultiLayerNetwork explorationNetwork;


    public Guard() {
        super();

        // loadNetwork("src/main/resources/networks/network-1647872939503-input-22-output-3-nodes-128-learningrate-0.01-epochs-1000.zip");
    }

    public void explore() {
        markingStep();
        navigationStep();
    }

    private void markingStep() {
        if (!currentCellBlocksPath()) {
            getCurrentTile().setVisited(true);
        } else {
            getCurrentTile().setExplored(true);
        }
    }

    private void navigationStep() {
        if (hasUnexploredSurroundingTiles(getCurrentTile())) {
            moveToBestTile();
        } else if (hasSurroundingExploredTiles(getCurrentTile())) {

        }
    }

    private boolean hasSurroundingExploredTiles(MemoryTile currentTile) {
        return false;
    }

    private void moveToBestTile() {
        var targetTile = getBestUnexploredTile();
        var nextPosition = getMovement().goForward(getPosition(), getDirection(), Game.getInstance().getTime());

        if (nextPosition.equals(targetTile.getPosition())) {

            goForward(Game.getInstance().getTime());
        } else {
            rotate(-1, Game.getInstance().getTime());
        }
    }

    /**
     * @return The MemoryTile adjacent to the agent which is most
     * likely to be marked as visited in the marking step
     */
    private MemoryTile getBestUnexploredTile() {
        List<MemoryTile> unexploredTiles = getUnexploredTiles(getCurrentTile());

        MemoryTile bestTile = unexploredTiles.get(0);
        int bestScore = 0;

        for (MemoryTile tile : unexploredTiles) {
            var surroundingTiles = getMemoryModule().getMap().getNeighbouringTiles(tile);

            int score = 0;

            for (Tile surroundingTile : surroundingTiles) {
                if (((MemoryTile)surroundingTile).isVisited() || !surroundingTile.getType().isPassable()) { score++; }
            }

            if (score > bestScore) {
                bestScore = score;
                bestTile = tile;
            }

        }

        return bestTile;
    }

    private List<MemoryTile> getUnexploredTiles(Tile tile) {
        var neighbouringTiles = getMemoryModule().getMap().getNeighbouringTiles(tile);
        var unexploredTiles = new ArrayList<MemoryTile>();

        for (Tile t : neighbouringTiles) {
            if (!( (MemoryTile) t).isExplored()) {
                unexploredTiles.add((MemoryTile) t);
            }
        }

        return unexploredTiles;
    }

    private boolean hasUnexploredSurroundingTiles(Tile tile) {
        return getUnexploredTiles(tile).size() > 0;
    }


    private boolean currentCellBlocksPath() { return false; }















    // network stuff

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
