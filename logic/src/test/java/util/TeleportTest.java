package util;

import nl.maastrichtuniversity.dke.logic.agents.modules.movement.MovementModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TeleportTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeleportTest {
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
                if (i < 2) {
                    tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);
                } else if (i == 2) {
                    tilemap[i][j] = new TeleportTile(new Position(i, j), 4,4,1);
                } else tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);

            }
        }
        Environment e = new Environment(5,5,tilemap);
        s.setEnvironment(e);
    }

    @Test
    void testTeleportTiles(){
        assert (tilemap[2][0].getType().equals(TileType.TELEPORT));
        assert (tilemap[2][1].getType().equals(TileType.TELEPORT));
        assert (tilemap[2][2].getType().equals(TileType.TELEPORT));
        assert (tilemap[2][3].getType().equals(TileType.TELEPORT));
        assert (tilemap[2][4].getType().equals(TileType.TELEPORT));
    }

    @Test
    void testTeleport(){
        MovementModule movementModule = new MovementModule(s,1,1);
        Position position = movementModule.goForward(new Position(1,2), Direction.EAST);
        assert (position.equals(new Position(4,4)));
    }
}
