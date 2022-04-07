package com.mygdx.game.views.fleet;

import com.badlogic.gdx.scenes.scene2d.Group;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;

import java.util.List;

public class CommunicationView extends Group {
    private final List<CommunicationMark> markers;

    public CommunicationView(List<CommunicationMark> markers) {
        this.markers = markers;

        addMarkers();
    }

    private void addMarkers() {
        for (CommunicationMark mark : markers) {
            addActor(new MarkerView(mark));
        }
    }
}
