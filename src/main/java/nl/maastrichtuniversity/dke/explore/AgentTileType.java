package nl.maastrichtuniversity.dke.explore;

public enum AgentTileType {
    WALL(true),
    TELEPORT(true),
    SENTRY(true),
    TARGET(true),
    START(true),

    EMPTY(false),
    WINDOW(false),
    DOOR(false);

    private final boolean isStatic;

    AgentTileType(boolean isStatic) {
        this.isStatic = isStatic;
    }
}
