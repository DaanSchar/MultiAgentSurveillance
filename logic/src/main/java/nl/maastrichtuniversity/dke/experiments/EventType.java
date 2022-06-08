package nl.maastrichtuniversity.dke.experiments;

public enum EventType {
    GUARD_SEEN(),
    INTRUDER_SEEN(),
    INTRUDER_CAUGHT(),
    DOOR_OPENED(),
    WINDOW_OPENED(),
    DOOR_CLOSED(),
    WINDOW_CLOSED(),
    GUARD_VICTORY(),
    INTRUDER_VICTORY();

    EventType() {

    }
}
