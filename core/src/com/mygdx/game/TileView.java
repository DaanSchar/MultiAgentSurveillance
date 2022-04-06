package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class TileView {

    private final Tile tile;
    private final Texture tileTexture;
    private final Texture stateTexture;
    private final double height;

    private final TextureRepository textureRepository;

    public TileView(Tile tile, double height) {
        this.tile = tile;
        this.height = height;
        this.textureRepository = TextureRepository.getInstance();
        this.tileTexture = getTileTexture();
        this.stateTexture = determineTextureByTileState();
    }

    public void draw(Batch batch) {
        drawTile(tileTexture, batch);
        drawTile(stateTexture, batch);
    }

    private void drawTile(Texture texture, Batch batch) {
        if (texture == null) {
            return;
        }
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
            case DESTINATION_TELEPORT -> {
                return textureRepository.get("teleportDestination");
            }
            case WALL -> {
                return textureRepository.get("shadedTile4");
            }
            case SPAWN_GUARDS -> {
                return textureRepository.get("sand1");
            }
            default -> {
                return textureRepository.get("emptyTile4");
            }
        }
    }

    private Texture getTileTexture() {
        final float third = 1 / 3f;


        if (height > 2 * third) {
            return textureRepository.get("stone1");
        } else if (height > 1 * third) {
            return textureRepository.get("grass1");
        } else {
            return textureRepository.get("dirt1");
        }
    }

    private Texture getEmptyTexture() {
        return null;
    }

}
