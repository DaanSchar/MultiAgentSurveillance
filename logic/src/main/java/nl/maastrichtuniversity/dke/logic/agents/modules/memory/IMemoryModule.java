package main.java.nl.maastrichtuniversity.dke.logic.agents.modules.memory;

import main.java.nl.maastrichtuniversity.dke.logic.agents.modules.listening.IListeningModule;
import main.java.nl.maastrichtuniversity.dke.logic.agents.modules.smell.ISmellModule;
import main.java.nl.maastrichtuniversity.dke.logic.agents.modules.vision.IVisionModule;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import java.util.List;

public interface IMemoryModule {

    void update(IVisionModule vision, IListeningModule listeningModule, ISmellModule smellModule, Position position);

    Environment getMap();

    List<Tile> getDiscoveredTiles();

    void setSpawnPosition(Position position);

    List<Tile> getCoveredTiles();

}
