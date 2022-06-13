package nl.maastrichtuniversity.dke.experiments;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.victory.Victory;
import nl.maastrichtuniversity.dke.scenario.Scenario;

import java.util.ArrayList;

@Getter
@Setter
public class EventExperiment {

    private Game game;
    private Scenario scenario;
    private ArrayList<Event> eventList = new ArrayList<>();

    public EventExperiment(Game game) {
        this.game = game;
        this.scenario = game.getScenario();
    }

    public void addEvents(double timeStep) {
        isSeenEvent(timeStep);
        isCatchEvent(timeStep);
        isVictoryEvent(timeStep);
    }

    public void isSeenEvent(double time) {
        ArrayList<Intruder> intruders = scenario.getIntruders();
        ArrayList<Guard> guards = scenario.getGuards();

        for (Intruder intruder : intruders) {
            if (intruder.seesGuard()) {
                Event event = new Event(EventType.GUARD_SEEN, time);
                eventList.add(event);

            } else if (intruder.seesIntruder()) {
                Event event = new Event(EventType.INTRUDER_SEEN, time);
                eventList.add(event);
            }
        }

        for (Guard guard : guards) {
            if (guard.seesGuard()) {
                Event event = new Event(EventType.GUARD_SEEN, time);
                eventList.add(event);

            } else if (guard.seesIntruder()) {
                Event event = new Event(EventType.INTRUDER_SEEN, time);
                eventList.add(event);
            }
        }
    }

    public void isCatchEvent(double time) {
        ArrayList<Intruder> intruders = scenario.getIntruders();

        for (Intruder intruder : intruders) {
            if (intruder.isCaught()) {
                Event event = new Event(EventType.INTRUDER_CAUGHT, time);
                eventList.add(event);
            }
        }
    }

    public void isVictoryEvent(double time) {
        Victory victory = game.getVictory();

        if (victory.intrudersHaveWon()) {
            Event event = new Event(EventType.INTRUDER_VICTORY, time);
            eventList.add(event);

        } else if (victory.guardsHaveWon()) {
            Event event = new Event(EventType.GUARD_VICTORY, time);
            eventList.add(event);
        }
    }
}
