package nl.maastrichtuniversity.dke.agents.modules.vision;

import nl.maastrichtuniversity.dke.areas.Area;
import nl.maastrichtuniversity.dke.util.Vector;

import java.util.List;

public interface IVisionModule {
    List<Area> getObstacles(Vector direction);




}