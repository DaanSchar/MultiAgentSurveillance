package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;

import java.util.Arrays;

public class EnvironmentView implements View {

    private final Environment environment;
    private final Texture[][] textures;
    private final TileView[][] tiles;
    private final double[][] heightMap;

    private @Getter final int width;
    private @Getter final int height;

    public EnvironmentView(Environment environment) {
        this.environment = environment;
        this.textures = new Texture[environment.getWidth()][environment.getHeight()];
        this.tiles = new TileView[environment.getWidth()][environment.getHeight()];
        final int octaveCount = 10;
        this.heightMap = PerlinNoiseGenerator.generatePerlinNoise(environment.getWidth(), environment.getHeight(), octaveCount);
        normalizeHeightMap();

        this.width = environment.getWidth() * TextureRepository.getInstance().getTextureWidth();
        this.height = environment.getHeight() * TextureRepository.getInstance().getTextureWidth();

        initTiles();
    }

    public void draw(Batch batch) {
        for (int i = 0; i < textures.length; i++) {
            for (int j = 0; j < textures[0].length; j++) {
                tiles[i][j].draw(batch);
            }
        }
    }

    private void initTiles() {
        for (int i = 0; i < textures.length; i++) {
            for (int j = 0; j < textures[0].length; j++) {
                tiles[i][j] = new TileView(environment.getTileMap()[i][j], heightMap[i][j]);
            }
        }
    }

    private void normalizeHeightMap() {
        double max = Arrays.stream(heightMap).flatMapToDouble(Arrays::stream).max().getAsDouble();
        double min = Arrays.stream(heightMap).flatMapToDouble(Arrays::stream).min().getAsDouble();

        Arrays.stream(heightMap).forEach(row -> Arrays.stream(row).map(value -> value = (value - min) / (max - min)));
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[0].length; j++) {
                heightMap[i][j] = (heightMap[i][j] - min) / (max - min);
            }
        }
    }

}
