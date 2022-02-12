package nl.maastrichtuniversity.dke.scenario;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.areas.Area;

public class Environment {

    private @Getter @Setter double width;
    private @Getter @Setter double height;
    private @Getter @Setter double scaling;
    private @Getter @Setter List<Area> spawnAreaIntruders;
    private @Getter @Setter List<Area> spawnAreaGuards;
    private @Getter @Setter List<Area> walls;
    private @Getter @Setter List<Area> shadedAreas;
    private @Getter @Setter List<Area> teleportPortals;
    private @Getter @Setter List<Area> targetArea;
    private @Getter @Setter List<Area> windows;
    private @Getter @Setter List<Area> doors;
    private @Getter @Setter List<Area> sentryTowers;
    private List<List<Area>> areas;

    public Environment(double width, double height,double scaling,List<Area> spawnAreaIntruders, List<Area> spawnAreaGuards, List<Area> walls, List<Area> shadedAreas, List<Area> teleportPortals, List<Area> targetArea, List<Area> windows, List<Area> doors,List<Area> sentryTowers) {
        this.width = width;
        this.height = height;
        this.scaling = scaling;
        this.spawnAreaIntruders = spawnAreaIntruders;
        this.spawnAreaGuards = spawnAreaGuards;
        this.walls = walls;
        this.shadedAreas = shadedAreas;
        this.teleportPortals = teleportPortals;
        this.targetArea = targetArea;
        this.windows = windows;
        this.doors = doors;
        this.sentryTowers = sentryTowers;
    }

    public Environment() {
        this.spawnAreaGuards = new ArrayList<>();
        this.spawnAreaIntruders = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.shadedAreas = new ArrayList<>();
        this.teleportPortals = new ArrayList<>();
        this.windows = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.sentryTowers = new ArrayList<>();
    }

    public List<List<Area>> getAreas() {
    	areas = new ArrayList<>();
    	areas.add(spawnAreaGuards);
    	areas.add(spawnAreaIntruders);
    	areas.add(walls);
    	areas.add(shadedAreas);
    	areas.add(teleportPortals);
    	areas.add(targetArea);
    	areas.add(windows);
    	areas.add(doors);
    	areas.add(sentryTowers);
    	return areas;
    }


}