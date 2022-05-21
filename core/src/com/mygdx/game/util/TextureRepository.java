package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public final class TextureRepository {

    private static TextureRepository instance;

    public static final int TILE_WIDTH = 1;
    public static final int TILE_HEIGHT = 1;

    public static TextureRepository getInstance() {
        if (instance == null) {
            instance = new TextureRepository();
        }

        return instance;
    }

    private final HashMap<String, Texture> textures;
    private final HashMap<Color, Texture> coloredTiles;

    private TextureRepository() {
        textures = new HashMap<>();
        coloredTiles = new HashMap<>();
        addTextures();
    }

    /**
     * Returns a texture with the given name.
     *
     * @param key The name of the texture.
     * @return The texture with the given name.
     */
    public Texture get(String key) {
        return textures.get(key);
    }

    /**
     * Returns a simple square texture with the given color.
     *
     * @param color The color of the texture.
     * @return A texture with size TileSize x TileSize and the given color.
     */
    public Texture getTile(Color color) {
        Texture texture = coloredTiles.get(color);

        if (texture == null) {
            texture = getPixmapTexture(color);
            coloredTiles.put(color, texture);
        }

        return texture;
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

    private void addTextures() {
        // underlying tiles
        textures.put("grass1", new Texture("textures/empty/grass1.png"));
        textures.put("grass2", new Texture("textures/empty/grass2.png"));
        textures.put("sand1", new Texture("textures/empty/sand1.png"));
        textures.put("sand2", new Texture("textures/empty/sand2.png"));
        textures.put("snow1", new Texture("textures/empty/snow1.png"));
        textures.put("snow2", new Texture("textures/empty/snow2.png"));
        textures.put("stone1", new Texture("textures/empty/stone2.png"));
        textures.put("stone2", new Texture("textures/empty/stone2.png"));
        textures.put("dirt1", new Texture("textures/empty/dirt2.png"));
        textures.put("dirt2", new Texture("textures/empty/dirt2.png"));
        textures.put("unexplored", new Texture("textures/empty/blackmark.png"));
        textures.put("teleport", new Texture("textures/texture/teleport.png"));
        textures.put("target", new Texture("textures/texture/target.png"));
        textures.put("sentry", new Texture("textures/texture/sentrytower.png"));

        // structures
        textures.put("teleportDestination", new Texture("textures/structure/teleportDestination.png"));
        textures.put("house1", new Texture("textures/structure/house1.png"));
        textures.put("house2", new Texture("textures/structure/house2.png"));
        textures.put("house3", new Texture("textures/structure/house3.png"));
        textures.put("house4", new Texture("textures/structure/house4.png"));
        textures.put("house5", new Texture("textures/structure/house5.png"));
        textures.put("house6", new Texture("textures/structure/house6.png"));


        textures.put("shadedTile1", new Texture("textures/shaded/tile1.png"));
        textures.put("shadedTile2", new Texture("textures/shaded/tile2.png"));
        textures.put("shadedTile3", new Texture("textures/shaded/tile3.png"));
        textures.put("shadedTile4", new Texture("textures/shaded/tile4.png"));
        textures.put("emptyTile3", new Texture("textures/empty/tile3.png"));
        textures.put("emptyTile4", new Texture("textures/empty/tile4.png"));
        textures.put("emptyTile5", new Texture("textures/empty/tile5.png"));
        textures.put("emptyTile6", new Texture("textures/empty/tile6.png"));

        // markers
        textures.put("blueMarker", new Texture("textures/markers/blueMarker.png"));
        textures.put("redMarker", new Texture("textures/markers/redMarker.png"));
        textures.put("greenMarker", new Texture("textures/markers/greenMarker.png"));

        //walls
        textures.put("wall", new Texture("textures/wall/wall.png"));
        textures.put("wall1", new Texture("textures/wall/wall1.png"));
        textures.put("wall2", new Texture("textures/wall/wall2.png"));
        textures.put("wall3", new Texture("textures/wall/wall3.png"));
        textures.put("wall4", new Texture("textures/wall/wall4.png"));

        //guards
        textures.put("guardback1", new Texture("textures/guard/guard1.png"));
        textures.put("guardback2", new Texture("textures/guard/guard2.png"));
        textures.put("guardback3", new Texture("textures/guard/guard3.png"));

        textures.put("guard1", new Texture("textures/guard/guardback1.png"));
        textures.put("guard2", new Texture("textures/guard/guardback2.png"));
        textures.put("guard3", new Texture("textures/guard/guardback3.png"));

        textures.put("guardleft1", new Texture("textures/guard/guardleft.png"));
        textures.put("guardleft2", new Texture("textures/guard/guardleft2.png"));
        textures.put("guardleft3", new Texture("textures/guard/guardleft3.png"));

        textures.put("guardright1", new Texture("textures/guard/guardright1.png"));
        textures.put("guardright2", new Texture("textures/guard/guardright2.png"));
        textures.put("guardright3", new Texture("textures/guard/guardright3.png"));

        //intruders
        textures.put("intruderback1", new Texture("textures/intruders/intruder1.png"));
        textures.put("intruderback2", new Texture("textures/intruders/intruder2.png"));
        textures.put("intruderback3", new Texture("textures/intruders/intruder3.png"));

        textures.put("intruder1", new Texture("textures/intruders/intruderback1.png"));
        textures.put("intruder2", new Texture("textures/intruders/intruderback2.png"));
        textures.put("intruder3", new Texture("textures/intruders/intruderback3.png"));

        textures.put("intruderleft1", new Texture("textures/intruders/intruderleft.png"));
        textures.put("intruderleft2", new Texture("textures/intruders/intruderleft2.png"));
        textures.put("intruderleft3", new Texture("textures/intruders/intruderleft3.png"));

        textures.put("intruderright1", new Texture("textures/intruders/intruderright1.png"));
        textures.put("intruderright2", new Texture("textures/intruders/intruderright2.png"));
        textures.put("intruderright3", new Texture("textures/intruders/intruderright3.png"));

        //others
        textures.put("door", new Texture("textures/texture/door.png"));
        textures.put("openeddoor", new Texture("textures/texture/openddoor.png"));
        textures.put("window", new Texture("textures/texture/window.png"));
        textures.put("brokenwindow", new Texture("textures/texture/brokenwindow.png"));
    }

}
