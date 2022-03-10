package nl.maastrichtuniversity.dke.discrete;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.util.Position;

public class Sound {
    private  @Getter @Setter Position source; //it holds the original tile where the sound is coming from
    private  @Getter @Setter Position position; //in which tile this sound object  sound is
    private  @Getter @Setter boolean isYell; //whether the sound is a yell that can be recognized by other guards

    public Sound(Position position, Position source, boolean isYell){
        this.source = source;
        this.position = position;
        this.isYell = isYell;
    }

}
