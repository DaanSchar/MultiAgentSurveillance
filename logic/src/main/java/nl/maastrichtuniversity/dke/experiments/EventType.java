package nl.maastrichtuniversity.dke.experiments;

public enum EventType {
    GUARD_SEEN(),
    INTRUDER_SEEN(),
    INTRUDER_CAUGHT(),
    GUARD_VICTORY(),
    INTRUDER_VICTORY();

    EventType() {

    }
}
