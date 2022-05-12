package nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Fleet;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Scenario {

    private String name;
    private int gameMode;
    private double timeStep;
    private double scaling;

    private Environment environment;
    private Fleet<Guard> guards;
    private Fleet<Intruder> intruders;
    private List<Smell> smellMap;
    private List<List<Sound>> soundMap;
    private List<CommunicationMark> communicationMarks;

    private int currentTimeStep;

    public Scenario(String name, int gameMode, double timeStep, double scaling, Environment environment) {
        this.name = name;
        this.gameMode = gameMode;
        this.timeStep = timeStep;
        this.scaling = scaling;
        this.smellMap = new ArrayList<>();
        this.communicationMarks = new ArrayList<>();
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
        if (soundMap.size() < 1) {
            return new ArrayList<>();
        }

        return soundMap.get(soundMap.size() - 1);
    }

    public void addSounds(List<Sound> sound) {
        soundMap.add(sound);
    }

}
