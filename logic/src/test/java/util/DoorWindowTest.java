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

public class DoorWindowTest {

    private Scenario scenario;
    private Tile[][] tileMap;
    private Position spawnPosition;
    private Position doorPosition;
    private Position windowPosition;

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
        this.spawnPosition = new Position(2, 1);
        this.doorPosition = new Position(2, 2);
        this.windowPosition = new Position(2, 3);
        tileMap[2][1] = new Tile(spawnPosition, TileType.SPAWN_GUARDS);
        tileMap[2][2] = new DoorTile(doorPosition);
        tileMap[2][3] = new WindowTile(windowPosition);
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
        spawnGuard(guard);

        assertGuardAtPosition(guard, this.spawnPosition);

        moveGuardForward(guard);

        assertGuardCantWalkThroughClosedDoor(guard);

        guard.toggleDoor();
        moveGuardForward(guard);

        assertGuardAtDoorPosition(guard);
        assertWindowIsClosed();

        moveGuardForward(guard);

        assertGuardAtDoorPosition(guard);

        guard.breakWindow();

        assertWindowIsBroken();

        moveGuardForward(guard);

        assertGuardAtPosition(guard, this.windowPosition);
    }

    void assertWindowIsClosed() {
        WindowTile windowTile = (WindowTile) tileMap[2][3];
        Assertions.assertFalse(windowTile.isBroken());
    }

    void assertWindowIsBroken() {
        WindowTile windowTile = (WindowTile) tileMap[2][3];
        Assertions.assertTrue(windowTile.isBroken());
    }

    void assertDoorIsOpened() {
        DoorTile doorTile = (DoorTile) tileMap[2][2];
        Assertions.assertTrue(doorTile.isOpened());
    }

    void assertGuardAtDoorPosition(Guard guard) {
        assertDoorIsOpened();
        assertGuardAtPosition(guard, doorPosition);
    }

    void assertGuardCantWalkThroughClosedDoor(Guard guard) {
        moveGuardForward(guard);
        assertDoorIsClosed();
        assertGuardAtPosition(guard, this.spawnPosition);
    }

    void assertDoorIsClosed() {
        DoorTile doorTile = (DoorTile) tileMap[2][2];
        Assertions.assertFalse(doorTile.isOpened());
    }

    void assertGuardAtPosition(Guard guard, Position position) {
        Assertions.assertEquals(guard.getPosition(), position);
    }

    void spawnGuard(Guard guard) {
        guard.spawn();
        guard.setDirection(Direction.SOUTH);
        guard.updateInternals();
    }

    void moveGuardForward(Guard guard) {
        guard.move(MoveAction.MOVE_FORWARD);
        guard.updateInternals();
        scenario.incrementTimeStep();
    }

}
