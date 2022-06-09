package nl.maastrichtuniversity.dke.agents.modules.vision;

import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;

public interface IVisionModule {
    void useVision(Position position, Direction direction);

    int getViewingDistance();

    List<Tile> getVisibleTiles();

    List<Agent> getVisibleAgents();

    List<Double> getVector();

    List<Double> toArray();

    int targetTilesSeen();

ch    Tile getCurrentPosition();
}