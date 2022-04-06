package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;

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

    private TextureRepository() {
        textures = new HashMap<>();
        addTextures();
    }

    public Texture get(String key) {
        return textures.get(key);
    }

    private void addTextures() {
        // underlying tiles
        textures.put("grass1", new Texture("empty/grass1.png"));
        textures.put("grass2", new Texture("empty/grass2.png"));
        textures.put("sand1", new Texture("empty/sand1.png"));
        textures.put("sand2", new Texture("empty/sand2.png"));
        textures.put("snow1", new Texture("empty/snow1.png"));
        textures.put("snow2", new Texture("empty/snow2.png"));
        textures.put("stone1", new Texture("empty/stone2.png"));
        textures.put("stone2", new Texture("empty/stone2.png"));
        textures.put("dirt1", new Texture("empty/dirt2.png"));
        textures.put("dirt2", new Texture("empty/dirt2.png"));

        // structures
        textures.put("teleportDestination", new Texture("structure/teleportDestination.png"));
        textures.put("house1", new Texture("structure/house1.png"));
        textures.put("house2", new Texture("structure/house2.png"));
        textures.put("house3", new Texture("structure/house3.png"));
        textures.put("house4", new Texture("structure/house4.png"));
        textures.put("house5", new Texture("structure/house5.png"));
        textures.put("house6", new Texture("structure/house6.png"));


        // agents
        textures.put("guard1", new Texture("guard/guard1.png"));

        textures.put("shadedTile1", new Texture("shaded/tile1.png"));
        textures.put("shadedTile2", new Texture("shaded/tile2.png"));
        textures.put("shadedTile3", new Texture("shaded/tile3.png"));
        textures.put("shadedTile4", new Texture("shaded/tile3.png"));
        textures.put("emptyTile3", new Texture("empty/tile3.png"));
        textures.put("emptyTile4", new Texture("empty/tile4.png"));
        textures.put("emptyTile5", new Texture("empty/tile5.png"));
        textures.put("emptyTile6", new Texture("empty/tile6.png"));

    }

}
