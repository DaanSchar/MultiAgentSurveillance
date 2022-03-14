package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Environment {

    private final int width;
    private final int height;

    private final Tile[][] tileMap;

    public Environment(int width, int height, Tile[][] tileMap) {
        this.width = width;
        this.height = height;
        this.tileMap = tileMap;
    }

    public Environment(int width, int height) {
        this.width = width;
        this.height = height;
        this.tileMap = new Tile[width][height];
    }

    public List<Tile> get(TileType type) {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tileMap[i][j].getType() == type) {
                    tiles.add(tileMap[i][j]);
                }
            }
        }
        return tiles;
    }

}
