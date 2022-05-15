package nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration.SoundType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

@Getter
public class Sound {
    private final Position position;
    private final Position source;
    private final SoundType soundType;

    public Sound(Position position, Position source, SoundType soundType) {
        this.position = position;
        this.source = source;
        this.soundType = soundType;
    }

}
