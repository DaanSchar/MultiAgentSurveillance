package com.mygdx.game.justforfun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.PerlinNoiseGenerator;

public class ColorStage extends ShapeRendererStage {

    private final double[][] heightMapR;
    private final double[][] heightMapG;
    private final double[][] heightMapB;
    private final int rectangleSize;

    public ColorStage(int rectangleSize, int mapSize) {
        super(rectangleSize * mapSize, rectangleSize * mapSize);
        this.rectangleSize = rectangleSize;
        this.heightMapR = PerlinNoiseGenerator.generatePerlinNoise(mapSize, mapSize, 10);
        this.heightMapG = PerlinNoiseGenerator.generatePerlinNoise(mapSize, mapSize, 10);
        this.heightMapB = PerlinNoiseGenerator.generatePerlinNoise(mapSize, mapSize, 10);
    }

    @Override
    public void draw() {
        super.draw();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < heightMapR.length; i++) {
            for (int j = 0; j < heightMapR[i].length; j++) {
                Color color = new Color(
                        (float) heightMapR[i][j],
                        (float) heightMapG[i][j],
                        (float) heightMapB[i][j], 1f
                );
                shapeRenderer.rect(
                        i * rectangleSize,
                        j * rectangleSize,
                        rectangleSize,
                        rectangleSize,
                        color, color, color, color
                );
            }
        }
        shapeRenderer.end();
    }

}
