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

    // TODO: add current time here
    private int currentTimeStep;

    private Environment environment;
    private Fleet<Guard> guards;
    private Fleet<Intruder> intruders;
    private List<Smell> smellMap;
    private List<Sound> soundMap;
    private List<CommunicationMark> communicationMarks;

    public Scenario(String name, int gameMode, double timeStep, double scaling, Environment environment) {
        this.name = name;
        this.gameMode = gameMode;
        this.timeStep = timeStep;
        this.scaling = scaling;
        this.soundMap = new ArrayList<>();
        this.smellMap = new ArrayList<>();
        this.communicationMarks = new ArrayList<>();
        this.environment = environment;
        this.currentTimeStep = 0;
    }

    public void incrementTimeStep() {
        currentTimeStep++;
    }

    public double getCurrentTime() {
        return currentTimeStep * timeStep;
    }

}
