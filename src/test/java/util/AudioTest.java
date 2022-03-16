package util;

import nl.maastrichtuniversity.dke.logic.agents.modules.listening.ListeningModule;
import nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration.NoiseModule;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
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
        NoiseModule noiseModule = new NoiseModule(s,2,3);
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
        NoiseModule noiseModule = new NoiseModule(s,2,3);
        Position agentPosition = new Position(2,3);
        noiseModule.makeWalkingSound(agentPosition);
        List<Sound> soundMap = s.getSoundMap();
        for(Sound sound:soundMap){
            System.out.println(sound.getPosition());
            int X = sound.getPosition().getX();
            int Y = sound.getPosition().getY();
            assert(Math.abs(X)-Math.abs(agentPosition.getX()) <= 2);
            assert(Math.abs(Y)-Math.abs(agentPosition.getY())<= 2);
        }
    }

    @Test
    public void listeningModuleTest(){
        NoiseModule noiseModule = new NoiseModule(s,2,3);
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

}
