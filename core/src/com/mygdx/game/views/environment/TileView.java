package com.mygdx.game.views.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.util.TextureRepository;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class TileView extends Actor {

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

    public void draw(Batch batch, float parentAlpha) {
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
                position.getX() * TextureRepository.TILE_WIDTH,
                position.getY() * TextureRepository.TILE_HEIGHT,
                TextureRepository.TILE_WIDTH,
                TextureRepository.TILE_HEIGHT
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
                return textureRepository.get("wall");
            }
            case SPAWN_GUARDS -> {
                return textureRepository.get("sand1");
            }
            case SPAWN_INTRUDERS -> {
                return textureRepository.get("sand2");
            }
            case TARGET -> {
                return textureRepository.get("target");
            }
            case SHADED -> {
                return textureRepository.get("shadedTile4");
            }
            case TELEPORT -> {
                return textureRepository.get("teleport");
            }
            case SENTRY -> {
                return textureRepository.get("sentry");
            }
            case UNKNOWN -> {
                return null;
            }
            case DOOR -> {
                // DoorTile doorTile = (DoorTile) tile;
                // if (doorTile.isOpened()) {
                //     return textureRepository.get("openeddoor");
                // }
                return textureRepository.get("door");
            }
            case WINDOW -> {
                //WindowTile windowTile = (WindowTile) tile;
                //if (windowTile.isBroken()) {
                //     return textureRepository.get("brokenwindow");
                // }
                return textureRepository.get("window");
            }

            default -> {
                return textureRepository.get("emptyTile4");
            }
        }
    }

    private Texture getTileTexture() {
        final float third = 1 / 3f;

        if (tile.getType() == TileType.UNKNOWN) {
            return null;
        }

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
