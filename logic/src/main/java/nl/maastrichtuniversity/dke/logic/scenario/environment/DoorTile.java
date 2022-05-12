package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class DoorTile extends Tile {

    private @Getter
    boolean isOpened;

    public DoorTile(Position position) {
        super(position, TileType.DOOR);
        isOpened = false;
    }

    public void toggleDoor() {
        isOpened = !isOpened;
    }

    @Override
    public boolean isPassable() {
        return super.isPassable() && isOpened;
    }
}
