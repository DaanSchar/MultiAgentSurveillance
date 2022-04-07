package com.mygdx.game.gamecomponent;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.views.environment.EnvironmentView;
import com.mygdx.game.views.fleet.CommunicationView;
import com.mygdx.game.views.fleet.FleetView;
import com.mygdx.game.views.fleet.MarkerView;
import com.mygdx.game.views.sound.SmellView;
import com.mygdx.game.views.sound.SoundView;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;

public class GameComponent extends MovableStage {

    private Scenario scenario;

    private EnvironmentView environmentView;
    private FleetView fleetView;
    private CommunicationView communicationView;
    private SoundView soundView;
    private SmellView smellView;

    public GameComponent(Scenario scenario, Environment environment) {
        super(environment.getWidth(), environment.getHeight());
        this.scenario = scenario;
        this.environmentView = new EnvironmentView(scenario.getEnvironment());
        this.fleetView = new FleetView(scenario.getGuards(), scenario.getIntruders());
        this.soundView = new SoundView(scenario);
        this.smellView= new SmellView(scenario);
        this.communicationView = new CommunicationView(scenario);
//        System.out.println(scenario.getCommunicationMarks().toString());

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
        communicationView.draw(getBatch(), 1f);
        getBatch().end();
    }

    private void drawShapes() {
        getShapeRenderer().begin(ShapeRenderer.ShapeType.Point);
        soundView.draw(getShapeRenderer(), 0f);
        smellView.draw(getShapeRenderer(), 1f);
        getShapeRenderer().end();
    }

}
