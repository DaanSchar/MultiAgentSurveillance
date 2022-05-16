package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameGUI;
import com.mygdx.game.views.brickandmortar.BrickAndMortarView;
import com.mygdx.game.views.environment.EnvironmentView;
import com.mygdx.game.views.communication.CommunicationView;
import com.mygdx.game.views.fleet.FleetView;
import com.mygdx.game.views.pathfind.PathFinderView;
import com.mygdx.game.views.smell.SmellView;
import com.mygdx.game.views.sound.SoundView;
import com.mygdx.game.views.vision.VisionView;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.scenario.Scenario;

public class GameComponent extends MovableStage {

    private EnvironmentView environmentView;
    private FleetView fleetView;
    private PathFinderView pathFinderView;
    private CommunicationView communicationView;
    private SoundView soundView;
    private SmellView smellView;
    private BrickAndMortarView brickAndMortarView;
    private VisionView visionView;

    private boolean showMemory;
    private boolean showPath;
    private boolean showSound;
    private boolean showBrickAndMortar;
    private boolean showVision;

    private final Game game;

    public GameComponent(Game game) {
        super(game.getScenario().getEnvironment().getWidth(), game.getScenario().getEnvironment().getHeight());
        this.game = game;
        reset(game.getScenario());
    }

    public void reset(Scenario scenario) {
        createNewViews(scenario);
        this.clear();
        addViewsToStage();
    }

    @Override
    public void draw() {
        super.draw();
        drawOutlines();
    }

    public void update() {
        environmentView.update();
    }

    private void createNewViews(Scenario scenario) {
        this.environmentView = new EnvironmentView(scenario, showMemory);
        this.pathFinderView = new PathFinderView(scenario, showPath);
        this.fleetView = new FleetView(scenario);
        this.soundView = new SoundView(scenario, showSound);
        this.smellView = new SmellView(scenario);
        this.communicationView = new CommunicationView(scenario);
        this.visionView = new VisionView(scenario, showVision);

        Agent agent = scenario.getGuards().get(0);
        this.brickAndMortarView = new BrickAndMortarView(agent, showBrickAndMortar);
    }

    private void addViewsToStage() {
        this.addActor(environmentView);
        this.addActor(soundView);
        this.addActor(communicationView);
        this.addActor(pathFinderView);
        this.addActor(brickAndMortarView);
        this.addActor(visionView);
        this.addActor(fleetView);
    }

    private void drawOutlines() {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Point);
        environmentView.draw(getShapeRenderer(), 1f);
        smellView.draw(getShapeRenderer(), 1f);
        soundView.draw(getShapeRenderer(), 1f);
        brickAndMortarView.draw(getShapeRenderer(), 1f);
        visionView.draw(getShapeRenderer(), 1f);
        pathFinderView.draw(getShapeRenderer(), 1f);
        getShapeRenderer().end();
    }

    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode) {
            case Input.Keys.G -> toggleMemoryView();
            case Input.Keys.D -> togglePathFindView();
            case Input.Keys.S -> toggleSoundView();
            case Input.Keys.B -> toggleBrickAndMortarView();
            case Input.Keys.V -> toggleVisionView();
            case Input.Keys.P -> pauseGame();
            case Input.Keys.R -> resetGame();
            case Input.Keys.MINUS -> GameGUI.incrementTimeInterval();
            case Input.Keys.EQUALS -> GameGUI.decrementTimeInterval();
            default -> super.keyDown(keyCode);
        }

        update();
        return super.keyDown(keyCode);
    }

    private void resetGame() {
        game.reset();
        reset(game.getScenario());
    }

    private void toggleVisionView() {
        showVision = !showVision;
        visionView.setVisible(showVision);
    }

    private void toggleBrickAndMortarView() {
        showBrickAndMortar = !showBrickAndMortar;
        brickAndMortarView.setVisible(showBrickAndMortar);
    }

    private void toggleSoundView() {
        this.showSound = !this.showSound;
        soundView.setVisible(showSound);
    }

    private void toggleMemoryView() {
        this.showMemory = !this.showMemory;
        environmentView.setShowMemoryMap(showMemory);
    }

    private void togglePathFindView() {
        this.showPath = !this.showPath;
        pathFinderView.setVisible(showPath);
    }

    private void pauseGame() {
        GameGUI.togglePause();
    }

}
