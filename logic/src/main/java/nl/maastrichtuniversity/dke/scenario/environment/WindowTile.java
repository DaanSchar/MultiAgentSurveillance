package nl.maastrichtuniversity.dke.scenario.environment;

import lombok.Getter;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public class WindowTile extends Tile {

    private @Getter boolean isBroken;

    public WindowTile(Position position) {
        super(position, TileType.WINDOW);
        isBroken = false;
    }

    public boolean breakWindow() {
        if (!isBroken) {
            isBroken = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean isPassable() {
        return super.isPassable() && isBroken;
    }
}
