package nl.maastrichtuniversity.dke.experiments;

public class Event {

    private EventType type;
    private Double time;

    public Event(EventType type, double time) {
        this.type = type;
        this.time = time;
    }
}
