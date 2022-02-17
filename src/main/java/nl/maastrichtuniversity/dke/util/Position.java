package nl.maastrichtuniversity.dke.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position add(Position other) {
        return new Position(
                this.x + other.getX(),
                this.y + other.getY()
        );
    }

    public Position sub(Position other) {
        return new Position(
                this.x - other.getX(),
                this.y - other.getY()
        );
    }
}
