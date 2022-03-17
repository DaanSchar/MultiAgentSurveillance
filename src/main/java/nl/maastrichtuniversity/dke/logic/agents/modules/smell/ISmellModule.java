package nl.maastrichtuniversity.dke.logic.agents.modules.smell;

import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface ISmellModule {
    boolean getSmell(Position position);
    int getSmellingDistance();
}
