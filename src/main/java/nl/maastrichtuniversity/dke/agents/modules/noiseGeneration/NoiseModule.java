package nl.maastrichtuniversity.dke.agents.modules.noiseGeneration;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.Vector;

public class NoiseModule extends AgentModule implements INoiseModule {
    private final double hearingDistanceWalking;
    private final double hearingDistanceSprinting;
    private final Environment environment;

    public NoiseModule(Scenario scenario, double hearingDistanceWalking, double hearingDistanceSprinting) {
        super(scenario);
        this.hearingDistanceSprinting = hearingDistanceSprinting;
        this.hearingDistanceWalking = hearingDistanceWalking;
        environment = scenario.getEnvironment();
    }

    @Override
    public void makeWalkingSound(Position position) {
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();

        for(Tile[] tiles:tileMap){
            for(Tile tile:tiles){
                if(position.distance(tile.getPosition()) <= hearingDistanceWalking){
                    //System.out.println("pos" + tile.getPosition());
                    scenario.getSoundMap().add(tile.getPosition());
                }
            }
        }
    }

    @Override
    public void makeSprintingSound(Position position) {
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for(Tile[] tiles:tileMap){
            for(Tile tile:tiles){
                if(position.distance(tile.getPosition()) <= hearingDistanceSprinting){
                    scenario.getSoundMap().add(position);
                }
            }
        }
    }
}
