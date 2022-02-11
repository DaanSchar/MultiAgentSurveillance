package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Area {

    public Rectangle(double x1, double y1, double x2, double y2){
        super(new Vector(x1, y1), x2- x1, y2- y1);
    }

    @Override
    public boolean isColliding(Area area) {
        return Collider.collides(this, area);
    }

    @Override
    public boolean containsPoint(int x, int y) {
        //TODO: implement this
        return false;
    }

    @Override
    public List<Vector> getPositions() {

        List<Vector> positionsVector = new ArrayList<>();

        double x1 = super.getPosition().getX();
        double y1 = super.getPosition().getY();
        double x2 = super.getPosition().getX() + super.getWidth();
        double y2 = super.getPosition().getY() + super.getHeight();

        int firstPointX = (int) Math.min(x1,x2);
        int lastPointX = (int) Math.max(x1,x2);
        int firstPointY= (int) Math.min(y1,y2);
        int lastPointY= (int) Math.max(y1,y2);

        for (int i = firstPointY; i <= lastPointY; i++) {
            for (int j = firstPointX; j <= lastPointX; j++) {
                positionsVector.add(new Vector(j,i));
            }
        }

        return positionsVector;
    }
}
