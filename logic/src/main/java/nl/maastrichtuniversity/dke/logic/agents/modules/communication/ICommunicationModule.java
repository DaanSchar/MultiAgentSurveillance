package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface ICommunicationModule {

    boolean hasMark(CommunicationType type);

    int countMark(CommunicationType type);

    void findMarker(CommunicationType marker);

    void dropMark(CommunicationMark device);

    boolean tileHasMark(Position position, CommunicationType type);

    Position getMark(CommunicationType type);
}
