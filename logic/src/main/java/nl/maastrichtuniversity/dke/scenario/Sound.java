package nl.maastrichtuniversity.dke.scenario;

import lombok.Getter;
import nl.maastrichtuniversity.dke.agents.modules.sound.SoundType;
import nl.maastrichtuniversity.dke.agents.modules.sound.SourceType;
import nl.maastrichtuniversity.dke.scenario.util.Position;

@Getter
public class Sound {
    private final Position position;
    private final Position source;
    private final SoundType soundType;
    private final SourceType sourceType;

    public Sound(Position position, Position source, SoundType soundType, SourceType sourceType) {
        this.position = position;
        this.source = source;
        this.soundType = soundType;
        this.sourceType = sourceType;
    }

}
