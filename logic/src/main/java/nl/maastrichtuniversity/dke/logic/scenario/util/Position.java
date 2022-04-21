package nl.maastrichtuniversity.dke.logic.scenario.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;

import java.lang.Math;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position getPosInDirection(Position position, Direction direction) {
        return new Position(position, direction);
    }

    private Position(Position position, Direction direction) {
        this.x = position.x + direction.getMoveX();
        this.y = position.y + direction.getMoveY();
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

    public double distance(Position other) {
        return Math.abs(Math.sqrt((other.y - this.y) * (other.y - this.y) + (other.x - this.x) * (other.x - this.x)));
    }
}
