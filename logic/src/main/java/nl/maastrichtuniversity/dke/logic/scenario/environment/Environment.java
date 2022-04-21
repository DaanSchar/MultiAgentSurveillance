package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.agents.util.MoveAction;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class Environment implements Collection<Tile> {

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

        fillMapWithEmptyTiles();
    }

    public List<Tile> get(TileType type) {
        return this.filter(tile -> tile.getType() == type);
    }

    public List<Tile> getNeighborsAndFilter(Tile tile, Predicate<Tile> predicate) {
        return getNeighboringTiles(tile).stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Tile> getNeighboringTiles(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        for (Direction direction : Direction.getAllDirections()) {
            try {
                neighbors.add(getNeighbor(tile, direction));
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }

        return neighbors;
    }

    public List<Tile> filter(Predicate<Tile> predicate) {
        return this.stream().filter(predicate).collect(Collectors.toList());
    }

    private Tile getNeighbor(Tile tile, Direction direction) throws ArrayIndexOutOfBoundsException {
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();

        return tileMap[x + direction.getMoveX()][y + direction.getMoveY()];
    }

    private void fillMapWithEmptyTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tileMap[x][y] = new Tile(new Position(x, y), TileType.EMPTY);
            }
        }
    }


    @Override
    public int size() {
        return width * height;
    }

    @Override
    public boolean isEmpty() {
        for (Tile t : this) {
            if (t.getType() != TileType.EMPTY) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Tile)) {
            return false;
        }

        Tile tile = (Tile) o;
        for (Tile referenceTile : this) {
            if (referenceTile.equals(tile)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size()];

        int i = 0;
        for (Tile tile : this) {
            array[i] = tile;
            i++;
        }

        return array;
    }

    @Override
    public boolean add(Tile tile) {
        var x = tile.getPosition().getX();
        var y = tile.getPosition().getY();

        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return false;
        }

        tileMap[x][y] = tile;

        return true;
    }

    /**
     * replaces the tile at the given position with a tile of type EMPTY
     *
     * @param o tile we want to remove
     * @return true if the tile was removed, false if it was not found
     */
    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Tile)) {
            return false;
        }

        Tile tile = (Tile) o;
        var tilePosition = tile.getPosition();
        var x = tilePosition.getX();
        var y = tilePosition.getY();

        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return false;
        }

        tileMap[x][y] = new Tile(tilePosition, TileType.EMPTY);

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean failedRemove = false;

        for (Object o : c) {
            boolean removed = this.remove(o);
            if (!removed) {
                failedRemove = true;
            }
        }

        return !failedRemove;
    }

    @Override
    public void clear() {
        fillMapWithEmptyTiles();
    }

    @Override
    public boolean addAll(Collection<? extends Tile> c) {
        log.warn("addAll() not implemented, returning false");
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        log.warn("retainAll() not implemented, returning false");
        return false;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        log.warn("toArray(T[] a) not implemented, returning empty list of type T");
        return (T[]) new Object[0];
    }

    /**
     * iterators may be used to iterate over the tiles in the environment
     * you use them in a for-each loop like this:
     * <p>
     * for (Tile tile : environment) {
     * // do something with the tile
     * }
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
            return i >= width || j >= height;
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
