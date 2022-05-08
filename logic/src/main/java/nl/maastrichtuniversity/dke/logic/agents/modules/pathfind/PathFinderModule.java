package nl.maastrichtuniversity.dke.logic.agents.modules.pathfind;

import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.*;

public interface PathFinderModule {

    boolean pathExists(Position start, Position goal);

    List<Position> getShortestPath(Position start, Position goal);

    Environment getEnvironment();

    int getDistanceFromStart(Position position);

}
