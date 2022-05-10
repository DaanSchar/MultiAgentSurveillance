package nl.maastrichtuniversity.dke.logic.scenario.util;

import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TeleportTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;

import java.util.Random;

public class RandomMapGenerator {
    private final int width = 120;
    private final int height = 80;
    private Tile[][] tileMap;
    private int maxEighteen = 18;
    private int maxEight = 8;
    private int maxThree = 3;
    private int maxTen = 10;
    private int maxTwen = 20;
    private int maxFifty = 50;
    private int maxSix = 6;
    private int maxFourTeen = 14;
    private int maxOne = 1;


    public Environment build() {
        this.tileMap = new Tile[width][height];
        createBorder(); //WALL BORDER
        createrArea(maxEighteen, maxEight, TileType.SPAWN_INTRUDERS); //SPAWN
        createrArea(maxEighteen, maxEight, TileType.SPAWN_GUARDS); //SPAWN
        createrArea(maxThree, maxThree, TileType.TARGET); //TARGET
        createTeleport(maxThree, maxThree, 0); //TELEPORT
        createTeleport(maxThree, maxThree, 0); //TELEPORT
        createrArea(maxTen, maxTwen, TileType.SHADED); //SHADED
        createrArea(maxOne, maxOne, TileType.SENTRY); //SENTRY
        createrArea(maxOne, maxOne, TileType.SENTRY); //SENTRY
        createrArea(maxOne, maxOne, TileType.SENTRY); //SENTRY
        createBigHouse(maxEighteen, maxEighteen); //BIG HOUSE
        createBigHouse(maxFourTeen, maxFourTeen); //BIG HOUSE
        createSmallHouse(maxSix, maxSix); //SMALL HOUSE
        createSmallHouse(maxEight, maxEight); //SMALL HOUSE
        createrArea(maxOne, maxTwen, TileType.WALL); //r WALL
        createrArea(maxTwen, maxOne, TileType.WALL); //r WALL
        createrArea(maxOne, maxTwen, TileType.WALL); //r WALL
        createrArea(maxTen, maxOne, TileType.WALL); //r WALL
        createrArea(maxFifty, maxOne, TileType.WALL); //r WALL
        createrArea(maxOne, maxFifty, TileType.WALL); //r WALL
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

    private void createrArea(int maxWidth, int maxHeight, TileType type) {
        Random rand = new Random();
        int rX1 = rand.nextInt(width - maxWidth);
        int rY1 = rand.nextInt(height - maxHeight);
        if (freeArea(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight))) {
            for (int x = rX1; x < (rX1 + maxWidth); x++) {
                for (int y = rY1; y < (rY1 + maxHeight); y++) {
                    tileMap[x][y] = new Tile(new Position(x, y), type);
                }
            }
        } else {
            createrArea(maxWidth, maxHeight, type);
        }

    }

    private void createArea(int x1, int y1, int x2, int y2, TileType type) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                tileMap[x][y] = new Tile(new Position(x, y), type);
            }
        }
    }

    private void createBorder() {
        createArea(0, 0, 1, height, TileType.WALL);
        createArea(0, height - 1, width, height, TileType.WALL);
        createArea(width - 1, 0, width, height, TileType.WALL);
        createArea(0, 0, width, 1, TileType.WALL);
    }

    private void createTeleport(int maxWidth, int maxHeight, int rotation) {
        Random rand = new Random();
        int rX1 = rand.nextInt(width - maxWidth);
        int rY1 = rand.nextInt(height - maxHeight);
        if (freeArea(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight))) {
            int[] target = createTeleportDis();
            for (int x = rX1; x < (rX1 + maxWidth); x++) {
                for (int y = rY1; y < (rY1 + maxHeight); y++) {
                    tileMap[x][y] = new TeleportTile(new Position(x, y), target[0], target[1], rotation);
                }
            }
        } else {
            createTeleport(maxWidth, maxHeight, rotation);
        }

    }

    public int[] createTeleportDis() {
        int[] target = new int[2];
        Random rand = new Random();
        int rX1 = rand.nextInt(width - 1);
        int rY1 = rand.nextInt(height - 1);
        target[0] = rX1;
        target[1] = rY1;
        if (freeArea(rX1, rY1, (1 + rX1), (rY1 + 1))) {
            for (int x = rX1; x < (rX1 + 1); x++) {
                for (int y = rY1; y < (rY1 + 1); y++) {
                    tileMap[x][y] = new Tile(new Position(x, y), TileType.DESTINATION_TELEPORT);
                }
            }
        } else {
            createTeleportDis();
        }

        return target;
    }

    private void createBigHouse(int maxWidth, int maxHeight) {
        Random rand = new Random();
        int rX1 = rand.nextInt(width - maxWidth);
        int rY1 = rand.nextInt(height - maxHeight);
        if (freeArea(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight))) {
            createArea(rX1, rY1, rX1 + 1, rY1 + maxHeight, TileType.WALL);
            createArea(rX1, (rY1 + maxHeight) - 1, (maxWidth + rX1), (rY1 + maxHeight), TileType.WALL);
            createArea((rX1 + maxHeight) - 1, rY1, (maxWidth + rX1), (rY1 + maxHeight), TileType.WALL);
            createArea(rX1, rY1, (rX1 + maxHeight), rY1 + 1, TileType.WALL);
            createDoor(rX1 + (maxWidth / 2), rY1);
            createWindow(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight));

        } else {
            createBigHouse(maxWidth, maxHeight);
        }

    }

    private void createSmallHouse(int maxWidth, int maxHeight) {
        Random rand = new Random();
        int rX1 = rand.nextInt(width - maxWidth);
        int rY1 = rand.nextInt(height - maxHeight);
        if (freeArea(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight))) {
            createArea(rX1, rY1, rX1 + 1, rY1 + maxHeight, TileType.WALL);
            createArea(rX1, (rY1 + maxHeight) - 1, (maxWidth + rX1), (rY1 + maxHeight), TileType.WALL);
            createArea((rX1 + maxHeight) - 1, rY1, (maxWidth + rX1), (rY1 + maxHeight), TileType.WALL);
            createArea(rX1, rY1, (rX1 + maxHeight), rY1 + 1, TileType.WALL);
            createDoor(rX1 + (maxWidth / 2), rY1);
        } else {
            createSmallHouse(maxWidth, maxHeight);
        }
    }

    private void createrWalls() {

    }

    private void createDoor(int x, int y) {
        tileMap[x][y] = new Tile(new Position(x, y), TileType.DOOR);
        tileMap[x - 1][y] = new Tile(new Position(x - 1, y), TileType.DOOR);
    }

    private void createWindow(int x1, int y1, int x2, int y2) {
        int window1y = y1 + 5;
        int window2y = y1 + 10;
        x2 = x2 - 1;

        tileMap[x1][window1y] = new Tile(new Position(x1, window1y), TileType.WINDOW);
        tileMap[x1][window2y] = new Tile(new Position(x1, window2y), TileType.WINDOW);
        tileMap[x2][window1y] = new Tile(new Position(x2, window1y), TileType.WINDOW);
        tileMap[x2][window2y] = new Tile(new Position(x2, window2y), TileType.WINDOW);

    }

    private boolean freeArea(int x1, int y1, int x2, int y2) {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                if ((tileMap[x][y] != null)) {
                    return false;
                }
            }
        }
        return true;
    }
}
