package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import nl.maastrichtuniversity.dke.util.Vector;

public class Circle extends Polygon implements Area{

    private static final int FIDELITY = 50; // total vertices representing the circle.

    private @Getter final double radius;

    public Circle(double x, double y, double radius) {
        super(x, y);
        this.radius = radius;
        super.setVertices(getVerticesApprox());
    }

    @Override
    public double getWidth() {
        return radius * 2;
    }

    @Override
    public double getHeight() {
        return radius * 2;
    }

    /**
     * approximates the circle with a polygon.
     * @return array of vertices representing the circle polygon.
     */
    private Vector[] getVerticesApprox() {

        Vector[] vertices = new Vector[FIDELITY];

        for (int i = 0; i < FIDELITY; i++) {
            double angle = i * 2 * Math.PI / FIDELITY;
            double x = super.getPosition().getX() + radius * Math.cos(angle);
            double y = super.getPosition().getY() + radius * Math.sin(angle);
            vertices[i] = new Vector(x, y);
        }

        return vertices;
    }
}
