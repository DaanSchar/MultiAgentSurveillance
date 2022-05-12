package nl.maastrichtuniversity.dke.logic.agents.modules.listening;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.Sound;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import nl.maastrichtuniversity.dke.util.Distribution;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public List<Sound> getSounds(Position position) {
        List<Sound> soundMap = scenario.getSoundMap();
        List<Sound> sounds = new ArrayList<>();

        for (Sound sound : soundMap) {
            if (sound.getPosition().equals(position)) {
                sounds.add(sound);
            }
        }

        return sounds;
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

    public Position guessPositionOfSource(Position currentPosition) {
        Position sourceOfSound = getSource(currentPosition);

        if (sourceOfSound == null) {
            return null;
        }

        Position guess = sourceOfSound.add(getGuessOffset());
        return guess;
    }

    private Position getGuessOffset() {
        double mean = 0;
        double stdDev = 5.0;
        int guessX = (int) Distribution.normal(mean, stdDev);
        int guessY = (int) Distribution.normal(mean, stdDev);

        return new Position(guessX, guessY);
    }

    /**
     * Given the source sound and the actual position of the agent,
     * compute the direction of the source relative to the agent
     ***/
    public Direction computeDirection(Position position, Position source) {
        int x1 = position.getX();
        int y1 = position.getY();
        int x2 = source.getX();
        int y2 = source.getY();

        if (x1 < x2 && y1 < y2) {
            return Direction.SOUTHEAST;
        } else if (x1 < x2 && y1 > y2) {
            return Direction.NORTHEAST;
        } else if (x1 > x2 && y1 < y2) {
            return Direction.SOUTHWEST;
        } else if (x1 > x2 && y1 > y2) {
            return Direction.NORTHWEST;
        } else if (x1 == x2 && y1 < y2) {
            return Direction.SOUTH;
        } else if (x1 == x2 && y1 > y2) {
            return Direction.NORTH;
        } else if (x1 > x2 && y1 == y2) {
            return Direction.WEST;
        } else if (x1 < x2 && y1 == y2) {
            return Direction.EAST;
        } else return null;
    }

    private Position getSource(Position position) {
        List<Sound> soundMap = scenario.getSoundMap();
        for (Sound sound : soundMap) {
            if (sound.getPosition().equals(position)) {
                return sound.getSource();
            }
        }
        return null;
    }
}
