package util;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.agents.modules.noiseGeneration.NoiseModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.discrete.TileType;
import nl.maastrichtuniversity.dke.util.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class NoiseGenerationTest {
    Scenario s;
    /** setup empty environment
     *  e- empty
     *  P - player
     *  O - obstacle
     *
     *   3  e e e e
     *   2  e e e e
     *   1  e e e e
     *   0  e e e e
     *      0 1 2 3
     */
    @BeforeEach
    void setup() {
        s = new Scenario("Test", 0, 0, 0);

        Tile[][] tilemap = new Tile[4][4];

        for (int i = 0; i < tilemap.length; i++) {
            for (int j = 0; j < tilemap.length; j++) {
                if (i < 2) {
                    tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);
                } else if (i == 2) {
                    tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);
                } else tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);

            }
        }
        Environment e = new Environment(4,4,tilemap);
        s.setEnvironment(e);


    }
    /*
    @Test
    testNoiseGeneration1(){
        NoiseModule noiseModule = new NoiseModule(s,3,3);
        noiseModule.makeWalkingSound(new Position(0,0));
        assert s.getSoundMap().get()

    }
    */

}
