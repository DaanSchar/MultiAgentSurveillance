package nl.maastrichtuniversity.dke.agents.modules.vision;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class RayCast2 extends AgentModule implements IVisionModule {

    private final double viewingDistance;

    private final @Getter List<Tile> visibleTiles;
    private final @Getter List<Agent> visibleAgents;

    public RayCast2(Scenario scenario, double viewingDistance) {
        super(scenario);
        this.viewingDistance = (float) viewingDistance;
        this.visibleTiles = new ArrayList<>();
        this.visibleAgents = new ArrayList<>();
    }

    @Override
    public void useVision(Position position, Direction direction) {
        visibleTiles.clear();
        visibleAgents.clear();

        List<Position> visibleTilePositions = getVisiblePositions(position, direction);

        for (Position tilePos : visibleTilePositions) {
            visibleTiles.add(scenario.getEnvironment().getAt(tilePos));
            visibleAgents.add(getAgentAt(tilePos));
        }
    }

    private List<Position> getVisiblePositions(Position position, Direction direction) {
        double viewingAngle = Math.toRadians(120);
        double currentAngle = (direction.getAngle());
        double halfAngle = viewingAngle / 2.0;
        double minAngle = currentAngle - halfAngle;
        double maxAngle = currentAngle + halfAngle;

        HashSet<Position> visibleTilePositions = new HashSet<>();

        for (double i = minAngle; i < maxAngle; i += 0.1) {
            Ray ray = new Ray(viewingDistance, scenario);
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
}
