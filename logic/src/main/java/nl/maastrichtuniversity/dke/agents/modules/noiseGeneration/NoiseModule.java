package nl.maastrichtuniversity.dke.agents.modules.noiseGeneration;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.util.Position;
import nl.maastrichtuniversity.dke.scenario.Sound;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NoiseModule extends AgentModule implements INoiseModule {

    private final double hearingDistanceWalking;
    private final double hearingDistanceSprinting;
    private final double hearingDistanceInteraction;
    private final Environment environment;

    public NoiseModule(Scenario scenario, double hearingDistanceWalking, double hearingDistanceSprinting,
                       double hearingDistanceInteraction) {
        super(scenario);
        this.hearingDistanceSprinting = hearingDistanceSprinting;
        this.hearingDistanceWalking = hearingDistanceWalking;
        this.hearingDistanceInteraction = hearingDistanceInteraction;
        this.environment = scenario.getEnvironment();
    }

    public void makeSound(Position position, SoundType soundType) {
        List<Sound> sounds = new ArrayList<>();

        for (Tile tile: environment) {
            double distanceToTile = position.distance(tile.getPosition());

            if (distanceToTile <= getHearingDistance(soundType)) {
                Sound sound = new Sound(tile.getPosition(), position, soundType);
                sounds.add(sound);
            }
        }

        scenario.addSounds(sounds);
    }

    private double getHearingDistance(SoundType soundType) {
        return switch (soundType) {
            case WALK -> hearingDistanceWalking;
            case SPRINT, YELL -> hearingDistanceSprinting;
            case TOGGLE_DOOR, BREAK_WINDOW -> hearingDistanceInteraction;
        };
    }











    @Override
    public void makeWalkingSound(Position position) {
        List<Sound> walkingSounds = new ArrayList<>();

        for (Tile tile: scenario.getEnvironment()) {
            if (position.distance(tile.getPosition()) <= hearingDistanceWalking) {
                Sound walkingSound = new Sound(tile.getPosition(), position, SoundType.WALK);
                walkingSounds.add(walkingSound);
            }
        }

        scenario.addSounds(walkingSounds);
    }

    @Override
    public void makeSprintingSound(Position position) {
        List<Sound> sprintingSounds = new ArrayList<>();

        for (Tile tile: scenario.getEnvironment()) {
            if (position.distance(tile.getPosition()) <= hearingDistanceSprinting) {
                Sound sprintingSound = new Sound(tile.getPosition(), position, SoundType.WALK);
                scenario.getSoundMap().add(sprintingSound);
            }
        }

        scenario.addSounds(sprintingSounds);
    }

    @Override
    public void yell(Position position) {
        Tile[][] tileMap = scenario.getEnvironment().getTileMap();
        for (Tile[] tiles : tileMap) {
            for (Tile tile : tiles) {
                if (position.distance(tile.getPosition()) <= hearingDistanceSprinting) {
                    Sound sound = new Sound(tile.getPosition(), position, SoundType.WALK);
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
                    Sound sound = new Sound(tile.getPosition(), position, SoundType.WALK);
                    scenario.getSoundMap().add(sound);
                }
            }
        }
    }

}
