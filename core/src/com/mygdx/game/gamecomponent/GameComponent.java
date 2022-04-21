package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameGUI;
import com.mygdx.game.views.environment.EnvironmentView;
import com.mygdx.game.views.communication.CommunicationView;
import com.mygdx.game.views.fleet.FleetView;
import com.mygdx.game.views.smell.SmellView;
import com.mygdx.game.views.sound.SoundView;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

public class GameComponent extends MovableStage {

    private EnvironmentView environmentView;
    private FleetView fleetView;
    private CommunicationView communicationView;
    private SoundView soundView;
    private SmellView smellView;

    private final Game game;

    public GameComponent(Game game) {
        super(game.getScenario().getEnvironment().getWidth(), game.getScenario().getEnvironment().getHeight());
        this.game = game;
        reset(game.getScenario());
    }

    public void reset(Scenario scenario) {
        this.environmentView = new EnvironmentView(scenario);
        this.fleetView = new FleetView(scenario);
        this.soundView = new SoundView(scenario);
        this.smellView = new SmellView(scenario);
        this.communicationView = new CommunicationView(scenario);
        this.clear();
        this.addActor(environmentView);
        this.addActor(fleetView);
        this.addActor(communicationView);
    }

    public void draw() {
        super.draw();
        drawShapes();
    }

    private void drawShapes() {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Point);
        soundView.draw(getShapeRenderer(), 0f);
        smellView.draw(getShapeRenderer(), 1f);
        getShapeRenderer().end();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.R) {
            onKeyPressR();
        }

        if (keyCode == Input.Keys.G) {
            onKeyPressG();
        }

        if (keyCode == Input.Keys.valueOf("-")) {
            GameGUI.incrementTimeInterval();
        }

        if (keyCode == Input.Keys.valueOf("=")) {
            GameGUI.decrementTimeInterval();
        }

        return super.keyDown(keyCode);
    }

    private void onKeyPressR() {
        game.reset();
        reset(game.getScenario());
    }

    private void onKeyPressG() {
        boolean isMemory = environmentView.isShowMemoryMap();
        environmentView.setShowMemoryMap(!isMemory);
    }
}
