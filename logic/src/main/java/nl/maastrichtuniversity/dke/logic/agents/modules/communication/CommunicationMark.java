package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.maastrichtuniversity.dke.logic.agents.Agent;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.awt.Color;

@Getter
@EqualsAndHashCode
public class CommunicationMark {

    private final Position position;
    private final CommunicationType type;
    private final Agent agentSource;
    private Color color;

    public CommunicationMark(Position position, CommunicationType type, Agent agent, Color color) {
        this.position = position;
        this.type = type;
        this.agentSource = agent;
        this.color = color;
    }


}