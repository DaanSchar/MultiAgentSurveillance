package com.mygdx.game.views.brickandmortar;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;

public class BrickAndMortarView extends Group {

    private @Getter @Setter boolean show;

    private final Environment environment;

    public BrickAndMortarView(Agent agent, boolean show) {
        this(agent);
        this.show = show;
    }

    public BrickAndMortarView(Agent agent) {
        this.environment = agent.getMemoryModule().getMap();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (show) {
            super.draw(batch, parentAlpha);
        }
    }

    public void update() {
        super.clear();
        addTiles();
    }

    private void addTiles() {
        for (Tile tile : environment) {
            addTile(tile);
        }
    }

    private void addTile(Tile tile) {
        if (isVisible(tile)) {
            this.addActor(new TileView((MemoryTile) tile));
        }
    }

    private boolean isVisible(Tile tile) {
        MemoryTile memoryTile = (MemoryTile) tile;
        return memoryTile.isVisited() && memoryTile.isExplored();
    }

}
