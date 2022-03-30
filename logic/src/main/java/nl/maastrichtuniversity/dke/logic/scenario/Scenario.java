package main.java.nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import lombok.Setter;
import main.java.nl.maastrichtuniversity.dke.logic.agents.Fleet;
import main.java.nl.maastrichtuniversity.dke.logic.agents.Guard;
import main.java.nl.maastrichtuniversity.dke.logic.agents.Intruder;
import main.java.nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import main.java.nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;

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
    }

}
