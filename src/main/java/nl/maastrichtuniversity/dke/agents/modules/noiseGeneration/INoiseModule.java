package nl.maastrichtuniversity.dke.agents.modules.noiseGeneration;

import nl.maastrichtuniversity.dke.util.Position;

import java.util.Vector;

public interface INoiseModule {
    public void makeWalkingSound(Position position); //generates walking sound
    public void makeSprintingSound(Position position); //generates sprinting sound

}
