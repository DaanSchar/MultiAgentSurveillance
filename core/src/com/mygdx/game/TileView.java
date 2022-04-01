package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class TileView {

    private final Tile tile;
    private Texture texture;

    private static final TextureRepository textureRepository = TextureRepository.getInstance();

    public TileView(Tile tile) {
        this.tile = tile;
    }

    public void draw(Batch batch) {
        determineTextureByTileState();
        drawTile(batch);
    }

    private void drawTile(Batch batch) {
        Position position = tile.getPosition();
        batch.draw(texture, position.getX() * textureRepository.getTextureWidth(), position.getY() * textureRepository.getTextureHeight());
    }

    private void determineTextureByTileState() {
        switch (tile.getType()) {
            case EMPTY -> texture = textureRepository.get("emptyTile1");
            case WALL -> texture = textureRepository.get("shadedTile4");
            default -> texture = textureRepository.get("emptyTile4");
        }
    }





}
