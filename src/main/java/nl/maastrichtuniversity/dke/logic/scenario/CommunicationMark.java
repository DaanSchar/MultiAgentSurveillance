package nl.maastrichtuniversity.dke.logic.scenario;


import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

import java.awt.*;

public class CommunicationMark {

    private Color color;
    private Position position;

    public CommunicationMark(Position position,Color color) {
        this.color=color;
        this.position=position;
    }
    public Position getPosition() {
        return position;
    }
    public Color getColor(){
        return color;
    }
}
