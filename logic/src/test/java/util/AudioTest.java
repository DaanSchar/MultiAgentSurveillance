package util;

import nl.maastrichtuniversity.dke.agents.modules.sound.ListeningModule;
import nl.maastrichtuniversity.dke.agents.modules.sound.NoiseModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AudioTest {
    Scenario s;
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

        Tile[][] tilemap = new Tile[5][5];

        for (int i = 0; i < tilemap.length; i++) {
            for (int j = 0; j < tilemap.length; j++) {
                if (i < 2) {
                    tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);
                } else if (i == 2) {
                    tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);
                } else tilemap[i][j] = new Tile(new Position(i, j), TileType.EMPTY);

            }
        }
        Environment e = new Environment(5,5,tilemap);
        s.setEnvironment(e);
    }
    /**
     *   4  e e e e e
     *   3  e e e e e
     *   2  s e e e e
     *   1  s s e e e
     *   0  S s s e e
     *      0 1 2 3 4
     */
    @Test
    public void testNoiseGeneration1(){
        NoiseModule noiseModule = new NoiseModule(s,2,3,0);
        Position agentPosition = new Position(0,0);
        noiseModule.makeWalkingSound(agentPosition);
        List<Sound> soundMap = s.getSoundMap();
        for(Sound sound:soundMap){
            int X = sound.getPosition().getX();
            int Y = sound.getPosition().getY();
            assert(Math.abs(X)-Math.abs(agentPosition.getX()) <= 2);
            assert(Math.abs(Y)-Math.abs(agentPosition.getY())<= 2);
        }
    }

    @Test
    public void testNoiseGeneration2(){
        NoiseModule noiseModule = new NoiseModule(s,2,3,0);
        Position agentPosition = new Position(2,3);
        noiseModule.makeWalkingSound(agentPosition);
        List<Sound> soundMap = s.getSoundMap();
        for(Sound sound:soundMap){
            int X = sound.getPosition().getX();
            int Y = sound.getPosition().getY();
            assert(Math.abs(X)-Math.abs(agentPosition.getX()) <= 2);
            assert(Math.abs(Y)-Math.abs(agentPosition.getY())<= 2);
        }
    }

    @Test
    public void listeningModuleTest(){
        NoiseModule noiseModule = new NoiseModule(s,2,3,0);
        ListeningModule listeningModule = new ListeningModule(s);
        Position sourceSound = new Position(0,0);
        noiseModule.makeWalkingSound(sourceSound);
        List<Sound> soundMap = s.getSoundMap();

        for (int i = 0; i < s.getEnvironment().getTileMap().length; i++) {
            for (int j = 0; j < s.getEnvironment().getTileMap().length; j++) {
                Position pos = new Position(i,j);
                for(Sound sound:soundMap){
                    assert !sound.getPosition().equals(pos) || (listeningModule.getSound(pos));
                }
            }
        }

    }


    /**
     *  x = positions from where we are checking the direction of sound S
     *   0  e e x e x
     *   1  x e e e e
     *   2  x e S e x
     *   3  x e e e x
     *   4  e e x e e
     *      0 1 2 3 4
     */
//    @Test
    public void directionSoundTest1(){
        NoiseModule noiseModule = new NoiseModule(s,2,3,0);
        ListeningModule listeningModule = new ListeningModule(s);
        Position sourceSound = new Position(2,2);
        noiseModule.makeWalkingSound(sourceSound);
        List<Sound> soundMap = s.getSoundMap();
        List<Direction> directions = listeningModule.getDirection(new Position(0,2));
        assert (directions.get(0).equals(Direction.EAST));
        directions = listeningModule.getDirection(new Position(2,0));
        assert (directions.get(0).equals(Direction.SOUTH));
        directions = listeningModule.getDirection(new Position(4,2));
        assert (directions.get(0).equals(Direction.WEST));
        directions = listeningModule.getDirection(new Position(2,4));
        assert (directions.get(0).equals(Direction.NORTH));
    }

//    @Test
    public void directionSoundTest2(){
        NoiseModule noiseModule = new NoiseModule(s,5,3,0);
        ListeningModule listeningModule = new ListeningModule(s);
        Position sourceSound = new Position(2,2);
        noiseModule.makeWalkingSound(sourceSound);
        List<Sound> soundMap = s.getSoundMap();
        List<Direction> directions = listeningModule.getDirection(new Position(0,3));
        assert (directions.get(0).equals(Direction.NORTHEAST));
        directions = listeningModule.getDirection(new Position(0,1));
        assert (directions.get(0).equals(Direction.SOUTHEAST));
        directions = listeningModule.getDirection(new Position(4,0));
        assert (directions.get(0).equals(Direction.SOUTHWEST));
        directions = listeningModule.getDirection(new Position(4,3));
        assert (directions.get(0).equals(Direction.NORTHWEST));

    }

}
