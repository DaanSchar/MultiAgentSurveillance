package com.mygdx.game.views.communication;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

import java.util.List;

public class CommunicationView extends Group {
    private final List<CommunicationMark> markers;

    public CommunicationView(Scenario scenario) {
        this.markers = scenario.getCommunicationMarks();
    }

    public void update() {
        super.clear();
        addMarkers();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        update();
    }

    private void addMarkers() {
        for (CommunicationMark mark : markers) {
            addActor(new MarkerView(mark));
        }
    }
}
