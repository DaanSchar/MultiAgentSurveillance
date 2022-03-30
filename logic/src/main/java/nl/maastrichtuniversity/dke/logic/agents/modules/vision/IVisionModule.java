package main.java.nl.maastrichtuniversity.dke.logic.agents.modules.vision;

import main.java.nl.maastrichtuniversity.dke.logic.agents.Agent;
import main.java.nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public interface IVisionModule {
    void useVision(Position position, Direction direction);

    List<Tile> getObstacles();
    List<Agent> getAgents();

}
