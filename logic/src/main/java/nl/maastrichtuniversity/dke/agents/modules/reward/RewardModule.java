package nl.maastrichtuniversity.dke.agents.modules.reward;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RewardModule extends AgentModule implements IRewardModule {

    @Setter @Getter private double moveReward;
    @Setter @Getter private Direction targetDirection;

    private List<Position> previousPositions;

    public RewardModule(Scenario scenario) {
        super(scenario);
    }

    @Override
    public double getReward() {
        return moveReward;
    }

    @Override
    public double updateFleeingReward(Position position, Direction direction) {
        Intruder intruder = (Intruder) scenario.getIntruders().getCurrentAgent();

        if (intruder.isFleeing()) {
            moveReward += 2;
        } else { // I think this is better, since it will entice intruder to not be in flee mode
            moveReward += 10;
        }

        if (isStuck(position, direction)) {
            moveReward -= 1;
        }

//        double distanceRewardScalar = -0.1;
//
//        for (Guard guard : intruder.getVisibleGuards()) {
//            double distance = intruder.getPosition().distance(guard.getPosition());
//            moveReward += distance * distanceRewardScalar;
//        }

        return moveReward;
    }


    @Override
    public boolean isInTargetDirection(Position p, Direction d) {
        List<Tile> targetTiles = super.scenario.getEnvironment().get(TileType.TARGET);

        Position fin = p.add(getDistanceToBorder(p, d));
        return isTargetBetweenPositions(targetTiles, p, fin);
    }

    @Override
    public double updateMoveReward(Position p, Direction d) {
        moveReward = 0;

        for (Tile tile : scenario.getIntruders().getCurrentAgent().getMemoryModule().getCoveredTiles()) {
            if (tile.getPosition().getX() == p.getX() && tile.getPosition().getY() == p.getY()) {
                moveReward -= 1;
                break;
            }
        }

        if (previousPositions != null) {
            if (previousPositions.contains(p)) {
                checkPreviousPositions(p);
                moveReward -= 3;
            }
        }
        checkPreviousPositions(p);
        if (isStuck(p, d)) {
            moveReward -= 3;
        }

        if (isInTargetDirection(p, d)) {
            moveReward += 3;
            moveReward += scenario.getIntruders().getCurrentAgent().getVisionModule().targetTilesSeen() * 10;
        }
        if (isOnTarget(p)) {
            log.info("Found Treasure");
            moveReward += 800;
        }


        return moveReward;
    }

    private void checkPreviousPositions(Position p) {
        if (previousPositions == null) {
            previousPositions = new ArrayList<>();
        }


        if (previousPositions.size() == 50) {
            previousPositions.remove(0);
        }
        previousPositions.add(p);
    }

    private boolean isStuck(Position p, Direction d) {
        return !scenario.getIntruders().getCurrentAgent().getMovement().agentCanMoveTo(p.add(
                new Position(d.getMoveX(), d.getMoveY()))
        );
    }

    private boolean isOnTarget(Position p) {
        return scenario.getEnvironment().getTileMap()[p.getX()][p.getY()].getType() == TileType.TARGET;
    }


    /**
     * @param targetTiles tiles where target contained
     * @param aPosition   position of the agent
     * @param fPosition   position of the agent if heads only in direction d
     * @return if target in direction of agent
     */
    private boolean isTargetBetweenPositions(List<Tile> targetTiles, Position aPosition, Position fPosition) {
        for (Tile tile : targetTiles) {
            Position targetPosition = tile.getPosition();
            if (isInBetween(targetPosition.getX(), aPosition.getX(), fPosition.getX())) {
                return true;
            } else if (isInBetween(targetPosition.getX(), fPosition.getX(), aPosition.getX())) {
                return true;
            } else if (isInBetween(targetPosition.getY(), aPosition.getY(), fPosition.getY())) {
                return true;
            } else if (isInBetween(targetPosition.getY(), fPosition.getY(), aPosition.getY())) {
                return true;
            }
        }
        return false;
    }


    private boolean isInBetween(int c, int a, int b) {
        return c > a && c < b;
    }

    private Position getDistanceToBorder(Position position, Direction direction) {
        int moveX = 0;
        int moveY = 0;

        if (direction == Direction.EAST) {
            moveX = scenario.getEnvironment().getWidth() - position.getX();
        } else if (direction == Direction.WEST) {
            moveX = -position.getX();
        } else if(direction == Direction.NORTH) {
            moveY = -position.getY();
        } else {
            moveY = scenario.getEnvironment().getHeight() - position.getY();
        }

        return new Position(moveX, moveY);
    }

}
