package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.*;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

@Getter
@EqualsAndHashCode
@ToString
public class Tile {

    private @Getter final Position position;
    private boolean isOpened;
    private @Setter TileType type;

    public Tile(Position position) {
        this.position = position;
        this.type = TileType.EMPTY;
    }

    public Tile(Position position, TileType type) {
        this.position = position;
        this.type = type;
    }

    public boolean isEmpty() {
        return type == TileType.EMPTY;
    }

    public boolean isPassable() {
        return type.isPassable();
    }


}
