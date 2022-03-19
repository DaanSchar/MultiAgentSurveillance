package nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Guard;
import nl.maastrichtuniversity.dke.logic.agents.Intruder;
import nl.maastrichtuniversity.dke.logic.agents.modules.communication.CommunicationMark;
import nl.maastrichtuniversity.dke.logic.agents.modules.memory.MemoryModule;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;

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
    private List<Guard> guards;
    private List<Intruder> intruders;
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


    /**
     * Collects all the tiles explored by the agents (Guards) of the environment and returns them in a list.
     * @return List of tiles explored by the agents (Guards).
     */
    public List<Tile> getCoveredTiles() {
        ArrayList<Tile> coveredTiles = new ArrayList<>();

        for (Guard guard : guards) {
            coveredTiles.addAll(((MemoryModule)guard.getMemoryModule()).getCoveredTiles());
        }

        return coveredTiles;
    }


}
