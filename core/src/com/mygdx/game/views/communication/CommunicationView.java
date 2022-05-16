package com.mygdx.game.views.communication;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.maastrichtuniversity.dke.agents.modules.communication.Mark;
import nl.maastrichtuniversity.dke.scenario.Scenario;

import java.util.List;

public class CommunicationView extends Group {
    private final List<Mark> markers;

    public CommunicationView(Scenario scenario) {
        this.markers = scenario.getMarks();
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
        for (Mark mark : markers) {
            addActor(new MarkerView(mark));
        }
    }
}
