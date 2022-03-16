package nl.maastrichtuniversity.dke.reinforcement;

import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.util.MapParser;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import java.io.File;
import java.util.LinkedList;

public class Environment implements MDP<NeuralGameState, Integer, DiscreteSpace> {

    private final Scenario scenario = new MapParser(new File("maps/testmap.txt")).createScenario();


    @Override
    public ObservationSpace<NeuralGameState> getObservationSpace() {
        return null;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return null;
    }

    @Override
    public NeuralGameState reset() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<NeuralGameState> step(Integer integer) {
        return null;
    }

    @Override
    public boolean isDone() {
        int numberOfTiles = scenario.getEnvironment().getHeight() * scenario.getEnvironment().getWidth();
        LinkedList<Tile> exploredTiles = new LinkedList<>();

        for (Agent a : scenario.getGuards()) {
            for (Tile[] tCol : a.getMemoryModule().getMap().getTileMap()) {
                for (Tile t : tCol) {
                    if (!exploredTiles.contains(t)) {
                        exploredTiles.add(t);
                    }
                    if (exploredTiles.size() == numberOfTiles) {
                        return true;
                    }
                }
            }
        }
        return exploredTiles.size() == numberOfTiles;
    }

    @Override
    public MDP<NeuralGameState, Integer, DiscreteSpace> newInstance() {
        return new Environment();
    }
}
