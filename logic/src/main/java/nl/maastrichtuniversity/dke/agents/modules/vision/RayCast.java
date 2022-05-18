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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class RayCast extends AgentModule implements IVisionModule {

    private final int viewingDistance;

    @Getter private final List<Agent> visibleAgents = new LinkedList<>();
    @Getter private final List<Tile> visibleTiles = new LinkedList<>();

    private Position currentPosition;
    private Direction currentDirection;

    public RayCast(Scenario scenario, int viewingDistance) {
        super(scenario);
        this.viewingDistance = viewingDistance;
    }

    @Override
    public void useVision(Position position, Direction direction) {
        this.currentPosition = position;
        this.currentDirection = direction;

        clear();
        processVision();
    }

    protected void clear() {
        visibleAgents.clear();
        visibleTiles.clear();
    }

    protected void processVision() {
        List<Position> visiblePositions = getAllVisiblePositions();
        processTilesAtPositions(visiblePositions);
    }

    private List<Position> getFinalPositions() {
        ArrayList<Position> finalPositions = new ArrayList<>();
        Position front = currentPosition.getPosInDirection(currentDirection,viewingDistance);
        finalPositions.add(front);
        for(int i = 0;i<5;i++) {
            Position newPosition = front.getPosInDirection(currentDirection.leftOf(),i);
            finalPositions.add(newPosition);
        }
        for(int i = 0;i<5;i++) {
            Position newPosition = front.getPosInDirection(currentDirection.rightOf(),i);
            finalPositions.add(newPosition);
        }
        return finalPositions;
    }

    private List<Position> getAllVisiblePositions() {
        List<Position> positions = getFinalPositions();
        List<Position> all = new ArrayList<>();
        double[] origin = new double[]{currentPosition.getX(),currentPosition.getY()};

        for (Position p:positions){
            double[] end = new double[]{p.getX(),p.getY()};
            List<Position> visible = GenerateBeam(origin,end);
            all = concatLists(all, visible);
        }
        return all;
    }

    public List<Position> GenerateBeam(double[] origin, double[] end) {
        List<Position> visiblePositions = new ArrayList<>();

        double[] directionVector = new double[]{end[0] - origin[0], end[1] - origin[1]};

        double[] rayUnitStepSize = new double[]{Math.sqrt(1 + Math.pow(directionVector[1]/directionVector[0],2)),
                Math.sqrt(1 + Math.pow(directionVector[0]/directionVector[1],2))};

        double[] vStep = new double[2];
        double[] rayLength = new double[2];
        double [] map = origin;

        // Establish Starting Conditions
        if (directionVector[0] < 0)
        {
            vStep[0] = -1;
            rayLength[0] = (origin[0] - map[0])*rayUnitStepSize[0];
        }
        else
        {
            vStep[0] = 1;
            rayLength[0] = ((map[0] + 1) - origin[0])*rayUnitStepSize[0];
        }

        if (directionVector[1] < 0)
        {
            vStep[1] = -1;
            rayLength[1] = (origin[0] - map[0])*rayUnitStepSize[0];
        }
        else
        {
            vStep[1] = 1;
            rayLength[1] = ((map[0] + 1) - origin[0])*rayUnitStepSize[0];
        }

        // Perform "Walk" until collision or range check
        boolean bTileFound = false;
        float fMaxDistance = 10;
        float fDistance = 0.0f;
        while (!bTileFound && fDistance < fMaxDistance)
        {
            // Walk along shortest path
            if (rayLength[0] < rayLength[1]) {
                map[0] += vStep[0];
                fDistance = (float) rayLength[0];
                rayLength[0] += rayUnitStepSize[0];
            }
            else
            {
                map[1] += vStep[1];
                fDistance = (float) rayLength[1];
                rayLength[1] += rayUnitStepSize[1];
            }

            // Test tile at new test point
            Position current = new Position((int)map[0],(int)map[1]);
            if (map[0] >= 0 && map[1] >= 0 && !isOutOfMap(current)) {
                if (!isSeeThroughTile(new Position((int)map[0],(int) map[1]))) {
                    bTileFound = true;
                }
                else{
                    visiblePositions.add(current);
                }

            }

        }
        return visiblePositions;
    }

    protected void processTilesAtPositions(List<Position> visiblePositions) {
        for (Position pos : visiblePositions) {
            visibleTiles.add(getTileAt(pos));
            addPresentAgents(pos);
        }
    }


    protected void addPresentAgents(Position position) {
        for (Agent agent : getAgents()) {
            if (agent.getPosition().equals(position)) {
                visibleAgents.add(agent);
            }
        }
    }

    protected List<Agent> getAgents() {
        return Stream.concat(
                scenario.getGuards().stream(),
                scenario.getIntruders().stream()
        ).collect(Collectors.toList());
    }

    protected int getViewingDistance() {
        Tile tile = getTileAt(currentPosition);

        if (tile.getType() == TileType.SENTRY) {
            return 2 * viewingDistance;
        }

        return viewingDistance;
    }

    protected boolean isSeeThroughTile(Position position) {
        Tile tile = getTileAt(position);

        if (tile.getType() == TileType.DOOR) {
            return tile.isOpened();
        }

        return tile.getType() != TileType.WALL;
    }

    protected boolean isPartiallySeeThroughTile(Position position) {
        Tile tile = getTileAt(position);

        return tile.getType() == TileType.SHADED;
    }

    protected boolean isOutOfMap(Position position) {
        int mapWidth = scenario.getEnvironment().getWidth();
        int mapHeight = scenario.getEnvironment().getHeight();

        return position.getX() < 0 || position.getY() < 0
                || position.getX() >= mapWidth || position.getY() >= mapHeight;
    }

    protected Tile getTileAt(Position position) {
        if (isOutOfMap(position)) {
            return null;
        }

        return scenario.getEnvironment().getAt(position);
    }

    protected List<Position> concatLists(List<Position>... lists) {
        List<Position> concat = new ArrayList<>();

        for (List<Position> list : lists) {
            concat.addAll(list);
        }

        return concat;
    }

}





