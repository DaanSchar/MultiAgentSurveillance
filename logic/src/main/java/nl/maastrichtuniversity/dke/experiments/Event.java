package nl.maastrichtuniversity.dke.experiments;

public interface Event {

    double getTimeStep();

    Enum<EventType> getEventType();
}
