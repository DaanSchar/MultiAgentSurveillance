package nl.maastrichtuniversity.dke.scenario;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.areas.Area;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Environment {

    private String name;
    private int gameMode;

    private double timeStep;

    private StaticEnvironment staticEnvironment;
    private DynamicEnvironment dynamicEnvironment;

    public Environment() {
        this.staticEnvironment = new StaticEnvironment();
    }
    
    public Environment(String name, int gameMode, double timeStep, StaticEnvironment staticEnvironment, DynamicEnvironment dynamicEnvironment) {
        this.name = name;
        this.gameMode = gameMode;
        this.timeStep = timeStep;
        this.staticEnvironment = staticEnvironment;
        this.dynamicEnvironment = dynamicEnvironment;
    }
    
    public List<Area> getObjects(){
        List<Area> areas = new ArrayList<>();
        for(List<Area> area:staticEnvironment.getAreas().values()){
            areas.addAll(area);
        }
        areas.addAll(dynamicEnvironment.getDoors());
        areas.addAll(dynamicEnvironment.getWindows());
        for(Guard guard:dynamicEnvironment.getGuards()){
            areas.add(guard.getArea());
        }
        for(Intruder intruder:dynamicEnvironment.getIntruders()){
            areas.add(intruder.getArea());
        }
        return areas;
    }
    


}
