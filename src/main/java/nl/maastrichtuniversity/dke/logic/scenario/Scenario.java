package nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import lombok.Setter;
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
    private @Setter List<Guard> guards;
    private @Setter List<Intruder> intruders;
    private @Getter @Setter List<Smell> smellMap;
    private @Getter @Setter List<Sound> soundMap;
    private @Getter @Setter List<CommunicationMark> communicationMarks;

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

    public double[] getStateVector() {
        int mapSize = environment.getWidth() * environment.getHeight();
        double[] stateVector = new double[mapSize * guards.size()];

        for (int i = 0; i < guards.size(); i++) {
            Environment memoryMap = guards.get(i).getMemoryModule().getMap();
            double[] mapStateVector = memoryMap.getStateVector();

            System.arraycopy(mapStateVector, 0, stateVector, i * mapSize, mapSize);
        }

        return stateVector;
    }

}
