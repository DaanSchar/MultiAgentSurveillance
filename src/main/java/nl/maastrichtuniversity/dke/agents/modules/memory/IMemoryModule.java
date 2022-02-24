package nl.maastrichtuniversity.dke.agents.modules.memory;

import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.discrete.Tile;

public interface IMemoryModule {

    void update(Tile tile);

    Environment getMap();

}
