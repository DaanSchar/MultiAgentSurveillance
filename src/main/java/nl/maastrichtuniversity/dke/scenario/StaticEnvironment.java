package nl.maastrichtuniversity.dke.scenario;

import java.util.ArrayList;
import java.util.HashMap;
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
public class StaticEnvironment {

    private double width;
    private double height;
    private double scaling;

    private HashMap<String, List<Area>> areas;

    public StaticEnvironment() {
        this.areas = initArea();
    }

    public List<Area> get(String areaName) {
        return areas.get(areaName);
    }

    private HashMap<String, List<Area>> initArea() {
        HashMap<String, List<Area>> staticAreas = new HashMap<>();

        staticAreas.put("targetArea", new ArrayList<>());
        staticAreas.put("spawnAreaIntruders", new ArrayList<>());
        staticAreas.put("spawnAreaGuards", new ArrayList<>());
        staticAreas.put("wall", new ArrayList<>());
        staticAreas.put("teleport", new ArrayList<>());
        staticAreas.put("shaded", new ArrayList<>());
        staticAreas.put("texture", new ArrayList<>());
        staticAreas.put("window", new ArrayList<>());
        staticAreas.put("door", new ArrayList<>());
        staticAreas.put("sentrytower", new ArrayList<>());

        return staticAreas;
    }

}