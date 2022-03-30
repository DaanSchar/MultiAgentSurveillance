package main.java.nl.maastrichtuniversity.dke.logic.agents.modules.communication;

public interface ICommunicationModule {

    boolean hasMark(CommunicationType type);
    int countMark(CommunicationType type);
    void findMarker(CommunicationType marker);
    void dropMark(CommunicationMark device);
}
