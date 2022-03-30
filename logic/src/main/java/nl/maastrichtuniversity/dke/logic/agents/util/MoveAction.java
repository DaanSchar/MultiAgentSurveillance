package main.java.nl.maastrichtuniversity.dke.logic.agents.util;

import lombok.Getter;

public enum MoveAction {

    MOVE_FORWARD(0),
    ROTATE_LEFT(-1),
    ROTATE_RIGHT(1),
    DO_NOTHING(Integer.MAX_VALUE);

    private @Getter final int value;

    MoveAction(int action) {this.value = action;}



}
