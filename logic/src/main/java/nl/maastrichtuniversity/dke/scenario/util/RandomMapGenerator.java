package nl.maastrichtuniversity.dke.scenario.util;

import nl.maastrichtuniversity.dke.scenario.environment.*;

import java.util.Random;

public class RandomMapGenerator {
    private final int width = 120 / 2;
    private final int height = 80 / 2;
    private Tile[][] tileMap = new Tile[width][height];
    private static final int EIGHTEEN = 18 / 2;
    private static final int EIGHT = 8 / 2;
    private static final int THREE = 3;
    private static final int TEN = 10 / 2;
    private static final int TWEN = 20 / 2;
    private static final int FIFTY = 50 / 2;
    private static final int FOURTEEN = 14 / 2;
    private static final int ONE = 1;
    private static final int FIVE = 5;
    private MapSaver mapSaver = new MapSaver();


    public Environment build() {
        mapSaver.scenarioSaver();
        createBorder(); //WALL BORDER
        createrArea(EIGHTEEN, EIGHT, TileType.SPAWN_INTRUDERS); //SPAWN
        createrArea(EIGHTEEN, EIGHT, TileType.SPAWN_GUARDS); //SPAWN
        createTeleport(ONE, ONE, 0); //TELEPORT
        createTeleport(ONE, ONE, 0); //TELEPORT
        createrArea(TEN, TWEN, TileType.SHADED); //SHADED
        createrArea(ONE, ONE, TileType.SENTRY); //SENTRY
        createrArea(ONE, ONE, TileType.SENTRY); //SENTRY
        createrArea(ONE, ONE, TileType.SENTRY); //SENTRY
        createBigHouse(EIGHTEEN, EIGHTEEN); //BIG HOUSE
        createBigHouse(FOURTEEN, FOURTEEN); //BIG HOUSE
        createSmallHouse(EIGHT, EIGHT); //SMALL HOUSE
        createSmallHouse(EIGHT, EIGHT); //SMALL HOUSE
        createrArea(ONE, TWEN, TileType.WALL); //r WALL
        createrArea(TWEN, ONE, TileType.WALL); //r WALL
        createrArea(ONE, TWEN, TileType.WALL); //r WALL
        createrArea(TEN, ONE, TileType.WALL); //r WALL
        createrArea(FIFTY, ONE, TileType.WALL); //r WALL
        createrArea(ONE, FIFTY, TileType.WALL); //r WALL
        createrArea(ONE, ONE, TileType.TARGET); //TARGET
        fillInEmptyTiles();
        mapSaver.saveMap();
        return new Environment(this.width, this.height, this.tileMap);
    }

    public void buildDraw() {
        fillInEmptyTiles();
        mapSaver.saveMap();
    }
    public void startBuild() {
        mapSaver.scenarioSaver();
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

    public void createrArea(int maxWidth, int maxHeight, TileType type) {
        Random rand = new Random();
        int rX1 = rand.nextInt(width - maxWidth);
        int rY1 = rand.nextInt(height - maxHeight);
        if (freeArea(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight))) {
            for (int x = rX1; x < (rX1 + maxWidth); x++) {
                for (int y = rY1; y < (rY1 + maxHeight); y++) {
                    tileMap[x][y] = new Tile(new Position(x, y), type);
                }
            }
            mapSaver.areaSaver(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight), type);
        } else {
            createrArea(maxWidth, maxHeight, type);
        }

    }

    public void createArea(int x1, int y1, int x2, int y2, TileType type) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                tileMap[x][y] = new Tile(new Position(x, y), type);
            }
        }
        mapSaver.areaSaver(x1, y1, x2, y2, type);

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
        int[] target = createTeleportDis();
        if (freeArea(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight))) {
            for (int x = rX1; x < (rX1 + maxWidth); x++) {
                for (int y = rY1; y < (rY1 + maxHeight); y++) {
                    tileMap[x][y] = new TeleportTile(new Position(x, y), target[0], target[1], rotation);
                }
            }
            mapSaver.teleportSaver(rX1, rY1, (maxWidth + rX1), (rY1 + maxHeight), target[0], target[1], rotation);

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


    private void createDoor(int x, int y) {
        tileMap[x][y] = new DoorTile(new Position(x, y));
        tileMap[x - 1][y] = new DoorTile(new Position(x - 1, y));
        mapSaver.tileSaver(x, y, TileType.DOOR);
        mapSaver.tileSaver(x - 1, y, TileType.DOOR);

    }

    private void createWindow(int x1, int y1, int x2, int y2) {
        int window1y = y1 + FIVE;
        int window2y = y1 + TEN;
        x2 = x2 - 1;

        tileMap[x1][window1y] = new WindowTile(new Position(x1, window1y));
        tileMap[x1][window2y] = new WindowTile(new Position(x1, window2y));
        tileMap[x2][window1y] = new WindowTile(new Position(x2, window1y));
        tileMap[x2][window2y] = new WindowTile(new Position(x2, window2y));
        mapSaver.tileSaver(x1, window1y, TileType.WINDOW);
        mapSaver.tileSaver(x1, window2y, TileType.WINDOW);
        mapSaver.tileSaver(x2, window1y, TileType.WINDOW);
        mapSaver.tileSaver(x2, window2y, TileType.WINDOW);
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
