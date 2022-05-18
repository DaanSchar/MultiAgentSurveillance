package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameGUI;
import com.mygdx.game.util.FleetType;
import com.mygdx.game.views.brickandmortar.BrickAndMortarView;
import com.mygdx.game.views.environment.EnvironmentView;
import com.mygdx.game.views.communication.CommunicationView;
import com.mygdx.game.views.fleet.FleetView;
import com.mygdx.game.views.pathfind.PathFinderView;
import com.mygdx.game.views.smell.SmellView;
import com.mygdx.game.views.sound.SoundView;
import com.mygdx.game.views.vision.VisionView;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Fleet;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.scenario.Scenario;

import java.util.ArrayList;

@Slf4j
public class GameComponent extends MovableStage {

    private EnvironmentView environmentView;
    private FleetView fleetView;
    private PathFinderView pathFinderView;
    private CommunicationView communicationView;
    private SoundView soundView;
    private SmellView smellView;
    private BrickAndMortarView brickAndMortarView;
    private VisionView visionView;

    private int currentAgentIndex;
    private FleetType currentFleet;

    private boolean showMemory;
    private boolean showPath;
    private boolean showSound;
    private boolean showBrickAndMortar;
    private boolean showVision;
    public ArrayList<Boolean> arrayKeys;

    private final Game game;
    private HUD hud;

    public GameComponent(Game game, HUD hud) {
        super(game.getScenario().getEnvironment().getWidth(), game.getScenario().getEnvironment().getHeight());
        this.game = game;
        this.currentFleet = FleetType.GUARD;
        this.currentAgentIndex = 0;
        arrayKeys = new ArrayList<>();
        this.hud=hud;
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
        this.environmentView = new EnvironmentView(scenario, showMemory, currentFleet);
        this.pathFinderView = new PathFinderView(scenario, showPath);
        this.fleetView = new FleetView(scenario);
        this.soundView = new SoundView(scenario, showSound);
        this.smellView = new SmellView(scenario);
        this.communicationView = new CommunicationView(scenario);
        this.visionView = new VisionView(scenario, showVision);
        this.brickAndMortarView = new BrickAndMortarView(getCurrentAgent(), showBrickAndMortar);
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
            case Input.Keys.M -> {
                hud.setKey(5);
                toggleMemoryView();
            }
            case Input.Keys.D -> {
                hud.setKey(4);
                togglePathFindView();
            }
            case Input.Keys.S -> {
                hud.setKey(1);
                toggleSoundView();
            }
            case Input.Keys.B -> {
                hud.setKey(6);
                toggleBrickAndMortarView();
            }
            case Input.Keys.V -> {
                hud.setKey(2);
                toggleVisionView();
            }
            case Input.Keys.P -> {
                hud.setKey(8);
                pauseGame();
            }
            case Input.Keys.R -> {
                resetGame();
            }
            case Input.Keys.MINUS -> {
                hud.setKey(3);
                GameGUI.incrementTimeInterval();
            }
            case Input.Keys.EQUALS -> {
                hud.setKey(3);
                GameGUI.decrementTimeInterval();
            }
            case Input.Keys.Q -> {
                hud.setKey(7);
                toggleCurrentFleet();
            }
            case Input.Keys.NUM_1 -> setCurrentAgentIndex(0);
            case Input.Keys.NUM_2 -> setCurrentAgentIndex(1);
            case Input.Keys.NUM_3 -> setCurrentAgentIndex(2);
            case Input.Keys.NUM_4 -> setCurrentAgentIndex(3);
            case Input.Keys.NUM_5 -> setCurrentAgentIndex(4);
            default -> super.keyDown(keyCode);
        }
        update();
        return super.keyDown(keyCode);
    }


    public ArrayList<Boolean> keysBoolean(){
        return arrayKeys;
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

    private void toggleCurrentFleet() {
        if (currentFleet == FleetType.GUARD) {
            currentFleet = FleetType.INTRUDER;
        } else {
            currentFleet = FleetType.GUARD;
        }
        updateViewsUsingAgents();
    }

    private void setCurrentAgentIndex(int index) {
        if (currentFleet == FleetType.GUARD) {
            if (getGuards().size() > index) {
                currentAgentIndex = index;
            }
        }

        if (currentFleet == FleetType.INTRUDER) {
            if (getIntruders().size() > index) {
                currentAgentIndex = index;
            }
        }
        updateViewsUsingAgents();
    }

    private Agent getCurrentAgent() {
        if (currentFleet == FleetType.GUARD) {
            if (getGuards().size() - 1 < currentAgentIndex) {
                currentAgentIndex = getGuards().size() - 1;
            }

            return getGuards().get(currentAgentIndex);
        } else {
            if (getIntruders().size() - 1 < currentAgentIndex) {
                currentAgentIndex = getIntruders().size() - 1;
            }

            return getIntruders().get(currentAgentIndex);
        }
    }

    private Fleet<Guard> getGuards() {
        return game.getScenario().getGuards();
    }

    private Fleet<Intruder> getIntruders() {
        return game.getScenario().getIntruders();
    }

    private void updateViewsUsingAgents() {
        environmentView.setFleetType(currentFleet);
        log.info("{}", currentAgentIndex);
        brickAndMortarView.setAgent(getCurrentAgent());
    }

}
