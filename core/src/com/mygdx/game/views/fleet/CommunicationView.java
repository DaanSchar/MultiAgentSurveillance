package com.mygdx.game.views.fleet;

import com.badlogic.gdx.scenes.scene2d.Group;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

import java.util.List;

public class CommunicationView extends Group {
    private final List<CommunicationMark> markers;

    public CommunicationView(Scenario scenario) {
        this.markers = scenario.getCommunicationMarks();

        addMarkers();
    }

    private void addMarkers() {
        for (CommunicationMark mark : markers) {
            addActor(new MarkerView(mark));
        }
    }
}
