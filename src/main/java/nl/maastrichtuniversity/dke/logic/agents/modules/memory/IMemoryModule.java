package nl.maastrichtuniversity.dke.logic.agents.modules.memory;

import nl.maastrichtuniversity.dke.logic.agents.modules.vision.IVisionModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface IMemoryModule {

    void update(IVisionModule vision);

    Environment getMap();

    void setStartPosition(Position position);

}
