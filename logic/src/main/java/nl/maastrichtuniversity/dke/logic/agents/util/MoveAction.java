package nl.maastrichtuniversity.dke.logic.agents.util;

import lombok.Getter;

public enum MoveAction {

    MOVE_FORWARD(0),
    SPRINT_FORWARD(2),
    ROTATE_LEFT(-1),
    ROTATE_RIGHT(1),
    STAND_STILL(Integer.MAX_VALUE);

    private @Getter final int value;

    MoveAction(int action) {
        this.value = action;
    }



}
