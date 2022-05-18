
package nl.maastrichtuniversity.dke.agents.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Direction {

    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0),
    NORTHEAST(1, -1),
    NORTHWEST(-1, -1),
    SOUTHEAST(1, 1),
    SOUTHWEST(-1, 1);

    private final int moveX;
    private final int moveY;

    Direction(int moveX, int moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public static List<Direction> getAllDirections() {
        List<Direction> directions = new ArrayList<>();
        directions.add(NORTH);
        directions.add(EAST);
        directions.add(SOUTH);
        directions.add(WEST);

        return directions;
    }

    public Direction leftOf() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            default -> throw new IllegalArgumentException("Direction not found");
        };
    }

    public Direction rightOf() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> throw new IllegalArgumentException("Direction not found");
        };
    }

    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            default -> throw new IllegalArgumentException("Direction not found");
        };
    }

}