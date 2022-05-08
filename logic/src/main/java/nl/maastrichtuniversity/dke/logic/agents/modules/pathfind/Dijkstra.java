package nl.maastrichtuniversity.dke.logic.agents.modules.pathfind;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Dijkstra implements PathFinderModule{

    private final @Getter Environment environment;

    private List<Tile> visited;
    private List<Tile> unvisited;

    private int[][] shortestPathFromStart;
    private final Tile[][] previousTile;

    public Dijkstra(Environment environment) {
        this.environment = environment;
        this.visited = new ArrayList<>();
        this.unvisited = new ArrayList<>();

        int width = environment.getWidth();
        int height = environment.getHeight();
        this.shortestPathFromStart = new int[width][height];
        this.previousTile = new Tile[width][height];
    }

    public List<Position> getShortestPath(Position start, Position goal) {
        clear(start);

        while(!unvisited.isEmpty()) {
            Tile currentTile = getTileWithShortestDistanceToStart();
            updateNeighbourDistances(currentTile);
            visited.add(currentTile);
            unvisited.remove(currentTile);
        }

        return getPath(start, goal);
    }

    public boolean pathExists(Position start, Position goal) {
        return false;
    }

    private void clear(Position start) {
        resetDistances(start);
        resetPreviousTile();
        resetUnvisited();
        visited = new ArrayList<>();
    }

    private void resetDistances(Position start) {
        shortestPathFromStart = new int[environment.getWidth()][environment.getHeight()];
        for (int i = 0; i < environment.getWidth(); i++) {
            for (int j = 0; j < environment.getHeight(); j++) {
                shortestPathFromStart[i][j] = Integer.MAX_VALUE;
            }
        }

        this.shortestPathFromStart[start.getX()][start.getY()] = 0;
    }

    private void resetPreviousTile() {
        for (int i = 0; i < previousTile.length; i++) {
            for (int j = 0; j < previousTile[0].length; j++) {
                previousTile[i][j] = null;
            }
        }
    }

    private void resetUnvisited() {
        this.unvisited = new ArrayList<>();
        this.unvisited.addAll(getWalkableTiles());
    }

    private List<Tile> getWalkableTiles() {
        return environment.filter(this::isWalkable);
    }

    private boolean isWalkable(Tile tile) {
        return tile.getType() != TileType.UNKNOWN && ((MemoryTile)tile).isPassable(true);
    }

    private Tile getTileWithShortestDistanceToStart() {
        int shortestDistance = Integer.MAX_VALUE;
        Tile shortestTile = unvisited.get(0);

        for (Tile tile : unvisited) {
            int distance = getDistanceFromStart(tile);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                shortestTile = tile;
            }
        }

        return shortestTile;
    }

    private List<Tile> getUnvisitedNeighbours(Tile tile) {
        return environment.getNeighborsAndFilter(
                tile,
                neighbor -> unvisited.contains(neighbor) && isWalkable(neighbor)
        );
    }

    private void updateNeighbourDistances(Tile currentTile) {
        List<Tile> neighbours = getUnvisitedNeighbours(currentTile);
        int distanceBetweenNeighbours = 1;

        for (Tile neighbour : neighbours) {
            int savedDistance = getDistanceFromStart(neighbour);
            int newDistance = distanceBetweenNeighbours + getDistanceFromStart(currentTile);

            if (savedDistance > newDistance) {
                setDistanceFromStart(neighbour, newDistance);
                setPreviousTile(neighbour, currentTile);
            }
        }
    }

    public int getDistanceFromStart(Position position) {
        return this.shortestPathFromStart[position.getX()][position.getY()];
    }

    private int getDistanceFromStart(Tile tile) {
        Position position = tile.getPosition();
        return this.shortestPathFromStart[position.getX()][position.getY()];
    }

    private void setDistanceFromStart(Tile tile, int distance) {
        if (distance < 0) {
            distance += Integer.MAX_VALUE;
        }
        Position position = tile.getPosition();
        this.shortestPathFromStart[position.getX()][position.getY()] = distance;
    }

    private void setPreviousTile(Tile tile, Tile previousTile) {
        Position position = tile.getPosition();
        this.previousTile[position.getX()][position.getY()] = previousTile;
    }

    private List<Position> getPath(Position start, Position goal) {
        List<Position> path = new ArrayList<>();
        Position currentPosition = goal;

        while (currentPosition != start) {
            path.add(currentPosition);
            Tile tile = previousTile[currentPosition.getX()][currentPosition.getY()];
            if (tile == null) {
                break;
            }
            currentPosition = tile.getPosition();
        }

        Collections.reverse(path);

        return path;
    }

}
