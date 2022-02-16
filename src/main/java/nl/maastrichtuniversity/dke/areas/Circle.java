package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import nl.maastrichtuniversity.dke.util.Vector;

import java.util.List;

public class Circle extends Area {

    private @Getter final double radius;

    public Circle(double x, double y, double radius, AreaType type) {
        super( new Vector(x, y), radius*2, radius*2, type);
        this.radius = radius;
    }

    public Circle(double x, double y, double radius) {
        super( new Vector(x, y), radius*2, radius*2, AreaType.SHAPE);
        this.radius = radius;
    }

    @Override
    public boolean isCollidingWith(Area area) {
        return Collider.collides(this, area);
    }

    @Override
    public boolean containsPoint(double x, double y) {
        return getDistance(x, y) <= radius;
    }

    @Override
    public List<Vector> getPositions() {
        logger.error("getPositions() not implemented for Circle");
        return null;
    }

    private double getDistance(double x, double y) {
        return getPosition().distance(new Vector(x, y));
    }
}
