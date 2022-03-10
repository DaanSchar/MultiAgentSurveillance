package nl.maastrichtuniversity.dke.agents.modules.listening;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.List;

public interface IListeningModule {
    boolean getSound(Position position);
    List<Position> getDirection(Position position);
}
