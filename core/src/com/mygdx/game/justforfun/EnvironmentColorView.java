package com.mygdx.game.justforfun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.util.PerlinNoiseGenerator;
import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;

import java.util.HashMap;

public class EnvironmentColorView {

    private final Environment environment;
    private HashMap<String, double[][]> colorMap;

    private @Getter
    final int width;
    private @Getter
    final int height;

    private final int tileSize = 64;


    public EnvironmentColorView(Environment environment) {
        this.environment = environment;
        this.width = environment.getWidth() * tileSize;
        this.height = environment.getHeight() * tileSize;
        this.colorMap = generateColorMaps();
    }

    public void draw(ShapeRenderer batch) {
        for (int i = 0; i < environment.getWidth(); i++) {
            for (int j = 0; j < environment.getHeight(); j++) {
                Color color = getColor(i, j);
                drawRectangle(batch, i, j, color);
            }
        }
    }

    private void drawRectangle(ShapeRenderer batch, int i, int j, Color color) {
        batch.rect(
                i * tileSize,
                j * tileSize,
                tileSize,
                tileSize,
                color, color, color, color
        );
    }

    private Color getColor(int i, int j) {
        TileType tileType = environment.getTileMap()[i][j].getType();

        switch (tileType) {
            case WALL -> {
                return generateWallColor(i, j);
            }
            case EMPTY -> {
                return generateEmptyColor(i, j);
            }
            case SPAWN_GUARDS -> {
                return generateSpawnColor(i, j);
            }
            default -> {
                return generateEmptyColor(i, j);
            }
        }
    }

    private Color generateSpawnColor(int i, int j) {
        return new Color(
                (float) colorMap.get("g")[i][j],
                (float) colorMap.get("r")[i][j],
                (float) colorMap.get("b")[i][j],
                1f
        );
    }

    private Color generateWallColor(int i, int j) {
        return new Color(
                (float) colorMap.get("b")[i][j],
                (float) colorMap.get("r")[i][j],
                (float) colorMap.get("g")[i][j],
                1f
        );
    }

    private Color generateEmptyColor(int i, int j) {
        return new Color(
                (float) colorMap.get("r")[i][j],
                (float) colorMap.get("g")[i][j],
                (float) colorMap.get("b")[i][j],
                1f
        );
    }

    private HashMap<String, double[][]> generateColorMaps() {
        HashMap<String, double[][]> hashMap = new HashMap<>();

        hashMap.put("r", generateMap());
        hashMap.put("g", generateMap());
        hashMap.put("b", generateMap());

        return hashMap;
    }

    private double[][] generateMap() {
        final int octaveCount = 10;

        return PerlinNoiseGenerator.generatePerlinNoise(
                environment.getWidth(),
                environment.getHeight(),
                octaveCount);
    }

}
