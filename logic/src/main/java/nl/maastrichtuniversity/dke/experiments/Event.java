package nl.maastrichtuniversity.dke.experiments;

import lombok.Getter;

@Getter
public class Event {

    private EventType type;
    private Double time;
    private int gameNumber;

    public Event(EventType type, double time, int gameNumber) {
        this.type = type;
        this.time = time;
        this.gameNumber = gameNumber;
    }
}
