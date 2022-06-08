package nl.maastrichtuniversity.dke.agents.modules.exploration;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.deeplearning4j.rl4j.network.configuration.NetworkConfiguration;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DQNRecurrentNetworkConfiguration extends NetworkConfiguration {

    /**
     * The number of layers
     */
    @Builder.Default
    private int numLayers = 3;

    /**
     * The number of hidden neurons in each layer
     */
    @Builder.Default
    private int numHiddenNodes = 100;

}
