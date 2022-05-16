package com.mygdx.game.views.pathfind;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.maastrichtuniversity.dke.agents.Fleet;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.scenario.Scenario;

public class PathFinderView extends Group {

    private final Fleet<Guard> guards;
    private final Fleet<Intruder> intruders;

    public PathFinderView(Scenario scenario, boolean visible) {
        this(scenario);
        setVisible(visible);
    }

    public PathFinderView(Scenario scenario) {
        this.guards = scenario.getGuards();
        this.intruders = scenario.getIntruders();

        for (Guard guard : guards) {
            addActor(new PathFinderAgentView(guard));
        }

        for (Intruder intruder : intruders) {
            addActor(new PathFinderAgentView(intruder));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        if (isVisible()) {
            for (Actor agentView : getChildren()) {
                PathFinderAgentView pathFinderAgentView = (PathFinderAgentView) agentView;
                pathFinderAgentView.draw(shapeRenderer, 1f);
            }
        }
    }
}
