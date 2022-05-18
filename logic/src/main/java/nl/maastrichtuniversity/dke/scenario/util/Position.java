package nl.maastrichtuniversity.dke.scenario.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.maastrichtuniversity.dke.agents.util.Direction;

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

    public Position(Position p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public Position getPosInDirection(Direction direction) {
        return new Position(
                this.x + direction.getMoveX(),
                this.y + direction.getMoveY()
        );
    }

    public Position getPosInDirection(Direction direction, int distance) {
        int new_x = this.x;
        int new_y = this.y;
        for (int i =0;i<distance;i++) {
            new_x = new_x + direction.getMoveX();
            new_y = new_y +direction.getMoveY();
        }
        return new Position(new_x,new_y);
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
