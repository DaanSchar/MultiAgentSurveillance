package nl.maastrichtuniversity.dke.logic.agents.modules.listening;

import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public interface IListeningModule {
    boolean getSound(Position position);
    List<Position> getDirection(Position position);
}
