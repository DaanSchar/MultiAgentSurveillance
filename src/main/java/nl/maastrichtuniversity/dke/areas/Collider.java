package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * GJK algorithm for collision detection between 2 polynomials.
 * source: https://www.youtube.com/watch?v=ajv46BSqcK4
 *
 * @Author Daan
 */

public class Collider {

    public Polygon p1;
    public Polygon p2;
    private Vector direction;
    private List<Vector> simplex;

    private static final Vector O = new Vector(0, 0);

    public Collider(Polygon p1, Polygon p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Polygon getSimplex() {
        return new Polygon(simplex.get(0), simplex.get(1), simplex.get(2));
    }

    public boolean isHit() {
        init();

        while (true) {
            Vector a = support(p1, p2, direction);

            if (a.dot(direction) < 0)
                return false;

            simplex.add(a);

            if (handleSimplex())
                return true;
        }
    }

    private void init() {
        direction = p2.getPosition().sub(p1.getPosition()).norm(); // initial direction vector;
        simplex = new ArrayList<>();
        simplex.add( support(p1, p2, direction) );
        direction = simplex.get(0).mul(-1).norm();
    }

    /**
     * returns the support vertex of the Minkowski difference of the two polynomials.
     * sC(d) = sA(d) - sB(-d)
     */
    private Vector support(Polygon p1, Polygon p2, Vector d) {
        Vector dNeg; // -d
        Vector furthestPoint1; // furthest point in direction d of p1
        Vector furthestPoint2; // furthest point in direction -d of p2

        dNeg = d.mul(-1);
        furthestPoint1 = p1.getSupportVertex(d);
        furthestPoint2 = p2.getSupportVertex(dNeg);

        return furthestPoint1.sub(furthestPoint2);
    }

    private boolean handleSimplex() {
        if (simplex.size() == 2)
            return lineCase();
        return triangleCase();
    }

    private boolean lineCase() {
        Vector a, b; // vertices from simplex, where A is the lastly added vertex
        Vector ab, ao; // vector from a to b | vector from a to origin
        Vector abPerp; // vector perpendicular to ab

        a = simplex.get(1);
        b = simplex.get(0);
        ab = b.sub(a);
        ao = O.sub(a);
        abPerp = ab.cross(ao).cross(ab); // triple cross product

        this.direction = abPerp.norm();

        return false;
    }

    public boolean triangleCase() {
        Vector a, b, c; // vertices from simplex, where A is the lastly added vertex
        Vector ab, ac, ao; // vectors from A to B,  A to C  and  A to origin
        Vector abPerp, acPerp; // vectors perpendicular to ab and ac

        a = simplex.get(2);
        b = simplex.get(1);
        c = simplex.get(0);
        ab = b.sub(a);
        ac = c.sub(a);
        ao = O.sub(a);
        abPerp = ac.cross(ab).cross(ab).norm();
        acPerp = ab.cross(ac).cross(ac).norm();

        if (abPerp.dot(ao) > 0) {
            simplex.remove(0);
            this.direction = abPerp.norm();
            return false;
        } else if (acPerp.dot(ao) > 0) {
            simplex.remove(1);
            this.direction = acPerp.norm();
            return false;
        }

        return true;
    }



}
