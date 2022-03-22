package nl.maastrichtuniversity.dke.logic.agents.modules.vision;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.LinkedList;
import java.util.List;

@Getter
public class VisionModule extends AgentModule implements IVisionModule {

    private final int viewingDistance;

    private List<Agent> agents = new LinkedList<>();
    private List<Tile> obstacles = new LinkedList<>();

    public VisionModule(Scenario scenario, int viewingDistance) {
        super(scenario);
        this.viewingDistance = viewingDistance;
    }

    /**
     * Method adds Tile/Agent to respective list,if is visible
     *
     * @param position  position of agent
     * @param direction direction agent is facing
     */
    @Override
    public void useVision(Position position, Direction direction) {
        agents.clear();
        obstacles.clear();
        boolean[] isShaded = {false, false, false};
        boolean[] checkedShaded = {false, false, false};



        List<Agent> scenario_agents = new LinkedList<>();
        Tile[][] tilemap = scenario.getEnvironment().getTileMap();
        int endTile = getEndTile(position,direction);
        int startTile = getStartTile(position);

        System.out.println(startTile + " START");
        System.out.println(endTile + " END");
        boolean[] canMove = canMove(position, direction);
        boolean canMove1 = canMove[0];
        boolean canMove2 = canMove[1];

        boolean obstruct0 = false;
        boolean obstruct1 = false;
        boolean obstruct2 = false;
        for (int i = startTile; i <= endTile; i++) {
            if (!isShaded[0]) {
                obstruct0 = checkTile(tilemap, scenario_agents, position, direction, obstruct0, true, isShaded, 0, 0, i);

            } else if (!checkedShaded[0]) {
                obstruct0 = checkTile(tilemap, scenario_agents, position, direction, obstruct0, true, isShaded, 0, 0, i);
                checkedShaded[0] = true;

            }
            if (!isShaded[1]) {
                obstruct1 = checkTile(tilemap, scenario_agents, position, direction, obstruct1, canMove1, isShaded, -1, -1, i);
            } else if (!checkedShaded[1]) {
                obstruct1 = checkTile(tilemap, scenario_agents, position, direction, obstruct1, canMove1, isShaded, -1, -1, i);
                checkedShaded[1] = true;

            }
            if (!isShaded[2]) {
                obstruct2 = checkTile(tilemap, scenario_agents, position, direction, obstruct2, canMove2, isShaded, 1, 1, i);
            } else if (!checkedShaded[2]) {
                obstruct2 = checkTile(tilemap, scenario_agents, position, direction, obstruct2, canMove2, isShaded, 1, 1, i);
                checkedShaded[2] = true;

            }

        }
    }

    private int getStartTile(Position position){
        if(scenario.getEnvironment().getTileMap()[position.getX()][position.getY()].getType()==TileType.SENTRY){
            return 2;
        }else return 0;
    }
    private int getEndTile(Position position,Direction direction){
        int end;
        int sentryVision = viewingDistance*2;
        if(direction==Direction.SOUTH ){
            end = (scenario.getEnvironment().getHeight()-1)- position.getY();
        }else if (direction==Direction.NORTH){
            end = position.getY();
        }else if(direction==Direction.EAST){
            end = (scenario.getEnvironment().getWidth()-1)-position.getX();
        }else end = position.getX();

        if(scenario.getEnvironment().getTileMap()[position.getX()][position.getY()].getType()==TileType.SENTRY){
            return Math.min(sentryVision, end);
        }else return Math.min(end,viewingDistance);
    }


    /**
     * This method, checks if tile is visible and non-empty, then  it adds it.
     *
     * @param tilemap  the map of the environment
     * @param obstruct - true if obstacle blocking vision, false if not
     * @param canMove  - true if possible to see a neighbouring columns, false if not
     * @param moveX    shift in X
     * @param moveY    shift in Y
     * @param i        iteration
     * @return true if obstacle blocking vision, false if not
     */
    private boolean checkTile(Tile[][] tilemap, List<Agent> agents, Position position, Direction direction,
                              boolean obstruct, boolean canMove, boolean[] isShaded, int moveX, int moveY, int i) {
        if (!obstruct) {
            if (canMove) {
                Position coordinates = getCoordinates(direction, position, i, moveX, moveY);
                if (coordinates.getX() < 0 || coordinates.getY() < 0) return true;
                obstruct = checkIfObstructed(tilemap, isShaded, coordinates.getX(), coordinates.getY(), moveX);
                addAgentIfPresent(agents, new Position(coordinates.getX(), coordinates.getY()));
            }
        }
        return obstruct;
    }


    private boolean checkIfObstructed(Tile[][] tilemap, boolean[] isShaded, int x, int y, int moveX) {
        boolean obstruct = false;




        Tile tmp = tilemap[x][y];
        if (tmp.getType() == TileType.SHADED) {
            if (moveX == 0) {
                isShaded[0] = true;
            } else if (moveX == -1) {
                isShaded[1] = true;
            } else {
                isShaded[2] = true;
            }
        }

        if (!tmp.isOpened() && tmp.getType() == TileType.WALL) { // only non-transparent tile-type is wall?
            obstruct = true;
        }
        obstacles.add(tmp);

        return obstruct;
    }

    private void addAgentIfPresent(List<Agent> scenario_agents, Position p) {
        for (Agent a : scenario_agents) {
            if (a.getPosition().equals(p)) {
                agents.add(a);
                break;
            }
        }
    }

    private Position getCoordinates(Direction direction, Position position, int iteration, int moveX, int moveY) {
        Position coordinate;

        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            coordinate = new Position(
                    (position.getX() + moveX) + direction.getMoveX() * iteration,
                    (position.getY()) + direction.getMoveY() * iteration
            );
        } else {
            coordinate = new Position(
                    (position.getX()) + direction.getMoveX() * iteration,
                    (position.getY() + moveY) + direction.getMoveY() * iteration
            );
        }

        return coordinate;
    }


    private boolean[] canMove(Position position, Direction direction) {
        boolean canMove1 = false;
        boolean canMove2 = false;

        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            if (position.getX() - 1 >= 0) {
                canMove1 = true;
            }
            if (position.getX() + 1 < scenario.getEnvironment().getWidth()) {
                canMove2 = true;
            }
        } else {
            if (position.getY() - 1 >= 0) {
                canMove1 = true;
            }
            if (position.getY() + 1 < scenario.getEnvironment().getHeight()) {
                canMove2 = true;
            }
        }

        return new boolean[]{canMove1, canMove2};
    }
}