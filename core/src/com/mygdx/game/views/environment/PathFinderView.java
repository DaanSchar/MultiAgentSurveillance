package com.mygdx.game.views.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.TextureRepository;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.agents.modules.pathfind.PathFinderModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.List;

public class PathFinderView extends Group {

    private final PathFinderModule pathFinder;
    private final BitmapFont font;
    private final Agent agent;

    private @Getter @Setter boolean showPath;
    private List<Position> path;

    public PathFinderView(PathFinderModule pathFinder, Agent agent) {
        this.agent = agent;
        this.pathFinder = pathFinder;
        this.showPath = false;
        this.font = new BitmapFont();
        setFotStyle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (showPath) {
            drawDistances(batch);
            drawPath(batch);
        }
    }

    public void update() {
        if (showPath) {
            computePath();
        }
    }

    private void setFotStyle() {
        font.setColor(1, 1, 1, 1);
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.05f);
    }

    private void computePath() {
        Position start = agent.getPosition();
        Position goal = agent.getGoalPosition();
        if (goal == null) {
            goal = start;
        }
        path = getPath(start, goal);
    }

    private void drawDistances(Batch batch) {
        for (Tile tile : pathFinder.getEnvironment()) {
            drawDistanceOfTile(batch, tile);
        }
    }

    private void drawPath(Batch batch) {
        for (Tile tile : pathFinder.getEnvironment()) {
            Position position = tile.getPosition();
            if (path.contains(position)) {
                drawPathTile(batch, tile);
            }
        }
    }

    private void drawDistanceOfTile(Batch batch, Tile tile) {
        Position position = tile.getPosition();
        int distance = pathFinder.getDistanceFromStart(position);

        if (distance == Integer.MAX_VALUE) {
            return;
        }

        Color color = getScaledColor(distance);
        batch.draw(
                getPixmapTexture(color),
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private void drawPathTile(Batch batch, Tile tile) {
        Position position = tile.getPosition();
        Color color = new Color(0f, 1f, 0f, 1f);
        batch.draw(
                getPixmapTexture(color),
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private Color getScaledColor(int distance) {
        float sqrtDistance = (float) Math.sqrt(distance);
        float colorValue = sqrtDistance / (float) (Math.sqrt(maxDistance() / 3f));
        float alpha = sqrtDistance / (float) Math.sqrt(maxDistance());

        if (distance % 15 == 0) {
            return new Color(0, 0, colorValue, alpha);
        } else if (distance % 10 == 0) {
            return new Color(0, colorValue, 0, alpha);
        } else if (distance % 5 == 0) {
            return new Color(colorValue, 0, 0, alpha);
        }

        return new Color(colorValue, colorValue, colorValue, alpha);
    }

    private Texture getPixmapTexture(Color color) {
        return new Texture(getPixmapRectangle( color));
    }

    public static Pixmap getPixmapRectangle(Color color){
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0,0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }

    private float maxDistance() {
        int width = pathFinder.getEnvironment().getWidth();
        int height = pathFinder.getEnvironment().getHeight();
        return (float) Math.sqrt(width * width + height * height);
    }

    private List<Position> getPath(Position start, Position goal) {
        return pathFinder.getShortestPath(start, goal);
    }

}
