package nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.modules.noiseGeneration.SoundType;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

@Getter
public class Sound {
    private final Position position; //in which tile this sound object  sound is
    private final Position source; //it holds the original tile where the sound is coming from
    private final SoundType soundType; //whether the sound is a yell that can be recognized by other guards

    public Sound(Position position, Position source, SoundType soundType) {
        this.position = position;
        this.source = source;
        this.soundType = soundType;
    }

}
