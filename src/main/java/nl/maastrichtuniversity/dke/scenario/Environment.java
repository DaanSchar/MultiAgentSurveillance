package nl.maastrichtuniversity.dke.scenario;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.areas.Area;
import nl.maastrichtuniversity.dke.areas.Rectangle;

public class Environment {

    private @Getter @Setter double width;
    private @Getter @Setter double height;
    private @Getter @Setter double scaling;
    private @Getter @Setter List<Area> spawnAreaIntruders;
    private @Getter @Setter List<Area> spawnAreaGuards;
    private @Getter @Setter List<Area> walls;
    private @Getter @Setter List<Area> shadedAreas;
    private @Getter @Setter List<Area> teleportPortals;
    private @Getter @Setter Area targetArea;
    private @Getter @Setter List<Area> windows;
    private @Getter @Setter List<Area> doors;
    private @Getter @Setter List<Area> sentrytowers;

    public Environment(double width, double height,double scaling,List<Area> spawnAreaIntruders, List<Area> spawnAreaGuards, List<Area> walls, List<Area> shadedAreas, List<Area> teleportPortals, Area targetArea, List<Area> windows, List<Area> doors,List<Area> sentrytowers) {
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
        this.sentrytowers = sentrytowers;
    }

    public Environment() {
        this.spawnAreaGuards = new ArrayList<>();
        this.spawnAreaIntruders = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.shadedAreas = new ArrayList<>();
        this.teleportPortals = new ArrayList<>();
        this.windows = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.sentrytowers = new ArrayList<>();
    }

    public List<Area> getObjects(){
        return null;
    }


}