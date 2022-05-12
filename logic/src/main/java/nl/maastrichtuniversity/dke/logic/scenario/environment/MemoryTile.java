package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

@Getter
@Setter
public class MemoryTile extends Tile {

    private boolean visited;
    private boolean explored;
    private boolean isOpened;
    private boolean isBroken;

    public MemoryTile(Position position) {
        super(position);
    }

    public MemoryTile(Position position, TileType type) {
        super(position, type);
    }

    public MemoryTile(Tile tile) {
        super(tile.getPosition(), tile.getType());
    }

    public MemoryTile(DoorTile tile) {
        super(tile.getPosition(), tile.getType());
        this.isOpened = tile.isOpened();
    }

    private MemoryTile(WindowTile tile) {
        super(tile.getPosition(), tile.getType());
        this.isBroken = tile.isBroken();
    }

    @Override
    public boolean isPassable() {
        return super.isPassable() && !isVisited() && isPassableIfDoor() && isPassableIfWindow();
    }

    public boolean isPassable(boolean ignoreVisited) {
        return super.isPassable() && isPassableIfDoor() && isPassableIfWindow();
    }

    private boolean isPassableIfDoor() {
        if (getType() == TileType.DOOR) {
            return isOpened();
        }

        return true;
    }
    private boolean isPassableIfWindow() {
        if (getType() == TileType.WINDOW) {
            return !isBroken();
        }

        return true;
    }
}
