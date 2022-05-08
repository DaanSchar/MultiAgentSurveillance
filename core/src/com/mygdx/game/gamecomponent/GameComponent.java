package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameGUI;
import com.mygdx.game.views.environment.EnvironmentView;
import com.mygdx.game.views.communication.CommunicationView;
import com.mygdx.game.views.environment.PathFinderView;
import com.mygdx.game.views.fleet.FleetView;
import com.mygdx.game.views.smell.SmellView;
import com.mygdx.game.views.sound.SoundView;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

public class GameComponent extends MovableStage {

    private EnvironmentView environmentView;
    private FleetView fleetView;
    private PathFinderView pathFinderView;
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
        Agent agent = scenario.getIntruders().get(0);
        this.pathFinderView = new PathFinderView(agent.getPathFinderModule(), agent);
        this.clear();
        this.addActor(environmentView);
        this.addActor(fleetView);
        this.addActor(communicationView);
        this.addActor(pathFinderView);
    }

    @Override
    public void draw() {
        super.draw();
        drawShapes();
    }

    public void update() {
        environmentView.update();
        pathFinderView.update();
    }

    private void drawShapes() {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Point);
        soundView.draw(getShapeRenderer(), 0f);
        smellView.draw(getShapeRenderer(), 1f);
        getShapeRenderer().end();
    }

    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode) {
            case Input.Keys.R -> resetGame();
            case Input.Keys.G -> toggleMemoryView();
            case Input.Keys.MINUS -> GameGUI.incrementTimeInterval();
            case Input.Keys.EQUALS -> GameGUI.decrementTimeInterval();
            case Input.Keys.P -> pauseGame();
            case Input.Keys.D -> togglePathFindView();
        }

        return super.keyDown(keyCode);
    }

    private void resetGame() {
        game.reset();
        reset(game.getScenario());
    }

    private void toggleMemoryView() {
        boolean isShowMemory = environmentView.isShowMemoryMap();
        environmentView.setShowMemoryMap(!isShowMemory);
    }

    private void togglePathFindView() {
        boolean isShowPath = pathFinderView.isShowPath();
        pathFinderView.setShowPath(!isShowPath);
    }

    private void pauseGame() {
        GameGUI.togglePause();
    }

}
