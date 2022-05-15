package com.mygdx.game.views.pathfind;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.TextureRepository;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.Path;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.LinkedList;
import java.util.Queue;

public class PathFinderAgentView extends Actor {

    private final Agent agent;
    private Color pathColor;
    private Color targetColor;

    private @Getter @Setter boolean showPath;

    public PathFinderAgentView(Agent agent, boolean showPath) {
        this(agent);
        this.showPath = showPath;
    }

    public PathFinderAgentView(Agent agent) {
        this.agent = agent;
        this.pathColor = new Color(0.7f, 0.2f, 0.3f, 0.7f);
        this.targetColor = new Color(1f, 1f, 1f, 0.4f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        if (showPath) {
            drawPathFills(batch);
//        }
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
//        if (showPath) {
            drawPathLines(shapeRenderer);
            drawTargetLines(shapeRenderer);
//        }
    }

    private Queue<Position> getPath() {
        Path path = agent.getPath();

        if (path == null) {
            return new LinkedList<>();
        }

        return path.getPath();
    }

    private void drawPathLines(ShapeRenderer shapeRenderer) {
        for (Position position : getPath()) {
            drawLines(shapeRenderer, position);
        }
    }

    private void drawTargetLines(ShapeRenderer shapeRenderer) {
        Position target = agent.getTarget();

        if (target != null) {
            drawLines(shapeRenderer, target);
        }
    }

    private void drawLines(ShapeRenderer shapeRenderer, Position position) {
        shapeRenderer.rect(
                position.getX(),
                position.getY(),
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT,
                pathColor, pathColor, pathColor, pathColor
        );
    }

    private void drawPathFills(Batch batch) {
        for (Position position :getPath()) {
            drawPathFill(batch, position);
        }
        drawTargetFill(batch);
    }

    private void drawPathFill(Batch batch, Position tilePosition) {
        drawRectangle(batch, tilePosition, pathColor);
    }

    private void drawTargetFill(Batch batch) {
        Position target = agent.getTarget();

        if (target != null) {
            drawRectangle(batch, agent.getTarget(), targetColor);
        }
    }

    private void drawRectangle(Batch batch, Position position, Color color) {
        batch.draw(
                getPixmapTexture(color),
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private Texture getPixmapTexture(Color color) {
        return new Texture(getPixmapRectangle(color));
    }

    public static Pixmap getPixmapRectangle(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }

}
