package util;


import nl.maastrichtuniversity.dke.agents.Direction;
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

public class VisionModuleTest {
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

    /** north direction is x:0 y:-1, so down from P
     *   3  e e e e
     *   2  e e e e
     *   1  O P e e
     *   0  O e O e
     *      0 1 2 3
     */
    @Test
    void testGetObstaclesNORTH() {
        VisionModule vm = new VisionModule(s,90);
        s.getEnvironment().getTileMap()[0][1].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[0][0].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[2][0].setType(TileType.WALL);


        List<Tile> obstacles = vm.getObstacles(new Position(1,1), Direction.NORTH);
        System.out.println(obstacles);
        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(0, 0));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(2, 0));
    }

    /** south direction is x:0 y:1, so up from P
     *   3  e e O e
     *   2  O e e e
     *   1  e O e e
     *   0  e P e O
     *      0 1 2 3
     */
    @Test
    void testGetObstaclesSOUTH() {
        VisionModule vm = new VisionModule(s,90);
        s.getEnvironment().getTileMap()[3][0].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[1][1].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[0][2].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[2][3].setType(TileType.WALL);

        List<Tile> obstacles = vm.getObstacles(new Position(1,0), Direction.SOUTH);

        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(1, 1));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(0, 2));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(2, 3));
    }

    /** east direction is x:1 y:0, so right from P
     *   3  e e e e
     *   2  e e e e
     *   1  O e e O
     *   0  P e O e
     *      0 1 2 3
     */
    @Test
    void testGetObstaclesEAST() {
        VisionModule vm = new VisionModule(s,90);
        s.getEnvironment().getTileMap()[1][0].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[3][1].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[2][0].setType(TileType.WALL);


        List<Tile> obstacles = vm.getObstacles(new Position(0,0), Direction.EAST);

        System.out.println(obstacles);
        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(2, 0));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(3, 1));
    }

    /** west direction is x:-1 y:0, so left from P
     *   3  e e e e
     *   2  e e e e
     *   1  O e e O
     *   0  e O e P
     *      0 1 2 3
     */
    @Test
    void testGetObstaclesWEST() {
        VisionModule vm = new VisionModule(s,90);
        s.getEnvironment().getTileMap()[3][1].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[1][0].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[0][1].setType(TileType.WALL);


        List<Tile> obstacles = vm.getObstacles(new Position(3,0), Direction.WEST);

        System.out.println(obstacles);
        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(1, 0));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(0, 1));
    }






}
