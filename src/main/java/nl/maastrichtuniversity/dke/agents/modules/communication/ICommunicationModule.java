package nl.maastrichtuniversity.dke.agents.modules.communication;

import nl.maastrichtuniversity.dke.discrete.CommunicationMark;

import java.util.List;

public interface ICommunicationModule {


    void addMark(int x, int y, CommunicationMark device);

    boolean hasMark(int x, int y);

    List<CommunicationMark> getMarks(int x, int y);

}
