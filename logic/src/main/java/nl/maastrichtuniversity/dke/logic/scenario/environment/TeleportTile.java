package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class TeleportTile extends Tile {

    private @Getter final Position targetPosition;
    private @Getter final int rotation;

    public TeleportTile(Position position, int targetX, int targetY, int rotation) {
        super(position, TileType.TELEPORT);
        targetPosition = new Position(targetX, targetY);
        this.rotation = rotation;
    }
}
