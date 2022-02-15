package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Area {


    public Rectangle(double x1, double y1, double x2, double y2){
        super(new Vector(x1, y1), x2- x1, y2- y1);
    }

    @Override
    public boolean isCollidingWith(Area area) {
        return Collider.collides(this, area);
    }

    @Override
    public boolean containsPoint(double x, double y) {
        double x1 = super.getPosition().getX();
        double y1 = super.getPosition().getY();
        double x2 = super.getPosition().getX() + super.getWidth();
        double y2 = super.getPosition().getY() + super.getHeight();

        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2))
            return y >= Math.min(y1, y2) && y <= Math.max(y1, y2);

        return false;
    }

    @Override
    public List<Vector> getPositions() {
        List<Vector> positionsVector = new ArrayList<>();

        for (int i = 0; i <= getWidth(); i++) {
            for (int j = 0; j <= getHeight(); j++) {
                positionsVector.add(
                        new Vector(
                                i + super.getPosition().getX(),
                                j + super.getPosition().getY())
                );
            }
        }

        return positionsVector;
    }
}
