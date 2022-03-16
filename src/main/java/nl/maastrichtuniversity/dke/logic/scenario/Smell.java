package nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class Smell {
        private  @Getter @Setter Position source;
        private  @Getter @Setter Position position;

        public Smell(Position position, Position source){
            this.source = source;
            this.position = position;
        }
}
