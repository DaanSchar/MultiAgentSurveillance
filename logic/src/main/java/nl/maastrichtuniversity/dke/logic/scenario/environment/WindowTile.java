package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class WindowTile extends Tile {

    private @Getter boolean isBroken;

    public WindowTile(Position position) {
        super(position, TileType.WINDOW);
        isBroken = false;
    }

    public void breakWindow() {
        isBroken = true;
    }

    @Override
    public boolean isPassable() {
        return super.isPassable() && isBroken;
    }
}
