package util;

import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SprintTest {
    Scenario s;
    Tile[][] tilemap;
    /** setup empty environment
     *  e- empty
     *  P - player
     *  O - obstacle
     *
     *   4  e e e e e
     *   3  e e e e e
     *   2  e e e e e
     *   1  e e e e e
     *   0  e e e e e
     *      0 1 2 3 4
     */
    @BeforeEach
    void setup() {
        s = new Scenario("Test", 0, 0, 0, null);

        tilemap = new Tile[5][5];

        for (int i = 0; i < tilemap.length; i++) {
            for (int j = 0; j < tilemap.length; j++) {
                tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);
            }
        }
        Environment e = new Environment(5,5,tilemap);
        s.setEnvironment(e);
    }

    @Test
    void testSprint(){
    }

}

