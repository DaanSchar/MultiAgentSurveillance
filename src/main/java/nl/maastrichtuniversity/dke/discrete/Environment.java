package nl.maastrichtuniversity.dke.discrete;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Environment {

    private final int width;
    private final int height;

    private final Tile[][] tileMap;

    private @Setter List<Guard> guards;
    private @Setter List<Intruder> intruders;

    public Environment(int width, int height, Tile[][] tileMap) {
        this.width = width;
        this.height = height;
        this.tileMap = tileMap;
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
