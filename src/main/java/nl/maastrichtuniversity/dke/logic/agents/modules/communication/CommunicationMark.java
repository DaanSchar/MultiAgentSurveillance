package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import nl.maastrichtuniversity.dke.logic.scenario.util.Position;
import java.awt.Color;

public class CommunicationMark {

    private Position position;
    private final CommunicationType type;
    private Color color;

    public CommunicationMark(Position position, CommunicationType type) {
        this.position=position;
        this.type = type;
    }

    public Position getPosition() { return position; }
    public CommunicationType getType() { return type; }
    public Color getColor() { return color; }
}