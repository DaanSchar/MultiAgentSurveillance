package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import lombok.Getter;

public class MovableStage extends Stage {

    private final CameraController cameraController;

    private final float worldWidth;
    private final float worldHeight;

    private final @Getter ShapeRenderer shapeRenderer;

    public MovableStage(float worldWidth, float worldHeight) {
        super();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.cameraController = new CameraController(this);
        this.shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        setupViewport();
    }

    private void setupViewport() {
        super.setViewport(new FillViewport(worldWidth, worldHeight, cameraController.getCamera()));
        super.getViewport().apply();
    }

    @Override
    public void draw() {
        super.draw();
        updateCamera();
    }

    private void updateCamera() {
        cameraController.update(getBatch());
        cameraController.update(getShapeRenderer());
    }

    public void resize(int width, int height) {
        super.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public float getWidth() {
        return worldWidth;
    }

    @Override
    public float getHeight() {
        return worldHeight;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return cameraController.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return cameraController.scrolled(amountX, amountY);
    }

    @Override
    public boolean keyDown(int keyCode) {
        return cameraController.keyDown(keyCode);
    }
}
