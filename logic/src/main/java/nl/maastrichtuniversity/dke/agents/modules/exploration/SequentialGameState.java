package nl.maastrichtuniversity.dke.agents.modules.exploration;

import org.deeplearning4j.rl4j.space.Encodable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

public class SequentialGameState implements Encodable {

    private final double[][] inputs;

    public SequentialGameState(double[][] inputs) {
        this.inputs = inputs;
    }

    @Override
    public double[] toArray() {
        return Arrays.stream(inputs).flatMapToDouble(Arrays::stream).toArray();
    }

    @Override
    public boolean isSkipped() {
        return false;
    }

    @Override
    public INDArray getData() {
        return Nd4j.createFromArray(inputs);
    }

    @Override
    public Encodable dup() {
        return null;
    }

}
