package nl.maastrichtuniversity.dke.scenario;
import java.util.List;

import nl.maastrichtuniversity.dke.areas.Area;

public record Environment(
            double width, double height,double scaling,
            List<Area> spawnAreaIntruders, List<Area> spawnAreaGuards, List<Area> walls,
            List<Area> shadedAreas, List<Area> teleportPortals, List<Area> targetArea,
            List<Area> windows, List<Area> doors,List<Area> sentryTowers
    ) {
}