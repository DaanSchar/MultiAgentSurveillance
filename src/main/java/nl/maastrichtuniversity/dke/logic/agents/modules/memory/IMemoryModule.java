package nl.maastrichtuniversity.dke.logic.agents.modules.memory;

import nl.maastrichtuniversity.dke.logic.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import java.util.List;

public interface IMemoryModule {

    void update(IVisionModule vision);

    Environment getMap();

    List<Tile> getDiscoveredTiles();

    void setStartPosition(Position position);

}
