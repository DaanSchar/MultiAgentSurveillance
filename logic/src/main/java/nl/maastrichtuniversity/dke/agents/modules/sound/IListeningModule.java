package nl.maastrichtuniversity.dke.agents.modules.sound;

import nl.maastrichtuniversity.dke.agents.util.Direction;
import nl.maastrichtuniversity.dke.scenario.Sound;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.util.List;

public interface IListeningModule {

    /**
     * Returns all sounds hear-able at the given position.
     *
     * @param position The position to check.
     * @return A list of sounds.
     */
    List<Sound> getSounds(Position position);

    /**
     * approximates the sound source of the sound given.
     *
     * @param sound the sound that is heard (by an agent)
     * @return an approximation of the position of the sound source.
     */
    Position guessPositionOfSource(Sound sound);





    /**
     * @param position the position in which the agent is right now
     * @return a boolean representing whether there is a sound in that position
     */
    @Deprecated
    boolean getSound(Position position);

    List<Double> toArray();

    /**
     * @param position the position in which the agent is right now
     * @return a list of directions, the direction is where the sound is coming from (the sound source),
     * there can be more than one sound in the agents position
     */
    @Deprecated
    List<Direction> getDirection(Position position);

}
