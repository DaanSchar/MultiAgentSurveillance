package nl.maastrichtuniversity.dke.discrete;

import lombok.Getter;

@Getter
public enum TileType {

    WALL(true),
    TELEPORT(true),
    SHADED(true),
    SENTRY(true),
    EMPTY(true),

    WINDOW(false),
    DOOR(false),
    INTRUDER(false),
    GUARD(false);

    private final boolean isStatic;

    TileType(boolean isStatic) {
        this.isStatic = isStatic;
    }


}
