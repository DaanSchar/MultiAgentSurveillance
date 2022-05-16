package com.mygdx.game.views.fleet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.maastrichtuniversity.dke.logic.agents.Fleet;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

public class FleetView extends Group {

    private final Fleet<Guard> guards;
    private final Fleet<Intruder> intruders;

    public FleetView(Scenario scenario) {
        this.guards = scenario.getGuards();
        this.intruders = scenario.getIntruders();

        addAgents();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private void addAgents() {
        for (Guard guard : guards) {
            addActor(new AgentView(guard));
        }
        for (Intruder intruder : intruders) {
            addActor(new AgentView(intruder));
        }
    }

}
