package nl.maastrichtuniversity.dke.agents;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.communication.CommunicationType;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Intruder extends Agent {

    private @Getter @Setter boolean isCaught;
    private boolean navigatedToBlueMark; // whether the agent has navigated to the mark that another agent dropped
    private boolean droppedBlueMark; // did it drop the mark already

    public Intruder() {
        super();
        this.isCaught = false;
        this.navigatedToBlueMark = false;
        this.droppedBlueMark = false;
    }

    @Override
    public void spawn() {
        super.spawn();
        this.isCaught = false;
    }

    @Override
    public void move() {
        if (hasTarget()) {
            if (hasReachedTarget()) {
                setTarget(null);
            } else {
                moveToPosition(getTarget());
            }
        } else {
            super.explore();
        }
        super.move();
    }

    @Override
    public void updateInternals() {
//        if (seesTargetArea()) {
//            setTarget(getTargetTile().getPosition());
//        }
        super.updateInternals();
    }

    private boolean seesTargetArea() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();

        return containsTarget(obstacles);
    }

    // TODO: this method is buggy and needs to be fixed.
    private void avoidGuards() {
        /* run away from the seen guard */
//        List<Guard> visibleGuards = getVisibleGuards();
//        Position avoid;
//        Position toGuard = getPathFinderModule().getShortestPath(
//              getPosition(),
//              visibleGuards.get(0).getPosition()
//        ).get(0);
//
//        if (toGuard.getX() != getPosition().getX()) {
//            if (toGuard.getX() < getPosition().getX()) {
//               avoid  = new Position(getPosition().getX() + 1, getPosition().getY());
//            } else {
//                avoid = new Position(getPosition().getX() - 1, getPosition().getY());
//            }
//        } else {
//            if (toGuard.getY() < getPosition().getY()) {
//                avoid = new Position(getPosition().getX(), getPosition().getY() + 1);
//            } else {
//                avoid = new Position(getPosition().getX(), getPosition().getY() - 1);
//            }
//        }
//        List<Position> q = new ArrayList<>();
//        q.add(avoid);
//        setNavigationQueue(q);
//        moveToPosition(avoid);

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

    private Tile getTargetTile() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();
        return obstacles.stream().filter(tile -> tile.getType().equals(TileType.TARGET)).findFirst().get();
    }

    private boolean seesBlueMark() {
        List<Tile> obstacles = super.getVisionModule().getVisibleTiles();

        return containsBlueMark(obstacles);
    }

    private boolean containsBlueMark(List<Tile> obstacles) {
        for (Tile tile : obstacles) {
            if (isBlueMark(tile)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBlueMark(Tile tile) {
        Position position = tile.getPosition();

        return super.getCommunicationModule().tileHasMark(position, CommunicationType.VISION_BLUE);

    }

    private Position getBlueMark() {
        return super.getCommunicationModule().getMark(CommunicationType.VISION_BLUE);
    }

    private void navigateToBlueMark() {
        Position target = getBlueMark();
        moveToPosition(target);
    }

}
