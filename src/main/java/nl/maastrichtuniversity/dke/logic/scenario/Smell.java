package nl.maastrichtuniversity.dke.logic.scenario;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class Smell {
        private  @Getter @Setter Position source;
        private  @Getter @Setter Position position;
        private  @Getter @Setter Agent agentSource;

        public Smell(Position position, Position source,Agent agentSource){
            this.source = source;
            this.position = position;
            this.agentSource = agentSource;
        }
}
