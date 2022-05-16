package com.mygdx.game.views.pathfind;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Path;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.LinkedList;
import java.util.Queue;

public class PathFinderAgentView extends Actor {

    private final Agent agent;
    private final Color pathColor;
    private final Color targetColor;

    private final TextureRepository texturesRepository;

    public PathFinderAgentView(Agent agent) {
        this.agent = agent;
        this.pathColor = new Color(0.7f, 0.2f, 0.3f, 0.7f);
        this.targetColor = new Color(1f, 1f, 1f, 0.4f);
        this.texturesRepository = TextureRepository.getInstance();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawTiles(batch);
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        drawPathOutlines(shapeRenderer);
        drawTargetOutlines(shapeRenderer);
    }

    private void drawPathOutlines(ShapeRenderer shapeRenderer) {
        for (Position position : getPath()) {
            drawOutlines(shapeRenderer, position);
        }
    }

    private void drawTargetOutlines(ShapeRenderer shapeRenderer) {
        Position target = agent.getTarget();

        if (target != null) {
            drawOutlines(shapeRenderer, target);
        }
    }

    private void drawOutlines(ShapeRenderer shapeRenderer, Position position) {
        shapeRenderer.rect(
                position.getX(),
                position.getY(),
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT,
                pathColor, pathColor, pathColor, pathColor
        );
    }

    private void drawTiles(Batch batch) {
        for (Position position :getPath()) {
            drawPathTile(batch, position);
        }
        drawTargetTile(batch);
    }

    private void drawPathTile(Batch batch, Position tilePosition) {
        drawRectangle(batch, tilePosition, pathColor);
    }

    private void drawTargetTile(Batch batch) {
        Position target = agent.getTarget();

        if (target != null) {
            drawRectangle(batch, agent.getTarget(), targetColor);
        }
    }

    private void drawRectangle(Batch batch, Position position, Color color) {
        batch.draw(
                texturesRepository.getTile(color),
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private Queue<Position> getPath() {
        Path path = agent.getPath();

        if (path == null) {
            return new LinkedList<>();
        }

        return path.getPath();
    }

}
