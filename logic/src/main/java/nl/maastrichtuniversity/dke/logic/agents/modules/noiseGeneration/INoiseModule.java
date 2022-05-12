package nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration;

import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface INoiseModule {
    void makeWalkingSound(Position position); // Generates walking sound

    void makeSprintingSound(Position position); // Generates sprinting sound

    void yell(Position position); // Generates yelling sound

    void makeInteractionNoise(Position position); // Generates breaking window / toggle door sound

}
