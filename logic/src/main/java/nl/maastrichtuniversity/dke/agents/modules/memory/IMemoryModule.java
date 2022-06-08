package nl.maastrichtuniversity.dke.agents.modules.memory;

import nl.maastrichtuniversity.dke.agents.modules.smell.ISmellModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;

public interface IMemoryModule {

    void update(IVisionModule vision, ISmellModule smellModule, Position position, double[] observations);

    Environment getMap();

    List<Tile> getDiscoveredTiles();

    void setSpawnPosition(Position position);

    List<Tile> getCoveredTiles();

    List<Sound> getCurrentSounds();

    void toggledDoor(Position position);

    void brokeWindow(Position position);

    boolean discoveredNewTiles();
}
