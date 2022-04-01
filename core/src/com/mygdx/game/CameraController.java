package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

public final class CameraController implements InputProcessor {

    private @Getter Viewport viewport;
    private @Getter OrthographicCamera camera;

    private final float worldWidth;
    private final float worldHeight;

    private final float DRAG_MULTIPLIER;

    public CameraController(EnvironmentView environmentView) {
        this.worldWidth = environmentView.getWidth();
        this.worldHeight = environmentView.getHeight();
        this.DRAG_MULTIPLIER = (Gdx.graphics.getWidth() / worldWidth) * 20f;

        setupViewPort();
    }

    private void setupViewPort() {
        camera = new OrthographicCamera();
        centerCameraPosition();

        viewport = new FillViewport(worldWidth, worldHeight, camera);
        viewport.apply();
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        float maxZoom = 2.0f;
        float minZoom = 0.1f;
        float zoomStep = 0.05f;

        if (amountY > 0 && camera.zoom < maxZoom) {
            camera.zoom += zoomStep;
        } else if (amountY < 0 && camera.zoom > minZoom) {
            camera.zoom -= zoomStep;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float dragX = Gdx.input.getDeltaX();
        float dragY = Gdx.input.getDeltaY();

        float moveCameraX = -dragX * DRAG_MULTIPLIER * camera.zoom;
        float moveCameraY = dragY * DRAG_MULTIPLIER * camera.zoom;

        camera.translate(moveCameraX, moveCameraY);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    private void centerCameraPosition() {
        float middleX = worldWidth / 2f;
        float middleY = worldHeight / 2f;
        camera.position.set(middleX, middleY, 0);
    }
}