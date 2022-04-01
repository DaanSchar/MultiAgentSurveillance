package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public final class TextureRepository {

    private static TextureRepository instance;

    public static TextureRepository getInstance() {
        if (instance == null) {
            instance = new TextureRepository();
        }

        return instance;
    }

    private final HashMap<String, Texture> textures;

    public Texture get(String key) {
        return textures.get(key);
    }

    public int getTextureWidth() {
        return textures.get("emptyTile1").getWidth();
    }

    public int getTextureHeight() {
        return textures.get("emptyTile1").getHeight();
    }

    private TextureRepository() {
        textures = new HashMap<>();
        addTextures();
    }

    private void addTextures() {
        textures.put("emptyTile1", new Texture("empty/tile1.png"));
        textures.put("emptyTile2", new Texture("empty/tile2.png"));
        textures.put("emptyTile3", new Texture("empty/tile3.png"));
        textures.put("emptyTile4", new Texture("empty/tile4.png"));
        textures.put("emptyTile5", new Texture("empty/tile5.png"));
        textures.put("emptyTile6", new Texture("empty/tile6.png"));

        textures.put("sand1", new Texture("empty/sand1.png"));
        textures.put("sand2", new Texture("empty/sand2.png"));

        textures.put("snow1", new Texture("empty/snow1.png"));
        textures.put("snow2", new Texture("empty/snow2.png"));

        textures.put("shadedTile1", new Texture("shaded/tile1.png"));
        textures.put("shadedTile2", new Texture("shaded/tile2.png"));
        textures.put("shadedTile3", new Texture("shaded/tile3.png"));
        textures.put("shadedTile4", new Texture("shaded/tile3.png"));

        textures.put("guard1", new Texture("guard/guard1.png"));
    }

}
