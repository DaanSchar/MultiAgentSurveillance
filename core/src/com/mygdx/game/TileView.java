package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class TileView {

    private final Tile tile;
    private final Texture texture;

    private final TextureRepository textureRepository;

    public TileView(Tile tile) {
        this.tile = tile;
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
                return textureRepository.get("emptyTile1");
            }
            case WALL -> {
                return textureRepository.get("shadedTile4");
            }
            default -> {
                return textureRepository.get("emptyTile4");
            }
        }
    }

}
