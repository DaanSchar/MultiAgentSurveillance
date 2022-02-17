package nl.maastrichtuniversity.dke.agents.modules.vision;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.discrete.Tile;

import java.util.List;

public interface IVisionModule {
    List<Tile> getObstacles(Direction direction);




}
