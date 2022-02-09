package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.util.Vector;

import java.util.Arrays;
import java.util.Objects;

public class Polygon {

    private @Getter @Setter Vector position;
    private @Getter @Setter Vector[] vertices;

    public Polygon(double x, double y, Vector... vertices) {
        this.position = new Vector(x, y);
        this.vertices = vertices;
    }

    public Polygon(Vector... vertices) {
        this.position = new Vector(0, 0);
        this.vertices = vertices;
    }

    public boolean isColliding(Polygon other) {
        if (other.equals(this))
            return true;
        return new Collider(this, other).isHit();
    }

    /**
     * returns the furthest point (vertex) in a given direction.
     * Used in GJK algorithm.
     */
    public Vector getFurthestPoint(Vector direction) {
        double max;
        int index;
        double dot; // dot product of the direction and the vector

        max = Double.MIN_VALUE;
        index = 0;
        direction = direction.unit();

        for (int i = 0; i < vertices.length; i++) {
            dot = Vector.dot(vertices[i], direction);
            if (dot > max) { max = dot; index = i; }
        }

        return vertices[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        return position.equals(polygon.position) && Arrays.equals(vertices, polygon.vertices);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(position);
        result = 31 * result + Arrays.hashCode(vertices);
        return result;
    }

    public void move(Vector direction) {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = vertices[i].add(direction);
        }
        this.position = this.position.add(direction);
    }

}
