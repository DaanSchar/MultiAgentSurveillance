package util;

import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.*;
import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoorTest {

    private Scenario scenario;
    private Tile[][] tileMap;

    @BeforeEach
    void setup() {
        createDummyScenario();
    }

    void createDummyScenario() {
        createEmptyTileMap();
        setDetailsOfTileMap();
        Environment environment = new Environment(5,5,tileMap);
        scenario = new MapParser().createDefaultScenario(environment);
    }

    void setDetailsOfTileMap() {
        tileMap[2][2] = new DoorTile(new Position(2, 2));
        tileMap[2][1] = new Tile(new Position(2, 1), TileType.SPAWN_GUARDS);
    }

    void createEmptyTileMap() {
        tileMap = new Tile[5][5];

        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap.length; j++) {
                tileMap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);
            }
        }
    }

    @Test
    void tileAtLocationIsDoor() {
        Assertions.assertEquals(TileType.DOOR, tileMap[2][2].getType());
    }

    @Test
    void test() {
        Guard guard = scenario.getGuards().get(0);
        guard.spawn();
        Assertions.assertEquals(guard.getPosition(), new Position(2, 1));
        guard.setDirection(Direction.SOUTH);
        guard.updateInternals();
        scenario.incrementTimeStep();
        guard.move(MoveAction.MOVE_FORWARD);

        System.out.println(guard.getPosition());

        guard.toggleDoor();

        scenario.incrementTimeStep();
        scenario.incrementTimeStep();
        guard.move(MoveAction.MOVE_FORWARD);

        System.out.println(guard.getPosition());

        guard.getDirection();
    }

}
