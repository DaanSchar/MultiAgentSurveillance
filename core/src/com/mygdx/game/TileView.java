package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class TileView {

    private final Tile tile;
    private final Texture texture;
    private final double height;

    private final TextureRepository textureRepository;

    public TileView(Tile tile, double height) {
        this.tile = tile;
        this.height = height;
        this.textureRepository = TextureRepository.getInstance();
        this.texture = determineTextureByTileState();
    }

    public void draw(Batch batch) {
        drawTile(batch);
    }

    private void drawTile(Batch batch) {
        Position position = tile.getPosition();
        batch.draw(
                texture,
                position.getX() * textureRepository.getTextureWidth(),
                position.getY() * textureRepository.getTextureHeight()
        );
    }

    private Texture determineTextureByTileState() {
        switch (tile.getType()) {
            case EMPTY -> {
                return getEmptyTexture();
            }
            case WALL -> {
                return textureRepository.get("shadedTile4");
            }
            default -> {
                return textureRepository.get("emptyTile4");
            }
        }
    }

    private Texture getEmptyTexture() {
        if (height > 2f/3f) {
            return textureRepository.get("snow1");
        } else if (height > 1f/3f) {
            return textureRepository.get("sand1");
        } else {
            return textureRepository.get("emptyTile1");
        }
    }

}
