package nl.maastrichtuniversity.dke.scenario;

import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.areas.Area;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DynamicEnvironment {

    private List<Intruder> intruders;
    private List<Guard> guards;
    private List<Area> windows;
    private List<Area> doors;

    public DynamicEnvironment() {
        this.intruders = new ArrayList<>();
        this.guards = new ArrayList<>();
        this.windows = new ArrayList<>();
        this.doors = new ArrayList<>();
    }



}
