package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

public class CameraController implements InputProcessor {

    private @Getter Viewport viewport;
    private @Getter OrthographicCamera camera;

    private final float worldWidth;
    private final float worldHeight;

    private final float MAX_ZOOM = 2.0f;
    private final float MIN_ZOOM = 0.1f;
    private final float ZOOM_STEP = 0.05f;
    private final float DRAG_MULTIPLIER;

    public CameraController(EnvironmentView environmentView) {
        this.worldWidth = environmentView.getWidth();
        this.worldHeight = environmentView.getHeight();
        this.DRAG_MULTIPLIER = (Gdx.graphics.getWidth() / worldWidth) * 20f;
        setupViewPort();
    }

    private void setupViewPort() {
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        camera = new OrthographicCamera();
        camera.position.set(worldWidth / 4f, worldHeight / 4f, 0);
        viewport = new FillViewport(worldWidth * aspectRatio, worldHeight * aspectRatio, camera);
        viewport.apply();
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY > 0 && camera.zoom < MAX_ZOOM)
            camera.zoom += ZOOM_STEP;
        else if (amountY < 0 && camera.zoom > MIN_ZOOM)
            camera.zoom -= ZOOM_STEP;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x = Gdx.input.getDeltaX();
        float y = Gdx.input.getDeltaY();

        camera.translate(-x * DRAG_MULTIPLIER * camera.zoom, y * DRAG_MULTIPLIER * camera.zoom);
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
}