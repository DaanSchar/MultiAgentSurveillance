package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TileType {

    UNKNOWN(true, true, 0),
    EMPTY(true, true, 1),
    WALL(true, false, 2),
    TELEPORT(true, true, 3),
    SHADED(true, true, 4),
    SENTRY(true, true, 5),
    TARGET(true, true, 6),
    SPAWN_INTRUDERS(true, true, 7),
    SPAWN_GUARDS(true, true, 8),
    START(true, true, 9),
    DESTINATION_TELEPORT(true, true, 10),

    WINDOW(false, true, 11),
    DOOR(false, true, 12);

    private final boolean isStatic;
    private final boolean passable;
    private final int value; // for reinforcement learning


}
