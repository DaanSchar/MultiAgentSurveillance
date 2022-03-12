package nl.maastrichtuniversity.dke.discrete;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.Guard;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Scenario {

    private String name;
    private int gameMode;
    private double timeStep;
    private double scaling;

    private Environment environment;
    private @Setter List<Guard> guards;
    private @Setter List<Intruder> intruders;
    private @Getter @Setter List<Sound> soundMap;
    private @Getter @Setter List<CommunicationMark> communicationMarks;

    public Scenario(String name, int gameMode, double timeStep, double scaling, Environment environment) {
        this.name = name;
        this.gameMode = gameMode;
        this.timeStep = timeStep;
        this.scaling = scaling;
        this.soundMap = new ArrayList<>();
        this.communicationMarks = new ArrayList<>();
        this.environment = environment;

    }



}
