package nl.maastrichtuniversity.dke.experiments;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.agents.modules.victory.Victory;

import java.util.ArrayList;

@Getter
@Setter
public class EventExperiment {

    private Game game;
    private ArrayList<Victory> eventList = new ArrayList<>();

    public EventExperiment(Game game) {
        this.game = game;
    }
}
