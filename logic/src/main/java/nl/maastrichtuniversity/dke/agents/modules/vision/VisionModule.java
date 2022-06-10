package nl.maastrichtuniversity.dke.agents.modules.vision;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class VisionModule extends AgentModule implements IVisionModule {

    @Getter
    private final int viewingDistance;
    @Getter
    private final List<Agent> visibleAgents = new LinkedList<>();
    @Getter
    private final List<Tile> visibleTiles = new LinkedList<>();

    private final List<Tile> targetTiles;

    private Position currentPosition;
    private Direction currentDirection;



    public VisionModule(Scenario scenario, int viewingDistance) {
        super(scenario);
        this.viewingDistance = viewingDistance;
        targetTiles = scenario.getEnvironment().get(TileType.TARGET);
    }

    @Override
    public void useVision(Position position, Direction direction) {
        this.currentPosition = position;
        this.currentDirection = direction;

        clear();
        processVision();
    }

    public List<Double> getVector() {
        return visibleTiles.stream().map(Tile::getType)
                .map(tileType -> (double) tileType.getValue())
                .collect(Collectors.toList());
    }

    /**
     * @return number of target tiles seen
     */
    @Override
    public int targetTilesSeen() {
        int numberOfTiles = 0;
        for (Tile targetTile : targetTiles) {
            if (visibleTiles.contains(targetTile)) {
                numberOfTiles++;
            }
        }
        return numberOfTiles;
    }





    /**
     * This will return a one-hot encoding of each tile that agent sees,e.g.
     * 1 0 0 0 0 0 0 0 0 0 0 0 - Corresponds to the tile being unknown
     * 0 1 0 0 0 0 0 0 0 0 0 0 - Corresponds to the tile being empty
     *
     * @return One-hot encoding of visible tiles
     */
    @Override
    public List<Double> toArray(Position p) {
        int oneHotEncodingSize = 13;
        List<Double> encodedTiles = new ArrayList<>(visibleTiles.size() * 13);
        for (Tile tile : visibleTiles) {
            encodedTiles.addAll(getEncodingPerTile(tile.getType().getValue(), oneHotEncodingSize));
        }
       return encodedTiles.subList(0, Math.min(computeVisionInputSize(), encodedTiles.size()));
    }

    private int computeVisionInputSize() {
        double visionInputSize = scenario.getIntruders().getCurrentAgent().getPolicyModule().getInputSize() * 0.977;
        return (int) Math.round(visionInputSize / 13) * 13;
    }

    public List<Double> getEncodingPerTile(int idx, int oneHotEncodingSize) {
        List<Double> list = new ArrayList<>(Collections.nCopies(oneHotEncodingSize, 0d));
        list.set(idx, 1d);
        return list;
    }

    private void clear() {
        visibleAgents.clear();
        visibleTiles.clear();
    }

    private void processVision() {
        List<Position> visiblePositions = getAllVisiblePositions();
        processTilesAtPositions(visiblePositions);
    }

    private List<Position> getAllVisiblePositions() {
        Position left = currentPosition.getPosInDirection(currentDirection.leftOf());
        Position right = currentPosition.getPosInDirection(currentDirection.rightOf());

        List<Position> leftRow = getVisiblePositionsRow(left, currentDirection);
        List<Position> rightRow = getVisiblePositionsRow(right, currentDirection);
        List<Position> centerRow = getVisiblePositionsRow(currentPosition, currentDirection);

        return concatLists(leftRow, centerRow, rightRow);
    }

    private void processTilesAtPositions(List<Position> visiblePositions) {
        for (Position pos : visiblePositions) {
            visibleTiles.add(getTileAt(pos));
            addPresentAgents(pos);
        }
    }

    private List<Position> getVisiblePositionsRow(Position position, Direction direction) {
        List<Position> positionsInRange = getPositionsRowInRange(position, direction);
        List<Position> visiblePositions = new ArrayList<>();

        for (Position possiblePos : positionsInRange) {
            visiblePositions.add(possiblePos);

            if (!isSeeThroughTile(possiblePos)) {
                break;
            }

            if (isPartiallySeeThroughTile(possiblePos)) {
                Position last = possiblePos.getPosInDirection(direction);
                visiblePositions.add(last);
                break;
            }
        }

        return visiblePositions;
    }

    private List<Position> getPositionsRowInRange(Position position, Direction direction) {
        List<Position> positionsRow = new ArrayList<>();
        int range = getViewDistance();
        Position targetPosition = position;

        for (int i = 0; i < range; i++) {
            positionsRow.add(targetPosition);
            targetPosition = targetPosition.getPosInDirection(direction);
        }

        removeOutOfMapPositions(positionsRow);

        return positionsRow;
    }

    private void removeOutOfMapPositions(List<Position> positions) {
        positions.removeIf(this::isOutOfMap);
    }

    private void addPresentAgents(Position position) {
        for (Agent agent : getAgents()) {
            if (agent.getPosition().equals(position)) {
                visibleAgents.add(agent);
            }
        }
    }

    private List<Agent> getAgents() {
        return Stream.concat(
                scenario.getGuards().stream(),
                scenario.getIntruders().stream()
        ).collect(Collectors.toList());
    }

    private int getViewDistance() {
        Tile tile = getTileAt(currentPosition);

        if (tile.getType() == TileType.SENTRY) {
            return 2 * viewingDistance;
        }

        return viewingDistance;
    }

    private boolean isSeeThroughTile(Position position) {
        Tile tile = getTileAt(position);

        if (tile.getType() == TileType.DOOR) {
            return tile.isOpened();
        }

        return tile.getType() != TileType.WALL;
    }

    private boolean isPartiallySeeThroughTile(Position position) {
        Tile tile = getTileAt(position);

        return tile.getType() == TileType.SHADED;
    }

    private boolean isOutOfMap(Position position) {
        int mapWidth = scenario.getEnvironment().getWidth();
        int mapHeight = scenario.getEnvironment().getHeight();

        return position.getX() < 0 || position.getY() < 0
                || position.getX() >= mapWidth || position.getY() >= mapHeight;
    }

    private Tile getTileAt(Position position) {
        if (isOutOfMap(position)) {
            return null;
        }

        return scenario.getEnvironment().getAt(position);
    }

    @SafeVarargs
    private List<Position> concatLists(List<Position>... lists) {
        List<Position> concat = new ArrayList<>();

        for (List<Position> list : lists) {
            concat.addAll(list);
        }

        return concat;
    }


}
