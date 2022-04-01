package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;

public class GameComponent {

    private final EnvironmentView environmentView;

    private final SpriteBatch batch;
    private final CameraController cameraController;

    public GameComponent(Environment environment) {
        this.environmentView = new EnvironmentView(environment);
        this.cameraController = new CameraController(environmentView);
        this.batch = new SpriteBatch();
        Gdx.input.setInputProcessor(cameraController);
    }

    public void draw() {
        updateCamera();
        drawBatch();
    }

    public void resize(int width, int height) {
        cameraController.resize(width, height);
    }

    public void dispose() {
        batch.dispose();
    }

    private void updateCamera() {
        cameraController.updateBatch(batch);
    }

    private void drawBatch() {
        batch.begin();
        environmentView.draw(batch);
        batch.end();
    }

}
