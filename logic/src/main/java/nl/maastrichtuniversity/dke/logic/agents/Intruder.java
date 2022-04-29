package nl.maastrichtuniversity.dke.logic.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Intruder extends Agent {

    private @Getter @Setter boolean alive;

    public Intruder() {
        super();
        this.alive = true;
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
        /* navigate to the target */
    }

    private boolean seesTarget() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();

        return containsTarget(obstacles);
    }

    private void avoidGuards() {
        /* run away from the seen guard */
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
        for (Tile t : obstacles) {
            if (t.getType().equals(TileType.TARGET)) {
                return true;
            }
        }
        return false;
    }
}
