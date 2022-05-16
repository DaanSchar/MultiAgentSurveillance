package com.mygdx.game.views.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.scenario.environment.*;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public class TileView extends Actor {

    private final Tile tile;
    private final Texture tileTexture;
    private final Texture stateTexture;
    private final float height;

    private final Color outlineColor;

    private final TextureRepository textureRepository;

    public TileView(Tile tile, double height) {
        this.tile = tile;
        this.height = (float) height;
        this.textureRepository = TextureRepository.getInstance();
        this.tileTexture = getTileTexture();
        this.outlineColor = getOutlineColor();
        this.stateTexture = getStateTexture();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawTile(tileTexture, batch);
        drawTile(stateTexture, batch);
    }

    public void draw(ShapeRenderer renderer, float parentAlpha) {
        renderer.rect(
                tile.getPosition().getX(),
                tile.getPosition().getY(),
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT,
                outlineColor, outlineColor, outlineColor, outlineColor
        );
    }

    private void drawTile(Texture texture, Batch batch) {
        if (texture == null) {
            return;
        }

        Position position = tile.getPosition();
        batch.draw(
                texture,
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
        );
    }

    private Texture getStateTexture() {
        return switch (tile.getType()) {
            case EMPTY, UNKNOWN -> null;
            case DESTINATION_TELEPORT -> textureRepository.get("teleportDestination");
            case WALL -> textureRepository.getTile(new Color(height/4f, height/4f, height/4f, 1f));
            case SPAWN_GUARDS -> textureRepository.get("sand1");
            case SPAWN_INTRUDERS -> textureRepository.get("sand2");
            case TARGET -> textureRepository.get("target");
            case SHADED -> textureRepository.get("shadedTile4");
            case TELEPORT -> textureRepository.get("teleport");
            case SENTRY -> textureRepository.get("sentry");
            case DOOR -> getDoorTexture();
            case WINDOW -> getWindowTexture();
            default -> textureRepository.get("emptyTile4");
        };
    }

    private Color getOutlineColor() {
        return switch (tile.getType()) {
            case WALL -> new Color(0.2f, 0.2f, 0.2f, 1f);
            case SPAWN_GUARDS, SPAWN_INTRUDERS -> new Color(199f/255f, 187f/255f, 153f/255f, 1f);
            case SHADED -> new Color(52f/255f, 107f/255f, 62f/255f, 1f);
            case UNKNOWN -> Color.BLACK;
            default -> new Color(0.8f, 0.8f, 0.8f, 1f);
        };
    }

    private Texture getTileTexture() {
        if (tile.getType() == TileType.UNKNOWN) {
            return textureRepository.getTile(Color.BLACK);
        }

        return getEmptyTileTexture();
    }

    private Texture getEmptyTileTexture() {
        float value = 1 - height/5f;
        Color emptyTileColor = new Color(value, value, value, 1f);
        return textureRepository.getTile(emptyTileColor);
    }

    private Texture getDoorTexture() {
        if (tile instanceof MemoryTile) {
            if (tile.isOpened()) {
                return textureRepository.get("openeddoor");
            }
            return textureRepository.get("door");
        }

        DoorTile doorTile = (DoorTile) tile;

        if (doorTile.isOpened()) {
            return textureRepository.get("openeddoor");
        }

        return textureRepository.get("door");
    }

    private Texture getWindowTexture() {
        if (tile instanceof MemoryTile) {
            if (((MemoryTile) tile).isBroken()) {
                return textureRepository.get("brokenwindow");
            }
            return textureRepository.get("window");
        }

        WindowTile windowTile = (WindowTile) tile;

        if (windowTile.isBroken()) {
            return textureRepository.get("brokenwindow");
        }
        return textureRepository.get("window");
    }
}
