package nl.maastrichtuniversity.dke.agents.modules.pathfind;

import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.*;

public interface PathFinderModule {

    boolean pathExists(Position start, Position goal);

    List<Position> getShortestPath(Position start, Position goal);

    Environment getEnvironment();

    int getDistanceFromStart(Position position);

}
