package nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.Fleet;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration.SoundType;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
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
        return soundMap;
    }

    public void addSounds(List<Sound> sound) {
        soundMap.addAll(sound);
    }

}
