package nl.maastrichtuniversity.dke.agents.modules.communication;

import lombok.Getter;

public class CommunicationModule implements ICommunicationModule{

    private final @Getter int numberOfMarkers;

    public CommunicationModule(int numberOfMarkers) {
        this.numberOfMarkers = numberOfMarkers;
    }

}
