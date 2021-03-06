package nl.maastrichtuniversity.dke.agents.modules.communication;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.scenario.util.Position;

import java.awt.Color;

@Getter
@EqualsAndHashCode
public class Mark {

    private final Position position;
    private final CommunicationType type;
    private final Agent agentSource;
    private Color color;

    public Mark(Position position, CommunicationType type, Agent agent) {
        this.position = position;
        this.type = type;
        this.agentSource = agent;
    }

}
