package nl.maastrichtuniversity.dke.logic.agents.util;

import lombok.Getter;

@Getter
public enum Direction {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1,0);

    private int moveX;
    private int moveY;
    Direction(int moveX, int moveY){
        this.moveX = moveX;
        this.moveY = moveY;
    }
}
