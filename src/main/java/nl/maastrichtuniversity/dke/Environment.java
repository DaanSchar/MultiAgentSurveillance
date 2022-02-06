package nl.maastrichtuniversity.dke;
import java.util.List;
import nl.maastrichtuniversity.dke.areas.Area;

public class Environment{
    private double width;
    private double height;
    private List<Area> spawnAreaIntruders;
    private List<Area> spawnAreaGuards;
    private List<Area> walls;
    private List<Area> shadedAreas;
    private List<Area> teleportPortals;
    private Area targetArea;

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

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<Area> getSpawnAreaIntruders() {
        return this.spawnAreaIntruders;
    }

    public void setSpawnAreaIntruders(List<Area> spawnAreaIntruders) {
        this.spawnAreaIntruders = spawnAreaIntruders;
    }

    public List<Area> getSpawnAreaGuards() {
        return this.spawnAreaGuards;
    }

    public void setSpawnAreaGuards(List<Area> spawnAreaGuards) {
        this.spawnAreaGuards = spawnAreaGuards;
    }

    public List<Area> getWalls() {
        return this.walls;
    }

    public void setWalls(List<Area> walls) {
        this.walls = walls;
    }

    public List<Area> getShadedAreas() {
        return this.shadedAreas;
    }

    public void setShadedAreas(List<Area> shadedAreas) {
        this.shadedAreas = shadedAreas;
    }

    public List<Area> getTeleportPortals() {
        return this.teleportPortals;
    }

    public void setTeleportPortals(List<Area> teleportPortals) {
        this.teleportPortals = teleportPortals;
    }

    public Area getTargetArea() {
        return this.targetArea;
    }

    public void setTargetArea(Area targetArea) {
        this.targetArea = targetArea;
    }


}