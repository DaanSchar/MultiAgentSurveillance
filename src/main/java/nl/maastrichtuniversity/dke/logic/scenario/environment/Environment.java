package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
public class Environment implements Iterable<Tile> {

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

        for (Tile tile : this) {
            if (tile.getType() == type) {
                tiles.add(tile);
            }
        }

        return tiles;
    }


    /**
     * iterators may be used to iterate over the tiles in the environment
     * you use them in a for-each loop like this:
     *
     *     for (Tile tile : environment) {
     *         // do something with the tile
     *     }
     *
     * @return an iterator over all tiles in the environment
     */
    @Override
    public Iterator<Tile> iterator() {
        return new EnvironmentIterator(this.tileMap);
    }

    public static class EnvironmentIterator implements Iterator<Tile> {

        private final Tile[][] internalTileMap;
        private final int width;
        private final int height;
        private int i, j;

        public EnvironmentIterator(Tile[][] tileMap) {
            this.internalTileMap = tileMap;
            this.i = 0;
            this.j = 0;
            this.width = tileMap.length;
            this.height = tileMap[0].length;
        }

        @Override
        public boolean hasNext() {
            return !indexIsOutsideBounds();
        }

        @Override
        public Tile next() {
            Tile value = internalTileMap[i][j];
            incrementIndex();

            return value;
        }

        private boolean indexIsOutsideBounds() {
            return indexIsTooSmall() || indexIsTooLarge();
        }

        private boolean indexIsTooSmall() {
            return i < 0 || j < 0;
        }

        private boolean indexIsTooLarge() {
            return i >= width|| j >= height;
        }

        private void incrementIndex() {
            if (j >= height - 1) {
                j = 0;
                i++;
            } else {
                j++;
            }
        }

    }
}
