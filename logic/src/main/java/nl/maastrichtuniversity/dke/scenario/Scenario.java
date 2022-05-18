package nl.maastrichtuniversity.dke.scenario;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Fleet;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.communication.Mark;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class Scenario {

    private String name;
    private int gameMode;
    private double timeStep;
    private double scaling;

    private Environment environment;
    private Fleet<Guard> guards;
    private Fleet<Intruder> intruders;
    private List<Smell> smellMap;
    private List<Sound> soundMap;
    private List<Mark> marks;

    private int currentTimeStep;

    public Scenario(String name, int gameMode, double timeStep, double scaling, Environment environment) {
        this.name = name;
        this.gameMode = gameMode;
        this.timeStep = timeStep;
        this.scaling = scaling;
        this.smellMap = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.environment = environment;
        this.currentTimeStep = 0;
        this.soundMap = new ArrayList<>();
    }

    public void incrementTimeStep() {
        currentTimeStep++;
    }

    public double getCurrentTime() {
        return currentTimeStep * timeStep;
    }

    public List<Sound> getSoundMap() {
        return soundMap;
    }

    public void addSounds(List<Sound> sound) {
        soundMap.addAll(sound);
    }

}
