package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import nl.maastrichtuniversity.dke.util.Vector;

import java.util.List;

public class Circle extends Shape implements Area{

    private @Getter final double radius;

    public Circle(double x, double y, double radius) {
        super( new Vector(x, y), radius*2, radius*2);
        this.radius = radius;
    }

    @Override
    public boolean isColliding(Area area) {
        //TODO: implement this
        return false;
    }

    @Override
    public boolean containsPoint(int x, int y) {
        return getDistance(x, y) >= radius;
    }

    @Override
    public List<Vector> getPositions() {
        return null;
    }


    private double getDistance(double x, double y) {
        return getPosition().getDistance(new Vector(x, y));
    }
}
