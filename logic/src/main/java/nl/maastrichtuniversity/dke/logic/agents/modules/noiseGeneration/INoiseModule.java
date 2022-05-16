package nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration;

import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface INoiseModule {

    void makeWalkingSound(Position position); // Generates walking sound

    void makeSprintingSound(Position position); // Generates sprinting sound

    void yell(Position position); // Generates yelling sound

    void makeInteractionNoise(Position position); // Generates breaking window / toggle door sound

    /**
     * Generates a noise at the given position.
     * @param position The position of the source of the noise.
     * @param soundType The type of sound to generate.
     **/
    void makeSound(Position position, SoundType soundType);

}
