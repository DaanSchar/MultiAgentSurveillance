package nl.maastrichtuniversity.dke.scenario;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.areas.Area;

@AllArgsConstructor
@Getter
@Setter
public class Environment {

    private double width;
    private double height;
    private double scaling;

    private List<Intruder> intruders;
    private List<Guard> guards;

    private List<Area> spawnAreaIntruders;
    private List<Area> spawnAreaGuards;
    private List<Area> walls;
    private List<Area> shadedAreas;
    private List<Area> teleportPortals;
    private List<Area> targetArea;
    private List<Area> windows;
    private List<Area> doors;
    private List<Area> sentryTowers;

    public Environment() {
        this(0, 0, 0, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }


    public List<Area> getObjects() {
        return null;
    }
}