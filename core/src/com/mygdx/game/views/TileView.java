package com.mygdx.game.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.logic.scenario.environment.MemoryTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class TileView extends Actor {

    private final MemoryTile tile;
    private final Color color;

    public TileView(MemoryTile tile) {
        this.tile = tile;
        this.color = determineColor();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Position position = tile.getPosition();
        drawRectangle(batch, position, color);
    }

    private Color determineColor() {
        if (tile.isVisited()) {
            return new Color(0.2f, 0.2f, 0.2f, 0.7f);
        }

        if (tile.isExplored()) {
            return new Color(0.5f, 0.5f, 0.5f, 0.7f);
        }

        return new Color(0f, 0f, 0f, 0f);
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

    private static Pixmap getPixmapRectangle(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }
}
