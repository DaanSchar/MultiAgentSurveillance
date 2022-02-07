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
    private @Getter @Setter List<Area> spawnAreaIntruders;
    private @Getter @Setter List<Area> spawnAreaGuards;
    private @Getter @Setter List<Area> walls;
    private @Getter @Setter List<Area> shadedAreas;
    private @Getter @Setter List<Area> teleportPortals;
    private @Getter @Setter Area targetArea;

    public Environment(double width, double height, List<Area> spawnAreaIntruders, List<Area> spawnAreaGuards, List<Area> walls, List<Area> shadedAreas, List<Area> teleportPortals, Area targetArea) {
        this.width = width;
        this.height = height;
        this.spawnAreaIntruders = spawnAreaIntruders;
        this.spawnAreaGuards = spawnAreaGuards;
        this.walls = walls;
        this.shadedAreas = shadedAreas;
        this.teleportPortals = teleportPortals;
        this.targetArea = targetArea;
    }

    public Environment() {
        this.spawnAreaGuards = new ArrayList<>();
        this.spawnAreaIntruders = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.shadedAreas = new ArrayList<>();
        this.teleportPortals = new ArrayList<>();
    }


}