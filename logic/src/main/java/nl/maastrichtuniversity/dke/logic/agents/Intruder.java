package nl.maastrichtuniversity.dke.logic.agents;

import lombok.Getter;
import lombok.Setter;

public class Intruder extends Agent {

    private @Getter @Setter boolean alive;

    public Intruder() {
        super();
        this.alive = true; //all the intruders are alive at start
    }

}
