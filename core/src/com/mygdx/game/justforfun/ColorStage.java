package com.mygdx.game.justforfun;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.PerlinNoiseGenerator;

public class ColorStage extends ShapeRendererStage {

    private final double[][] heightMapR;
    private final double[][] heightMapG;
    private final double[][] heightMapB;
    private final int size;

    public ColorStage(int size) {
        super(100*10, 100*10);
        this.size = size;
        this.heightMapR = PerlinNoiseGenerator.generatePerlinNoise(100, 100, 10);
        this.heightMapG = PerlinNoiseGenerator.generatePerlinNoise(100, 100, 10);
        this.heightMapB = PerlinNoiseGenerator.generatePerlinNoise(100, 100, 10);
    }

    @Override
    public void draw() {
        super.draw();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < heightMapR.length; i++) {
            for (int j = 0; j < heightMapR[i].length; j++) {
                Color color = new Color((float) heightMapR[i][j], (float) heightMapG[i][j], (float) heightMapB[i][j], 1f);
                shapeRenderer.rect(i * size, j * size, size, size,color, color, color, color);
            }
        }
        shapeRenderer.end();
    }


    @Override
    public float getWidth() {
        return size*100;
    }

    @Override
    public float getHeight() {
        return size*100;
    }

//    @Override
//    public void resize(int width, int height) {
//        super.resize(width, height);
//    }
}
