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

import java.util.*;

@Slf4j
public class RayCast extends AgentModule implements IVisionModule {

    private final double viewingDistance;
    private final double viewingAngle = 100;

    private Position currentPosition;

    private final @Getter List<Tile> visibleTiles;
    private final @Getter List<Agent> visibleAgents;

    public RayCast(Scenario scenario, double viewingDistance) {
        super(scenario);
        this.viewingDistance = (float) viewingDistance;
        this.visibleTiles = new ArrayList<>();
        this.visibleAgents = new ArrayList<>();
    }

    @Override
    public void useVision(Position position, Direction direction) {
        visibleTiles.clear();
        visibleAgents.clear();
        this.currentPosition = position;

        List<Position> visibleTilePositions = getVisiblePositions(position, direction);

        for (Position tilePos : visibleTilePositions) {
            visibleTiles.add(scenario.getEnvironment().getAt(tilePos));
            visibleAgents.add(getAgentAt(tilePos));
        }

        sortTileOnDistanceFromAgent(position);
    }

    @Override
    public int getViewingDistance() {
        Tile tile = getTileAt(currentPosition);

        if (tile.getType() == TileType.SENTRY) {
            return 2 * (int) viewingDistance;
        }

        return (int) viewingDistance;
    }

    @Override
    public List<Double> getVector() {
        return null;
    }


    /**
     * This will return a one-hot encoding of each tile that agent sees,e.g.
     * 1 0 0 0 0 0 0 0 0 0 0 0 - Corresponds to the tile being unknown
     * 0 1 0 0 0 0 0 0 0 0 0 0 - Corresponds to the tile being empty
     *
     * @return One-hot encoding of visible tiles
     */
    @Override
    public List<Double> toArray() {
        int oneHotEncodingSize = 13;
        List<Double> encodedTiles = new ArrayList<>(visibleTiles.size() * 13);

        // TODO: Maybe sort the list on distance from the player.
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


    @Override
    public int targetTilesSeen() {
        return 0;
    }

    private List<Position> getVisiblePositions(Position position, Direction direction) {
        double viewingAngle = Math.toRadians(this.viewingAngle);
        double currentAngle = direction.getAngle();
        double halfAngle = viewingAngle / 2.0;
        double minAngle = currentAngle - halfAngle;
        double maxAngle = currentAngle + halfAngle;
        double angleStep = 0.05;

        HashSet<Position> visibleTilePositions = new HashSet<>();

        for (double i = minAngle; i < maxAngle; i += angleStep) {
            Ray ray = new Ray(getViewingDistance(), scenario);
            visibleTilePositions.addAll(ray.cast(position, i));
        }

        return new ArrayList<>(visibleTilePositions);
    }

    private Agent getAgentAt(Position position) {
        List<Agent> agents = new ArrayList<>(scenario.getIntruders());
        agents.addAll(scenario.getGuards());

        for (Agent agent : agents) {
            if (agent.getPosition().equals(position)) {
                return agent;
            }
        }

        return null;
    }

    private Tile getTileAt(Position position) {
        return scenario.getEnvironment().getAt(position);
    }

    private void sortTileOnDistanceFromAgent(Position agentPosition) {
        visibleTiles.sort((o1, o2) -> {
            double distanceO1 = o1.getPosition().distance(agentPosition);
            double distanceO2 = o2.getPosition().distance(agentPosition);

            if (distanceO1 > distanceO2) {
                return 1;
            } else if (distanceO1 < distanceO2) {
                return -1;
            }

            return 0;
        });
    }
}
