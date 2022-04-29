package nl.maastrichtuniversity.dke.logic.agents.modules.movement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.modules.memory.IMemoryModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.*;

public class PathFinderModule {

    private final IMemoryModule memoryModule;

    private final PriorityQueue<Node> queue;
    private final List<Node> visited;
    private final List<Position> path;

    public PathFinderModule(IMemoryModule memoryModule) {
        this.memoryModule = memoryModule;
        this.queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
        this.visited = new ArrayList<>();
        this.path = new ArrayList<>();
    }

    public List<Position> findShortestPath(Position start, Position target) {
        clear();

        fillQueueWithTiles(start);
        processQueue();

        Node t = null;
        for (Node x : visited) {
            if (x.current.equals(target)) {
                t = x;
                break;
            }
        }

        for (Node vertex = t; vertex != null; vertex = vertex.getPrevious()) {
            path.add(vertex.current);
        }

        Collections.reverse(path);

        return path;
    }

    private void fillQueueWithTiles(Position start) {
        List<Tile> possibleTiles = memoryModule.getCoveredTiles();

        for (Tile tile : possibleTiles) {
            if (tile.isPassable()) {
                addTileToQueue(tile, start);
            }
        }
    }

    private void addTileToQueue(Tile tile, Position start) {
        if (tile.getPosition().equals(start)) {
            Node startingNode = new Node(0, start, null);
            queue.add(startingNode);
        } else {
            Node node = new Node(Integer.MAX_VALUE, tile.getPosition(), null);
            queue.add(node);
        }
    }

    private void processQueue() {
        while (!queue.isEmpty()) {
            Node u = queue.poll();
            visited.add(u);

            for (Node v : queue) {
                if (!visited.contains(v)) {
                    int tempDis = u.getCost();
                    if (tempDis < v.getCost()) {
                        v.setCost(tempDis);
                        v.setPrevious(u);
                    }
                }
            }
        }
    }

    private void clear() {
        queue.clear();
        visited.clear();
        path.clear();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Node {
        private int cost;
        private Position current;
        private Node previous;
    }

}
