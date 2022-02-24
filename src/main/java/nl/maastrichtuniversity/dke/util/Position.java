package nl.maastrichtuniversity.dke.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.lang.Math;

@Getter
@Setter
@ToString
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
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

    public double distance(Position other){
        double distance = Math.abs(Math.sqrt((other.y - this.y) * (other.y - this.y) + (other.x - this.x) * (other.x - this.x)));
        return  distance;
    }

    @Override
    public boolean equals(Object o){

        if (getClass() != o.getClass())
            return false;

        Position other = (Position) o;
        return x==other.getX() && y== other.getY();
    }

    // added cause had error in CodeFactor with just having equals method
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
