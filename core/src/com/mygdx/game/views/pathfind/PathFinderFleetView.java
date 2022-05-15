package com.mygdx.game.views.pathfind;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Fleet;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;

public class PathFinderFleetView extends Group {

    private final Fleet<Guard> guards;
    private final Fleet<Intruder> intruders;
    private @Getter @Setter boolean showPath;

    public PathFinderFleetView(Scenario scenario, boolean showPath) {
        this(scenario);
        this.showPath = showPath;
    }

    public PathFinderFleetView(Scenario scenario) {
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
        if (showPath) {
            super.draw(batch, parentAlpha);
        }
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        if (showPath) {
            for (Actor agentView : getChildren()) {
                PathFinderAgentView pathFinderAgentView = (PathFinderAgentView) agentView;
                pathFinderAgentView.draw(shapeRenderer, 1f);
            }
        }
    }
}
