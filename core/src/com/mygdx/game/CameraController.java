package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.Getter;

public final class CameraController implements InputProcessor {

    private @Getter OrthographicCamera camera;

    private final float worldWidth;
    private final float worldHeight;

    private static final float MAX_ZOOM = 2.0f;
    private static final float MIN_ZOOM = 0.2f;
    private static final float ZOOM_STEP = 0.05f;

    public CameraController(Stage stage) {
        this.worldWidth = stage.getWidth();
        this.worldHeight = stage.getHeight();

        setupCamera();
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        centerCameraPosition();
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY > 0 && camera.zoom < MAX_ZOOM) {
            camera.zoom += ZOOM_STEP;
        } else if (amountY < 0 && camera.zoom > MIN_ZOOM) {
            camera.zoom -= ZOOM_STEP;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float dragX = Gdx.input.getDeltaX();
        float dragY = Gdx.input.getDeltaY();
        float dragMultiplier = 7800f;
        float dragSpeed = (1f / Gdx.graphics.getWidth()) * dragMultiplier;

        float moveCameraX = -dragX * dragSpeed * camera.zoom;
        float moveCameraY = dragY * dragSpeed * camera.zoom;

        camera.translate(moveCameraX, moveCameraY);
        return true;
    }

    public void update(Batch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public void update(ShapeRenderer shapeRenderer) {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
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
