package nl.maastrichtuniversity.dke.discrete;

import lombok.Getter;

@Getter
public enum TileType {

    WALL(true, false),
    TELEPORT(true,true),
    SHADED(true, true ),
    SENTRY(true, true),
    TARGET(true, true),
    SPAWN_INTRUDERS(true, true),
    SPAWN_GUARDS(true, true),
    EMPTY(true, true),


    WINDOW(false, true),
    DOOR(false, true);

    private final boolean isStatic;
    private boolean passable;

    TileType(boolean isStatic, boolean passable) {
        this.isStatic = isStatic;
        this.passable = passable;
    }


}
