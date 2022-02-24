package nl.maastrichtuniversity.dke.agents.modules.vision;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.List;

public interface IVisionModule {
    void useVision(Position position,Direction direction);

    List<Tile> getObstacles();
    List<Agent> getAgents();




}
