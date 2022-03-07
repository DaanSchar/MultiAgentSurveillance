package nl.maastrichtuniversity.dke.agents.modules.vision;

import lombok.Getter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.LinkedList;
import java.util.List;

public class VisionModule extends AgentModule implements IVisionModule {
    private final double viewingDistance;

    @Getter private List<Agent> agents = new LinkedList<>();
    @Getter private List<Tile> obstacles = new LinkedList<>();

    public VisionModule(Scenario scenario, double viewingDistance) {
        super(scenario);
        this.viewingDistance = viewingDistance;
    }

    /**
     *  Method adds Tile/Agent to respective list,if is visible
     * @param position  position of agent
     * @param direction direction agent is facing
     */
    @Override
    public void useVision(Position position, Direction direction) {
        agents.clear();
        obstacles.clear();

        List<Agent> scenario_agents = new LinkedList<>();
        Tile[][] tilemap = scenario.getEnvironment().getTileMap();


        boolean[] canMove = canMove(position, direction);
        boolean canMove1 = canMove[0];
        boolean canMove2 = canMove[1];

        boolean obstruct0 = false;
        boolean obstruct1 = false;
        boolean obstruct2 = false;
        for (int i = 1; i < viewingDistance; i++) {

            obstruct0 = checkTile(tilemap, scenario_agents, position, direction, obstruct0, true, 0, 0, i);
            obstruct1 = checkTile(tilemap, scenario_agents, position, direction, obstruct1, canMove1, -1, -1, i);
            obstruct2 = checkTile(tilemap, scenario_agents, position, direction, obstruct2, canMove2, 1, 1, i);

        }
    }



    /**
     * This method, checks if tile is visible and non-empty, then  it adds it.
     *
     * @param tilemap   the map of the environment
     * @param obstruct  - true if obstacle blocking vision, false if not
     * @param canMove   - true if possible to see a neighbouring columns, false if not
     * @param moveX     shift in X
     * @param moveY     shift in Y
     * @param i         iteration
     * @return true if obstacle blocking vision, false if not
     */
    private boolean checkTile(Tile[][] tilemap, List<Agent> agents, Position position, Direction direction,
                              boolean obstruct, boolean canMove, int moveX, int moveY, int i) {
        if (!obstruct) {
            if (canMove) {
                Position coordinates = getCoordinates(direction, position, i, moveX, moveY);
                if (coordinates.getX() < 0 || coordinates.getY() < 0) return true;
                obstruct = checkIfObstructed(tilemap, coordinates.getX(), coordinates.getY());
                addAgentIfPresent(agents, new Position( coordinates.getX(), coordinates.getY()));
            }
        }
        return obstruct;
    }

    private boolean checkIfObstructed(Tile[][] tilemap, int x, int y) {
        boolean obstruct = false;
        Tile tmp = tilemap[x][y];

        if (!tmp.isEmpty()) {
            if (!tmp.isOpened()) {
                obstruct = true;
            }
            obstacles.add(tmp);

        }
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
        int x_coordinate;
        int y_coordinate;
        if (direction.name().equals("NORTH") || direction.name().equals("SOUTH")) {
            x_coordinate = (position.getX() + moveX) + direction.getMoveX() * iteration;
            y_coordinate = (position.getY()) + direction.getMoveY() * iteration;
        } else {
            x_coordinate = (position.getX()) + direction.getMoveX() * iteration;
            y_coordinate = (position.getY() + moveY) + direction.getMoveY() * iteration;
        }

        return new Position(x_coordinate, y_coordinate);
    }


    private boolean[] canMove(Position position, Direction direction) {
        boolean canMove1 = false;
        boolean canMove2 = false;

        if (direction.name().equals("NORTH") || direction.name().equals("SOUTH")) {
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