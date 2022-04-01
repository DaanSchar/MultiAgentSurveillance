package nl.maastrichtuniversity.dke.logic.agents.modules.listening;

import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.util.ArrayList;
import java.util.List;

public class ListeningModule extends AgentModule implements IListeningModule {
    private final Environment environment;

    public ListeningModule(Scenario scenario) {
        super(scenario);
        environment = scenario.getEnvironment();
    }

    /**
     * @param position the position in which the agent is right now
     * @return a boolean representing whether there is a sound in that position
     */
    @Override
    public boolean getSound(Position position) {
        List<Sound> soundMap = scenario.getSoundMap();
        for (Sound sound : soundMap) {
            if (sound.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param position the position in which the agent is right now
     * @return a list of directions, the direction is where the sound is coming from (the sound source),
     * there can be more than one sound in the agents position
     */
    @Override
    public List<Direction> getDirection(Position position) {
        List<Sound> soundMap = scenario.getSoundMap();
        List<Direction> soundSource = new ArrayList<>();
        for (Sound sound : soundMap) {
            if (sound.getPosition().equals(position)) {
                Position source = sound.getSource();
                soundSource.add(computeDirection(position, source));
            }
        }
        return soundSource;
    }

    /**
     * Given the source sound and the actual position of the agent,
     * compute the direction of the source relative to the agent
     ***/
    public Direction computeDirection(Position position, Position source) {
        int X1 = position.getX();
        int Y1 = position.getY();
        int X2 = source.getX();
        int Y2 = source.getY();

        if (X1 < X2 && Y1 < Y2) {
            return Direction.SOUTHEAST;
        } else if (X1 < X2 && Y1 > Y2) {
            return Direction.NORTHEAST;
        } else if (X1 > X2 && Y1 < Y2) {
            return Direction.SOUTHWEST;
        } else if (X1 > X2 && Y1 > Y2) {
            return Direction.NORTHWEST;
        } else if (X1 == X2 && Y1 < Y2) {
            return Direction.SOUTH;
        } else if (X1 == X2 && Y1 > Y2) {
            return Direction.NORTH;
        } else if (X1 > X2 && Y1 == Y2) {
            return Direction.WEST;
        } else if (X1 < X2 && Y1 == Y2) {
            return Direction.EAST;
        } else return null;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
