package nl.maastrichtuniversity.dke.agents.modules.memory;

import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.util.Position;

public interface IMemoryModule {

    void update(Tile tile);

    Environment getMap();

    void setStartPosition(Position position);

}
