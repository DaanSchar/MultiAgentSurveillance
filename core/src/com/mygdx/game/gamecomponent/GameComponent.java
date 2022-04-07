package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.views.environment.EnvironmentView;
import com.mygdx.game.views.fleet.FleetView;
import com.mygdx.game.views.sound.SoundView;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;

public class GameComponent extends MovableStage {

    private EnvironmentView environmentView;
    private FleetView fleetView;
    private SoundView soundView;

    private final Game game;

    public GameComponent(Game game) {
        super(game.getScenario().getEnvironment().getWidth(), game.getScenario().getEnvironment().getHeight());
        this.game = game;
        reset(game.getScenario());
    }

    public void reset(Scenario scenario) {
        this.environmentView = new EnvironmentView(scenario.getEnvironment());
        this.fleetView = new FleetView(scenario);
        this.soundView = new SoundView(scenario);
    }

    public void draw() {
        super.draw();
        drawTextures();
        drawShapes();
    }

    private void drawTextures() {
        getBatch().begin();
        environmentView.draw(getBatch(), 1f);
        fleetView.draw(getBatch(), 1f);
        getBatch().end();
    }

    private void drawShapes() {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Point);
        soundView.draw(getShapeRenderer(), 0f);
        getShapeRenderer().end();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.R) {
            game.reset();
            reset(game.getScenario());
            return true;
        } else {
            return super.keyDown(keyCode);
        }
    }
}
