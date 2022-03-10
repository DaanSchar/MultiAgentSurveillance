package nl.maastrichtuniversity.dke.agents.modules.memory;

import nl.maastrichtuniversity.dke.agents.modules.listening.IListeningModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.List;

public interface IMemoryModule {

    void update(IVisionModule vision, List<Position> listeningModule);

    Environment getMap();

    void setStartPosition(Position position);

}
