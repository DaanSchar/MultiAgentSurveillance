package nl.maastrichtuniversity.dke.discrete;

import lombok.Getter;

public class Environment {

    private final @Getter Tile[][] tileMap;

    public Environment(int width, int height) {
        tileMap = new Tile[width][height];
    }

}
