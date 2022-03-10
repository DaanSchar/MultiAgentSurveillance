package nl.maastrichtuniversity.dke.agents.modules.listening;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Sound;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.ArrayList;
import java.util.List;

public class ListeningModule extends AgentModule implements IListeningModule {
    private final Environment environment;

    public ListeningModule(Scenario scenario) {
        super(scenario);
        environment = scenario.getEnvironment();
    }

    /**
     *
     * @param position the position in which the agent is right now
     * @return a boolean representing whether there is a sound in that position
     */
    @Override
    public boolean getSound(Position position){
        List<Sound> soundMap = scenario.getSoundMap();
        for(Sound sound: soundMap){
            if(sound.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param position the position in which the agent is right now
     * @return a list of positions, the position is where the sound is coming from (the sound source),
     * there can be more than one sound in the agents position
     */
    @Override
    public List<Position> getDirection(Position position){
        List<Sound> soundMap = scenario.getSoundMap();
        List<Position> soundSource = new ArrayList<>();
        for(Sound sound: soundMap){
            if(sound.getPosition().equals(position)){
                Position source = sound.getSource();
                soundSource.add(deviation(source));
            }
        }
        return  soundSource;
    }

    /**
     * Creates a deviation from the actual source of the sound
     ***/
    public Position deviation(Position position){
        int X = position.getX();
        int Y = position.getY();
        int deviationX = getRandomNumber(-2,2);
        int deviationY = getRandomNumber(-2,2);
        return new Position(X+deviationX,Y+deviationY);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
