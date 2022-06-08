package nl.maastrichtuniversity.dke.agents.modules.exploration;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.conf.layers.recurrent.SimpleRnn;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.rl4j.network.dqn.DQN;
import org.deeplearning4j.rl4j.network.dqn.DQNFactory;
import org.deeplearning4j.rl4j.util.Constants;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Nadam;
import org.nd4j.linalg.lossfunctions.LossFunctions;



public class DQNNetwork implements DQNFactory {

    public DQN buildDQN(int[] numInputs, int numOutputs) {
        MultiLayerNetwork network = createNeuralNetwork(numInputs, numOutputs);
        network.init();
        return new DQN(network);
    }

    private MultiLayerNetwork createNeuralNetwork(int[] numInputs, int numOutputs) {
        int nIn = 1;

        for (int i : numInputs) {
            nIn *= i;
        }

        NeuralNetConfiguration.ListBuilder confB = new NeuralNetConfiguration.Builder()
                .seed(Constants.NEURAL_NET_SEED)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Nadam(Math.pow(10, -3.5)))
                .weightInit(WeightInit.XAVIER)
                .l2(0.0)
                .list()

                // input layer
                .layer(new SimpleRnn.Builder()
                        .nIn(nIn)
                        .nOut(100)
                        .activation(Activation.RELU).build()
                )

                .layer(new DenseLayer.Builder()
                        .nIn(100)
                        .nOut(70)
                        .activation(Activation.RELU).build()
                )

                // output layer
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(70)
                        .nOut(numOutputs)
                        .build()
                );

        MultiLayerConfiguration mlnConf = confB.build();
        return new MultiLayerNetwork(mlnConf);
    }

}
