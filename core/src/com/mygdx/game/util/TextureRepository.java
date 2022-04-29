package com.mygdx.game.util;

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
        textures.put("unexplored", new Texture("empty/blackmark.png"));
        textures.put("teleport", new Texture("texture/teleport.png"));
        textures.put("target", new Texture("texture/target.png"));

        // structures
        textures.put("teleportDestination", new Texture("structure/teleportDestination.png"));
        textures.put("house1", new Texture("structure/house1.png"));
        textures.put("house2", new Texture("structure/house2.png"));
        textures.put("house3", new Texture("structure/house3.png"));
        textures.put("house4", new Texture("structure/house4.png"));
        textures.put("house5", new Texture("structure/house5.png"));
        textures.put("house6", new Texture("structure/house6.png"));


        textures.put("shadedTile1", new Texture("shaded/tile1.png"));
        textures.put("shadedTile2", new Texture("shaded/tile2.png"));
        textures.put("shadedTile3", new Texture("shaded/tile3.png"));
        textures.put("shadedTile4", new Texture("shaded/tile4.png"));
        textures.put("emptyTile3", new Texture("empty/tile3.png"));
        textures.put("emptyTile4", new Texture("empty/tile4.png"));
        textures.put("emptyTile5", new Texture("empty/tile5.png"));
        textures.put("emptyTile6", new Texture("empty/tile6.png"));

        // markers
        textures.put("blueMarker", new Texture("markers/blueMarker.png"));
        textures.put("redMarker", new Texture("markers/redMarker.png"));
        textures.put("greenMarker", new Texture("markers/greenMarker.png"));

        //walls
        textures.put("wall", new Texture("wall/wall.png"));
        textures.put("wall1", new Texture("wall/wall1.png"));
        textures.put("wall2", new Texture("wall/wall2.png"));
        textures.put("wall3", new Texture("wall/wall3.png"));
        textures.put("wall4", new Texture("wall/wall4.png"));

        //guards
        textures.put("guard1", new Texture("guard/guard1.png"));
        textures.put("guard2", new Texture("guard/guard2.png"));
        textures.put("guard3", new Texture("guard/guard3.png"));

        textures.put("guardback1", new Texture("guard/guardback1.png"));
        textures.put("guardback2", new Texture("guard/guardback2.png"));
        textures.put("guardback3", new Texture("guard/guardback3.png"));

        textures.put("guardleft1", new Texture("guard/guardleft.png"));
        textures.put("guardleft2", new Texture("guard/guardleft2.png"));
        textures.put("guardleft3", new Texture("guard/guardleft3.png"));

        textures.put("guardright1", new Texture("guard/guardright1.png"));
        textures.put("guardright2", new Texture("guard/guardright2.png"));
        textures.put("guardright3", new Texture("guard/guardright3.png"));
        //intruders
        textures.put("intruder1", new Texture("intruders/intruder1.png"));
        textures.put("intruder2", new Texture("intruders/intruder2.png"));
        textures.put("intruder3", new Texture("intruders/intruder3.png"));

        textures.put("intruderback1", new Texture("intruders/intruderback1.png"));
        textures.put("intruderback2", new Texture("intruders/intruderback2.png"));
        textures.put("intruderback3", new Texture("intruders/intruderback3.png"));

        textures.put("intruderleft1", new Texture("intruders/intruderleft.png"));
        textures.put("intruderleft2", new Texture("intruders/intruderleft2.png"));
        textures.put("intruderleft3", new Texture("intruders/intruderleft3.png"));

        textures.put("intruderright1", new Texture("intruders/intruderright1.png"));
        textures.put("intruderright2", new Texture("intruders/intruderright2.png"));
        textures.put("intruderright3", new Texture("intruders/intruderright3.png"));

        //others
        textures.put("door", new Texture("texture/door.png"));
        textures.put("openeddoor", new Texture("texture/openeddoor.png"));
        textures.put("window", new Texture("texture/window.png"));
        textures.put("brokenWindow", new Texture("texture/window2.png"));


    }

}
