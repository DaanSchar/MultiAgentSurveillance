package nl.maastrichtuniversity.dke.ai;

import org.deeplearning4j.rl4j.space.Encodable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * This class takes care of transforming the state of the game ie the board and the player to something
 * the neural network can understand.
 */
public class NeuralGameState implements Encodable {

    private double[] inputs;

    public NeuralGameState(double[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public double[] toArray() {
        return inputs;
    }

    @Override
    public boolean isSkipped() {
        return false;
    }

    @Override
    public INDArray getData() {
        return Nd4j.create(inputs);
    }

    @Override
    public Encodable dup() {
        return null;
    }

    public INDArray getMatrix() {
        return Nd4j.create(new double[][] {
                inputs
        });
    }
}