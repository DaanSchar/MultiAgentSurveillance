package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.views.environment.EnvironmentView;
import com.mygdx.game.views.fleet.FleetView;
import com.mygdx.game.views.sound.SoundView;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;

public class GameComponent extends MovableStage {

    private Scenario scenario;

    private EnvironmentView environmentView;
    private FleetView fleetView;
    private SoundView soundView;

    public GameComponent(Scenario scenario, Environment environment) {
        super(environment.getWidth(), environment.getHeight());
        this.scenario = scenario;
        this.environmentView = new EnvironmentView(scenario.getEnvironment());
        this.fleetView = new FleetView(scenario.getGuards(), scenario.getIntruders());
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

}
