package util;

import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.*;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoorTest {

    private Scenario scenario;
    private Tile[][] tileMap;

    @BeforeEach
    void setup() {
        scenario = new Scenario("Test", 0, 0, 0, null);

        tileMap = new Tile[5][5];

        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap.length; j++) {
                tileMap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);
            }
        }

        tileMap[2][2] = new DoorTile(new Position(2, 2));
        Environment e = new Environment(5,5,tileMap);
        scenario.setEnvironment(e);
    }

    @Test
    void tileAtLocationIsDoor() {

        Assertions.assertEquals(TileType.DOOR, tileMap[2][2].getType());
    }
}
