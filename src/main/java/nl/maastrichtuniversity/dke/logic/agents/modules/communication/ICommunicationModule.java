package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import nl.maastrichtuniversity.dke.logic.scenario.CommunicationMark;

public interface ICommunicationModule {


    void addMark(int x, int y, CommunicationMark device);

    boolean hasMark(int x, int y);

    //List<CommunicationMark> getMarks(int x, int y);

}
