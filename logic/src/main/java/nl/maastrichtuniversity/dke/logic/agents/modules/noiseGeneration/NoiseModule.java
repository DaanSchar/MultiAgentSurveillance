package nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration;

import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;

public class NoiseModule extends AgentModule implements INoiseModule {
    private final double hearingDistanceWalking;
    private final double hearingDistanceSprinting;
    private final double hearingDistanceInteraction;
    private final Environment environment;

    public NoiseModule(Scenario scenario, double hearingDistanceWalking, double hearingDistanceSprinting, double hearingDistanceInteraction) {
        super(scenario);
        this.hearingDistanceSprinting = hearingDistanceSprinting;
        this.hearingDistanceWalking = hearingDistanceWalking;
        this.hearingDistanceInteraction = hearingDistanceInteraction;
        environment = scenario.getEnvironment();
    }

    @Override
    public void makeWalkingSound(Position position) {
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for (Tile[] tiles : tileMap) {
            for (Tile tile : tiles) {
                if (position.distance(tile.getPosition()) <= hearingDistanceWalking) {
                    Sound sound = new Sound(tile.getPosition(), position, false);
                    scenario.getSoundMap().add(sound);
                }
            }
        }

    }

    @Override
    public void makeSprintingSound(Position position) {
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for (Tile[] tiles : tileMap) {
            for (Tile tile : tiles) {
                if (position.distance(tile.getPosition()) <= hearingDistanceSprinting) {
                    Sound sound = new Sound(tile.getPosition(), position, false);
                    scenario.getSoundMap().add(sound);
                }
            }
        }
    }

    @Override
    public void Yell(Position position) {
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for (Tile[] tiles : tileMap) {
            for (Tile tile : tiles) {
                if (position.distance(tile.getPosition()) <= hearingDistanceSprinting) {
                    Sound sound = new Sound(tile.getPosition(), position, true);
                    scenario.getSoundMap().add(sound);
                }
            }
        }
    }

    @Override
    public void makeInteractionNoise(Position position) {
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for (Tile[] tiles : tileMap) {
            for (Tile tile : tiles) {
                if (position.distance(tile.getPosition()) <= hearingDistanceInteraction) {
                    Sound sound = new Sound(tile.getPosition(), position, false);
                    scenario.getSoundMap().add(sound);
                }
            }
        }
    }
}
