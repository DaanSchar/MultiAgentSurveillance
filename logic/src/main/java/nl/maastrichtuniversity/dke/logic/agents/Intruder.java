package nl.maastrichtuniversity.dke.logic.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Intruder extends Agent {

    private @Getter
    @Setter
    boolean isCaught;

    public Intruder() {
        super();
        this.isCaught = false;
    }

    @Override
    public void update() {
        if (seesGuard()) {
            avoidGuards();
        } else if (seesTarget()) {
            navigateToTarget();
        } else {
            super.explore();
        }


        super.update();
    }

    private void navigateToTarget() {
        Tile target = getTarget();
        Position targetPosition = target.getPosition();
        moveToLocation(targetPosition);
    }

    private boolean seesTarget() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();

        return containsTarget(obstacles);
    }

    private void avoidGuards() {
        /* run away from the seen guard */
        List<Guard> visibleGuards = getVisibleGuards();
        Position toGuard = getPathFinderModule().getShortestPath(getPosition(),
                visibleGuards.get(0).getPosition()).get(0);

        if (toGuard.getX() != getPosition().getX()) {
            if (toGuard.getX() < getPosition().getX()) {
                Position avoid = new Position(getPosition().getX() + 1, getPosition().getY());
                moveToLocation(avoid);
            } else {
                Position avoid = new Position(getPosition().getX() - 1, getPosition().getY());
                moveToLocation(avoid);
            }
        } else {
            if (toGuard.getY() < getPosition().getY()) {
                Position avoid = new Position(getPosition().getX(), getPosition().getY() + 1);
                moveToLocation(avoid);
            } else {
                Position avoid = new Position(getPosition().getX(), getPosition().getY() - 1);
                moveToLocation(avoid);
            }
        }
    }

    private boolean seesGuard() {
        return getVisibleGuards().size() > 0;
    }

    private List<Guard> getVisibleGuards() {
        List<Agent> visibleAgents = super.getVisibleAgents();
        List<Agent> visibleGuards = filterGuards(visibleAgents);

        return castAgentsToGuards(visibleGuards);
    }

    private List<Agent> filterGuards(List<Agent> agents) {
        return agents.stream()
                .filter(agent -> agent instanceof Guard)
                .collect(Collectors.toList());
    }

    private List<Guard> castAgentsToGuards(List<Agent> agents) {
        return agents.stream()
                .map(agent -> (Guard) agent)
                .collect(Collectors.toList());
    }

    private boolean containsTarget(List<Tile> obstacles) {
        for (Tile tile : obstacles) {
            if (isTarget(tile)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTarget(Tile tile) {
        return tile.getType().equals(TileType.TARGET);
    }

    private Tile getTarget() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();
        return obstacles.stream().filter(tile -> tile.getType().equals(TileType.TARGET)).findFirst().get();
    }
}
