package nl.maastrichtuniversity.dke.scenario.factory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.scenario.environment.*;
import nl.maastrichtuniversity.dke.scenario.util.Position;
import nl.maastrichtuniversity.dke.util.DebugSettings;


@NoArgsConstructor
@Setter
@Getter
@Slf4j
public class EnvironmentFactory {

    private int width;
    private int height;


    private Tile[][] tileMap;

    public void addArea(int x1, int y1, int x2, int y2, TileType type) {
        if (DebugSettings.FACTORY) {
            log.info("adding area of type " + type + ": " + x1 + " " + y1 + " " + x2 + " " + y2);

        }
        if (this.tileMap == null && width > 0 && height > 0) {
            this.tileMap = new Tile[width][height];
        }

        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                tileMap[x][y] = new Tile(new Position(x, y), type);
            }
        }
    }

    public Environment build() {
        fillInEmptyTiles();

        return new Environment(this.width, this.height, this.tileMap);
    }

    private void fillInEmptyTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tileMap[x][y] == null) {
                    tileMap[x][y] = new Tile(new Position(x, y), TileType.EMPTY);
                }
            }
        }

    }

    public void addTeleportArea(int x1, int y1, int x2, int y2, int targetX, int targetY, int rotation) {
        if (DebugSettings.FACTORY) {
            log.info("adding teleport area: " + x1 + " " + y1 + " " + x2 + " " + y2 + " with target coordinates " + targetX + " " + targetY + " and rotation " + rotation);
        }

        if (this.tileMap == null && width > 0 && height > 0) {
            this.tileMap = new Tile[width][height];
        }

        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                tileMap[x][y] = new TeleportTile(new Position(x, y), targetX, targetY, rotation);
            }
        }
    }

    public void addTile(int x, int y, TileType type) {
        if (x < 0 || x > tileMap.length || y < 0 || y > tileMap[0].length) {
            return;
        }
        if (type == TileType.DOOR) {
            tileMap[x][y] = new DoorTile(new Position(x, y));
        } else if (type == TileType.WINDOW) {
            tileMap[x][y] = new WindowTile(new Position(x, y));
        } else {
            tileMap[x][y] = new Tile(new Position(x, y), type);
        }
    }
}
