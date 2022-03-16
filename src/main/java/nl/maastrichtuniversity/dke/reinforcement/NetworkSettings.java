package nl.maastrichtuniversity.dke.reinforcement;

import lombok.Getter;

public class NetworkSettings {

    private static @Getter int numLayers = 3;
    private static @Getter int numInputs = 4;

    private static @Getter int minEpochs = 100;

    private static @Getter double learningRate = 0.1;

}
