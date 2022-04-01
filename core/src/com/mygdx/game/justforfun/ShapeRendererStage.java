package com.mygdx.game.justforfun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.CameraController;

public class ShapeRendererStage extends Stage {

    protected CameraController cameraController;
    protected ShapeRenderer shapeRenderer;

    private final int worldWidth;
    private final int worldHeight;

    public ShapeRendererStage(int worldWidth, int worldHeight) {
        super();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.cameraController = new CameraController(this);
        this.shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(cameraController);

        setupViewport();
    }

    private void setupViewport() {
        super.setViewport(new FillViewport(worldWidth, worldHeight, cameraController.getCamera()));
        super.getViewport().apply();
    }

    @Override
    public void draw() {
        updateCamera();
    }

    private void updateCamera() {
        cameraController.update(shapeRenderer);
    }

    public void resize(int width, int height) {
        super.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public float getWidth() {
        return super.getWidth();
    }

    @Override
    public float getHeight() {
        return super.getHeight();
    }
}
