package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameGUI;
import com.mygdx.game.views.environment.EnvironmentView;
import com.mygdx.game.views.communication.CommunicationView;
import com.mygdx.game.views.fleet.FleetView;
import com.mygdx.game.views.pathfind.PathFinderFleetView;
import com.mygdx.game.views.smell.SmellView;
import com.mygdx.game.views.sound.SoundView;
import nl.maastrichtuniversity.dke.logic.Game;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

public class GameComponent extends MovableStage {

    private EnvironmentView environmentView;
    private FleetView fleetView;
    private PathFinderFleetView pathFinderFleetView;
    private CommunicationView communicationView;
    private SoundView soundView;
    private SmellView smellView;

    private boolean showMemory;
    private boolean showPath;
    private boolean showSound;

    private final Game game;

    public GameComponent(Game game) {
        super(game.getScenario().getEnvironment().getWidth(), game.getScenario().getEnvironment().getHeight());
        this.game = game;
        reset(game.getScenario());
    }

    public void reset(Scenario scenario) {
        this.environmentView = new EnvironmentView(scenario, showMemory);
        this.pathFinderFleetView = new PathFinderFleetView(scenario, showPath);
        this.fleetView = new FleetView(scenario);
        this.soundView = new SoundView(scenario, showSound);
        this.smellView = new SmellView(scenario);
        this.communicationView = new CommunicationView(scenario);
        this.clear();
        this.addActor(environmentView);
        this.addActor(soundView);
        this.addActor(fleetView);
        this.addActor(communicationView);
        this.addActor(pathFinderFleetView);
    }

    @Override
    public void draw() {
        super.draw();
        drawLines();
    }

    public void update() {
        environmentView.update();
    }

    private void drawLines() {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Point);
        soundView.draw(getShapeRenderer(), 1f);
        smellView.draw(getShapeRenderer(), 1f);
        pathFinderFleetView.draw(getShapeRenderer(), 1f);
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
            case Input.Keys.S -> toggleSoundView();
            default -> super.keyDown(keyCode);
        }

        return super.keyDown(keyCode);
    }

    private void resetGame() {
        game.reset();
        reset(game.getScenario());
    }

    private void toggleSoundView() {
        this.showSound = !this.showSound;
        soundView.setShowSound(showSound);
    }

    private void toggleMemoryView() {
        this.showMemory = !this.showMemory;
        environmentView.setShowMemoryMap(showMemory);
    }

    private void togglePathFindView() {
        this.showPath = !this.showPath;
        pathFinderFleetView.setShowPath(showPath);
    }

    private void pauseGame() {
        GameGUI.togglePause();
    }

}
