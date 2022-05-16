package com.mygdx.game.views.brickandmortar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.util.TextureRepository;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class BrickAndMortarView extends Group {

    private @Getter @Setter boolean show;

    private final Environment environment;

    private final Color exploredColor;
    private final Color visitedColor;

    private final Texture visitedTexture;
    private final Texture exploredTexture;

    public BrickAndMortarView(Agent agent, boolean show) {
        this(agent);
        this.show = show;
    }

    public BrickAndMortarView(Agent agent) {
        this.environment = agent.getMemoryModule().getMap();
        this.visitedColor = new Color(0.2f, 0.2f, 0.2f, 0.7f);
        this.exploredColor = new Color(0.5f, 0.5f, 0.5f, 0.7f);
        this.visitedTexture = getPixmapTexture(visitedColor);
        this.exploredTexture = getPixmapTexture(exploredColor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (show) {
            super.draw(batch, parentAlpha);

            for (Tile tile : environment) {
                drawTileFill(batch, tile);
            }
        }
    }

    public void draw(ShapeRenderer shapeRenderer, float parentAlpha) {
        if (show) {
            for (Tile tile : environment) {
                drawTileOutline(shapeRenderer, tile);
            }
        }
    }

    private void drawTileOutline(ShapeRenderer shapeRenderer, Tile tile) {
        Color color = getColor(tile);

        if (color != null) {
            Position position = tile.getPosition();

            shapeRenderer.rect(
                    position.getX(),
                    position.getY(),
                    TextureRepository.TILE_WIDTH,
                    TextureRepository.TILE_HEIGHT,
                    color, color, color, color
            );
        }
    }

    private void drawTileFill(Batch batch, Tile tile) {
        Texture texture = getTexture(tile);

        if (texture != null) {
            Position position = tile.getPosition();

            batch.draw(
                    getTexture(tile),
                    position.getX(),
                    position.getY(),
                    TextureRepository.TILE_WIDTH,
                    TextureRepository.TILE_HEIGHT
            );
        }
    }

    private Texture getTexture(Tile tile) {
        MemoryTile memoryTile = (MemoryTile) tile;

        if (memoryTile.isVisited()) {
            return visitedTexture;
        }

        if (memoryTile.isExplored()) {
            return exploredTexture;
        }

        return null;
    }

    private Color getColor(Tile tile) {
        MemoryTile memoryTile = (MemoryTile) tile;

        if (memoryTile.isVisited()) {
            return visitedColor;
        }

        if (memoryTile.isExplored()) {
            return exploredColor;
        }

        return null;
    }

    private Texture getPixmapTexture(Color color) {
        return new Texture(getPixmapRectangle(color));
    }

    private static Pixmap getPixmapRectangle(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }

}
