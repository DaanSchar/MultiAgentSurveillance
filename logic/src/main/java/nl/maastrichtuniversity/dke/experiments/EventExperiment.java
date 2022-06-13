package nl.maastrichtuniversity.dke.experiments;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.victory.Victory;
import nl.maastrichtuniversity.dke.scenario.Scenario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

@Getter
@Setter
public class EventExperiment {

    private Game game;
    private Scenario scenario;
    private String events = " ";
    private String times = " ";
    private String gameNumbers = " ";
    private ArrayList<Event> eventList = new ArrayList<>();

    public EventExperiment(Game game) {
        this.game = game;
        this.scenario = game.getScenario();
    }

    public void addEvents(double timeStep, int gameNumber) {
        isSeenEvent(timeStep, gameNumber);
        isCatchEvent(timeStep, gameNumber);
        isVictoryEvent(timeStep, gameNumber);
    }

    public void isSeenEvent(double time, int gameNumber) {
        ArrayList<Intruder> intruders = scenario.getIntruders();
        ArrayList<Guard> guards = scenario.getGuards();

        for (Intruder intruder : intruders) {
            if (intruder.seesGuard()) {
                Event event = new Event(EventType.GUARD_SEEN, time, gameNumber);
                eventList.add(event);
            }
        }

        for (Guard guard : guards) {
            if (guard.seesIntruder()) {
                Event event = new Event(EventType.INTRUDER_SEEN, time, gameNumber);
                eventList.add(event);
            }
        }
    }

    public void isCatchEvent(double time, int gameNumber) {
        ArrayList<Intruder> intruders = scenario.getIntruders();

        for (Intruder intruder : intruders) {
            if (intruder.isCaught()) {
                Event event = new Event(EventType.INTRUDER_CAUGHT, time, gameNumber);
                eventList.add(event);
            }
        }
    }

    public void isVictoryEvent(double time, int gameNumber) {
        Victory victory = game.getVictory();

        if (victory.intrudersHaveWon()) {
            Event event = new Event(EventType.INTRUDER_VICTORY, time, gameNumber);
            eventList.add(event);

        } else if (victory.guardsHaveWon()) {
            Event event = new Event(EventType.GUARD_VICTORY, time, gameNumber);
            eventList.add(event);
        }
    }

    public void printEvents() {
        events = " ";
        times = " ";
        gameNumbers = " ";

        for (Event e : eventList) {
            events += e.getType() + ", ";
            times += (e.getTime() + ", ");
            gameNumbers += (e.getGameNumber() + ", ");
        }
    }

    public void toCSV(String fileName) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
            StringBuilder sb = new StringBuilder();

            sb.append("Game Number");
            sb.append(",");
            sb.append("Time-Step");
            sb.append(",");
            sb.append("Event");
            sb.append("\r\n");

            // Append strings from array
            for (Event e : eventList) {
                sb.append(e.getGameNumber());
                sb.append(",");
                sb.append(e.getTime());
                sb.append(",");
                sb.append(e.getType());
                sb.append("\r\n");
            }

            br.write(sb.toString());
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
