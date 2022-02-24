package nl.maastrichtuniversity.dke.explore;

import lombok.Getter;

@Getter
public enum AgentTileType {
    WALL(true, false),
    TELEPORT(true,false),
    SENTRY(true, false),
    TARGET(true, true),
    START(true,true),
//    VISITED(true,true),

    EMPTY(false, true),
    WINDOW(false, false),
    DOOR(false, false);

    private final boolean isStatic;
    private boolean passable;

    AgentTileType(boolean isStatic, boolean passable) {
        this.isStatic = isStatic;
        this.passable = passable;
    }

}
