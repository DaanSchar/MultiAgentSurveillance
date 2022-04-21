package nl.maastrichtuniversity.dke.logic.agents.util;

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

    public static Direction getDirectionAfterRotation(MoveAction action, Direction direction) {
        if (direction.equals(Direction.NORTH)) {
            if (action.equals(MoveAction.STAND_STILL)) {
                return Direction.NORTH;
            } else if (action.equals(MoveAction.ROTATE_LEFT)) {
                return Direction.WEST;
            } else {
                return Direction.EAST;
            }
        } else if (direction.equals(Direction.EAST)) {
            if (action.equals(MoveAction.STAND_STILL)) {
                return Direction.EAST;
            } else if (action.equals(MoveAction.ROTATE_LEFT)) {
                return Direction.NORTH;
            } else {
                return Direction.SOUTH;
            }
        } else if (direction.equals(Direction.WEST)) {
            if (action.equals(MoveAction.STAND_STILL)) {
                return Direction.WEST;
            } else if (action.equals(MoveAction.ROTATE_LEFT)) {
                return Direction.SOUTH;
            } else {
                return Direction.NORTH;
            }
        } else {
            if (action.equals(MoveAction.STAND_STILL)) {
                return Direction.SOUTH;
            } else if (action.equals(MoveAction.ROTATE_LEFT)) {
                return Direction.EAST;
            } else {
                return Direction.WEST;
            }
        }
    }

    public static Direction leftOf(Direction direction) {
        return switch (direction) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            default -> throw new IllegalArgumentException("Direction not found");
        };
    }

    public static Direction rightOf(Direction direction) {
        return switch (direction) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> throw new IllegalArgumentException("Direction not found");
        };
    }
}
