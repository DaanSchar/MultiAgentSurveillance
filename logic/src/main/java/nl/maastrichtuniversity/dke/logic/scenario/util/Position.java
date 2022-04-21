package nl.maastrichtuniversity.dke.logic.scenario.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.maastrichtuniversity.dke.logic.agents.util.Direction;

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

    public Position getPosInDirection(Direction direction) {
        return new Position(
                this.x + direction.getMoveX(),
                this.y + direction.getMoveY()
        );
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
