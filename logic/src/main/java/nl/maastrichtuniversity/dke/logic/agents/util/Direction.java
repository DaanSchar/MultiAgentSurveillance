package main.java.nl.maastrichtuniversity.dke.logic.agents.util;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Direction {

    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1,0),
    NORTHEAST(1,-1),
    NORTHWEST(-1,-1),
    SOUTHEAST(1,1),
    SOUTHWEST(-1,1);

    private final int moveX;
    private final int moveY;

    Direction(int moveX, int moveY){
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

    public int getOptimalRotation(Direction currentDirection, Direction targetDirection){
        if (currentDirection == targetDirection) { return 0; }

        if (currentDirection == Direction.NORTH) {
            if (targetDirection == Direction.EAST) { return 1; }
            if (targetDirection == Direction.WEST) { return -1; }
            return 1;
        } else if (currentDirection == Direction.EAST) {
            if (targetDirection == Direction.SOUTH) { return 1; }
            if (targetDirection == Direction.NORTH) { return -1; }
            return 1;
        } else if (currentDirection == Direction.SOUTH) {
            if (targetDirection == Direction.WEST) { return 1; }
            if (targetDirection == Direction.EAST) { return -1; }
            return 1;
        }
        if (currentDirection == Direction.WEST) {
            if (targetDirection == Direction.NORTH) { return 1; }
            if (targetDirection == Direction.SOUTH) { return -1; }
            return 1;
        }

        return 0;
    }

}
