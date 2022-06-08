package nl.maastrichtuniversity.dke.agents.modules.sound;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.agents.modules.approximation.IApproximationModule;
import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.TileType;
import nl.maastrichtuniversity.dke.scenario.util.Position;
import nl.maastrichtuniversity.dke.util.Distribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ListeningModule extends AgentModule implements IListeningModule {

    private final Environment environment;
    private IApproximationModule appriximationModule;

    public ListeningModule(Scenario scenario, IApproximationModule approximationModule) {
        super(scenario);
        this.environment = scenario.getEnvironment();
        this.appriximationModule = approximationModule;
    }

    @Override
    public List<Sound> getSounds(Position currentPosition) {
        List<Sound> soundMap = scenario.getSoundMap();

        return soundMap.stream().filter(
                sound -> sound.getPosition().equals(currentPosition)
        ).collect(Collectors.toList());
    }

    @Override
    public Position guessPositionOfSource(Sound sound) {
        Position sourceOfSound = sound.getSource();
        return appriximationModule.getValidGuess(sourceOfSound);
    }









    @Override
    public List<Double> toArray() {
        List<Double> obs = new ArrayList<>();
        for (Direction dir : getDirection(scenario.getIntruders().getCurrentAgent().getPosition())) {
            obs.addAll(getListPerSound(dir));
        }

        return obs.subList(0, Math.min(computeSoundInputSize(), obs.size()));
    }

    public int computeSoundInputSize() {
        double soundInputSize = scenario.getIntruders().getCurrentAgent().getPolicyModule().getInputSize() * 0.0194;
        return (int) Math.round(soundInputSize / 8) * 8;
    }

    public List<Double> getListPerSound(Direction direction) {
        if (direction == null) {
            return new ArrayList<>();
        }
        List<Double> list = new ArrayList<>(Collections.nCopies(Direction.values().length, 0d));
        switch (direction) {
            case NORTH -> list.set(0, 1d);
            case EAST -> list.set(1, 1d);
            case SOUTH -> list.set(2, 1d);
            case WEST -> list.set(3, 1d);
            case NORTHEAST -> list.set(4, 1d);
            case NORTHWEST -> list.set(5, 1d);
            case SOUTHEAST -> list.set(6, 1d);
            case SOUTHWEST -> list.set(7, 1d);
            default -> list.set(0, 0d);
        }
        return list;

    }

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

    @Override
    public List<Direction> getDirection(Position position) {
        List<Sound> soundMap = scenario.getSoundMap();
        List<Direction> soundSource = new ArrayList<>();

        for (Sound sound : soundMap) {
            if (sound.getPosition().equals(position) && sound.getSourceType() == SourceType.GUARD) {
                Position source = sound.getSource();
                soundSource.add(computeDirection(position, source));
            }
        }
        return soundSource;
    }

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
        } else {
            return null;
        }
    }

}
