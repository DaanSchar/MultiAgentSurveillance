package nl.maastrichtuniversity.dke.logic.agents.modules.communication;

import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class CommunicationMark {

    private Position position;

    public CommunicationMark(Position position, CommunicationType type) {
        this.position=position;
    }

    public Position getPosition() { return position; }
    public CommunicationType getType() { return type; }
    public Color getColor() { return color; }
}