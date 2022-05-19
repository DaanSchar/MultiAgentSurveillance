package nl.maastrichtuniversity.dke.agents.modules.reward;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Guard;
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

    @Setter
    @Getter
    public double moveReward;
    @Setter
    @Getter
    Direction targetDirection;

    private List<Position> previousPositions;

    public RewardModule(Scenario scenario) {
        super(scenario);
    }


    @Override
    public double getReward() {
        return moveReward;
    }


    @Override
    public double updateFleeingReward(Position p, Direction d, double[] inputs) {
        Intruder i = (Intruder) scenario.getIntruders().getCurrentAgent();
        if (inputs[0] == 1 && !i.isCaught()) {
            moveReward += 1;
        } else moveReward = 0;


        for(Guard g : i.getVisibleGuards()){
            double distance = i.getPosition().distance(g.getPosition());
            moveReward-=distance*0.1; // or some other scalar
        }

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
        if (IsOnTarget(p)) {
            log.info("Found Treasure");
            moveReward += 800;
        }


        return moveReward;
    }

    private void checkPreviousPositions(Position p) {
        if (previousPositions == null) {
            previousPositions = new ArrayList<>();
        }


        if (previousPositions.size() == 20) {
            previousPositions.remove(0);
        }
        previousPositions.add(p);
    }

    private boolean isStuck(Position p, Direction d) {
        return !scenario.getIntruders().getCurrentAgent().getMovement().agentCanMoveTo(p.add(new Position(d.getMoveX(), d.getMoveY())));
    }

    private boolean IsOnTarget(Position p) {
        return scenario.getEnvironment().getTileMap()[p.getX()][p.getY()].getType() == TileType.TARGET;
    }


    /**
     * @param target_tiles tiles where target contained
     * @param a_position   position of the agent
     * @param f_position   position of the agent if heads only in direction d
     * @return if target in direction of agent
     */
    private boolean isTargetBetweenPositions(List<Tile> target_tiles, Position a_position, Position f_position) {
        for (Tile tile : target_tiles) {
            Position target_position = tile.getPosition();
            if (isInBetween(target_position.getX(), a_position.getX(), f_position.getX())) {
                return true;
            } else if (isInBetween(target_position.getX(), f_position.getX(), a_position.getX())) {
                return true;
            } else if (isInBetween(target_position.getY(), a_position.getY(), f_position.getY())) {
                return true;
            } else if (isInBetween(target_position.getY(), f_position.getY(), a_position.getY())) {
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

        switch (direction) {
            case EAST -> moveX = scenario.getEnvironment().getWidth() - position.getX();
            case WEST -> moveX = -position.getX();
            case NORTH -> moveY = -position.getY();
            case SOUTH -> moveY = scenario.getEnvironment().getHeight() - position.getY();

        }

        return new Position(moveX, moveY);
    }


}