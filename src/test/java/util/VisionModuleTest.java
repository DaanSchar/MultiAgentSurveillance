package util;


import lombok.ToString;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.modules.vision.VisionModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
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
        s = new Scenario("Test", 0, 0, 0, null);

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
        VisionModule vm = new VisionModule(s,4);
        s.getEnvironment().getTileMap()[0][1].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[0][0].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[2][0].setType(TileType.WALL);

        vm.useVision(new Position(1,1), Direction.NORTH);
        List<Tile> obstacles =vm.getObstacles();
        System.out.println(obstacles);


        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(1, 1));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(0, 1));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(2, 1));
        Assertions.assertEquals(obstacles.get(3).getPosition(), new Position(1, 0));
        Assertions.assertEquals(obstacles.get(4).getPosition(), new Position(2, 0));
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
        VisionModule vm = new VisionModule(s,4);
        s.getEnvironment().getTileMap()[3][0].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[1][1].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[0][2].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[2][3].setType(TileType.WALL);


        vm.useVision(new Position(1,0), Direction.SOUTH);
        List<Tile> obstacles =vm.getObstacles();

        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(1,0));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(0,0));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(2,0));
        Assertions.assertEquals(obstacles.get(3).getPosition(), new Position(1,1));
        Assertions.assertEquals(obstacles.get(4).getPosition(), new Position(0,1));
        Assertions.assertEquals(obstacles.get(5).getPosition(), new Position(2,1));
        Assertions.assertEquals(obstacles.get(6).getPosition(), new Position(0,2));
        Assertions.assertEquals(obstacles.get(7).getPosition(), new Position(2,2));
        Assertions.assertEquals(obstacles.get(8).getPosition(), new Position(2, 3));
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
        VisionModule vm = new VisionModule(s,4);
        s.getEnvironment().getTileMap()[1][0].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[3][1].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[2][0].setType(TileType.WALL);

        vm.useVision(new Position(0,0), Direction.EAST);
        List<Tile> obstacles =vm.getObstacles();
        for(Tile t : obstacles){
            System.out.println("new Position("+t.getPosition().getX()+","+t.getPosition().getY()+"));");
        }
        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(0,0));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(0,1));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(1,0));
        Assertions.assertEquals(obstacles.get(3).getPosition(), new Position(1,1));
        Assertions.assertEquals(obstacles.get(4).getPosition(), new Position(2,0));
        Assertions.assertEquals(obstacles.get(5).getPosition(), new Position(2,1));
        Assertions.assertEquals(obstacles.get(6).getPosition(), new Position(3,1));
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
        VisionModule vm = new VisionModule(s,4);
        s.getEnvironment().getTileMap()[3][1].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[1][0].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[0][1].setType(TileType.WALL);


        vm.useVision(new Position(3,0), Direction.WEST);
        List<Tile> obstacles =vm.getObstacles();

         Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(3,0));
         Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(3,1));
         Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(2,0));
         Assertions.assertEquals(obstacles.get(3).getPosition(), new Position(1,0));
    }

    /** shaded test, can only see one tile after a shaded tile
     *   3  e e O e
     *   2  O e e e
     *   1  e S e e
     *   0  e P e O
     *      0 1 2 3
     */
    @Test
    void testShaded(){
        VisionModule vm = new VisionModule(s,4);
        s.getEnvironment().getTileMap()[3][0].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[1][1].setType(TileType.SHADED);
        s.getEnvironment().getTileMap()[0][2].setType(TileType.WALL);
        s.getEnvironment().getTileMap()[2][3].setType(TileType.WALL);


        vm.useVision(new Position(1,0), Direction.SOUTH);
        List<Tile> obstacles =vm.getObstacles();

        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(1,0));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(0,0));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(2,0));
        Assertions.assertEquals(obstacles.get(3).getPosition(), new Position(1,1));
        Assertions.assertEquals(obstacles.get(4).getPosition(), new Position(0,1));
        Assertions.assertEquals(obstacles.get(5).getPosition(), new Position(2,1));
        Assertions.assertEquals(obstacles.get(6).getPosition(), new Position(1,2));
        Assertions.assertEquals(obstacles.get(7).getPosition(), new Position(0,2));
        Assertions.assertEquals(obstacles.get(8).getPosition(), new Position(2,2));
        Assertions.assertEquals(obstacles.get(9).getPosition(), new Position(2, 3));


    }

    /** sentry test, do not see first two tiles, but see double viewing distance
     *   3  e e e P
     *   2  e e e e
     *   1  e e e e
     *   0  e e e e
     *      0 1 2 3
     */
    @Test
    void testSentryWEST(){
        VisionModule vm = new VisionModule(s,2);
        s.getEnvironment().getTileMap()[3][0].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[1][1].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[0][2].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[2][3].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[3][3].setType(TileType.SENTRY);


        vm.useVision(new Position(3,3), Direction.WEST);
        List<Tile> obstacles =vm.getObstacles();
        System.out.println(obstacles);

        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(1,3));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(1,2));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(0,3));
        Assertions.assertEquals(obstacles.get(3).getPosition(), new Position(0,2));


    }

    /** sentry test, do not see first two tiles, but see double viewing distance
     *   3  e e e e
     *   2  e e e e
     *   1  e e P e
     *   0  e e e e
     *      0 1 2 3
     */
    @Test
    void testSentrySOUTH(){
        VisionModule vm = new VisionModule(s,2);
        s.getEnvironment().getTileMap()[3][0].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[1][1].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[0][2].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[2][3].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[2][1].setType(TileType.SENTRY);


        vm.useVision(new Position(2,1), Direction.SOUTH);
        List<Tile> obstacles =vm.getObstacles();

        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(2,3));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(1,3));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(3,3));


    }

    /** sentry test, do not see first two tiles, but see double viewing distance
     *   3  e e e e
     *   2  e e P e
     *   1  e e e e
     *   0  e e e e
     *      0 1 2 3
     */
    @Test
    void testSentryNORTH(){
        VisionModule vm = new VisionModule(s,2);
        s.getEnvironment().getTileMap()[3][0].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[1][1].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[0][2].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[2][3].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[2][2].setType(TileType.SENTRY);


        vm.useVision(new Position(2,2), Direction.NORTH);
        List<Tile> obstacles =vm.getObstacles();
        System.out.println(obstacles);
        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(2,0));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(1,0));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(3,0));
    }



    /** sentry test, do not see first two tiles, but see double viewing distance
     *   3  e e e e
     *   2  e P e e
     *   1  e e e e
     *   0  e e e e
     *      0 1 2 3
     */
    @Test
    void testSentryEAST(){
        VisionModule vm = new VisionModule(s,2);
        s.getEnvironment().getTileMap()[3][0].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[1][1].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[0][2].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[2][3].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[1][2].setType(TileType.SENTRY);


        vm.useVision(new Position(1,2), Direction.EAST);
        List<Tile> obstacles =vm.getObstacles();
        System.out.println(obstacles);
        Assertions.assertEquals(obstacles.get(0).getPosition(), new Position(3,2));
        Assertions.assertEquals(obstacles.get(1).getPosition(), new Position(3,1));
        Assertions.assertEquals(obstacles.get(2).getPosition(), new Position(3,3));
    }


    /** sentry test, do not see first two tiles, but see double viewing distance
     *   3  e e e e
     *   2  e e e e
     *   1  e e P e
     *   0  e e e e
     *      0 1 2 3
     */
    @Test
    void testSentry(){
        VisionModule vm = new VisionModule(s,10);
        s.getEnvironment().getTileMap()[3][0].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[1][1].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[0][2].setType(TileType.EMPTY);
        s.getEnvironment().getTileMap()[2][3].setType(TileType.EMPTY);
        //s.getEnvironment().getTileMap()[1][2].setType(TileType.SENTRY);


        vm.useVision(new Position(2,1), Direction.SOUTH);
        List<Tile> obstacles =vm.getObstacles();
        System.out.println(obstacles);
    }





}
