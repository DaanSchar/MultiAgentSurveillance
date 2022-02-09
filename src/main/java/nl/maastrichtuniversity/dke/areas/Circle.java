package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.util.Vector;

public class Circle implements Area{

    private @Getter @Setter Vector position; // center
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
    public double getWidth() {
        return radius * 2;
    }

    @Override
    public double getHeight() {
        return radius * 2;
    }
}
