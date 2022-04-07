package nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration;

import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public interface INoiseModule {
    void makeWalkingSound(Position position); //generates walking sound

    void makeSprintingSound(Position position); //generates sprinting sound

    void Yell(Position position); //generates yelling sound

}
