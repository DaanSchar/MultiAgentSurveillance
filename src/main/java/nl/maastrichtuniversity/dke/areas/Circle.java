package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.Vector;

public class Circle implements Area{

    // center position of the circle
    private Vector position;
    private double radius;

    public Circle(int x, int y, double radius) {
        this.position = new Vector(x, y);
        this.radius = radius;
    }

    @Override
    public boolean isHit(Area area) {
        return Collider.isColliding(this, area);
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public double getWidth() {
        return radius * 2;
    }

    @Override
    public double getHeight() {
        return radius * 2;
    }
}
