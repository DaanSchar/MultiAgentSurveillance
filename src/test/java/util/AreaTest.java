package util;

import nl.maastrichtuniversity.dke.areas.Area;
import nl.maastrichtuniversity.dke.areas.Circle;
import nl.maastrichtuniversity.dke.areas.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AreaTest {

    @Test
    public void testCreateRectangle() {
        Area a = new Rectangle(100, 200, 300, 400);

        Assertions.assertEquals(100, a.getPosition().getX());
        Assertions.assertEquals(200, a.getPosition().getY());
        Assertions.assertEquals(200, a.getWidth());
        Assertions.assertEquals(200, a.getHeight());
    }

    @Test
    public void testCreateCircle() {
        Area a = new Circle(100, 200, 300);

        Assertions.assertEquals(100, a.getPosition().getX());
        Assertions.assertEquals(200, a.getPosition().getY());
        Assertions.assertEquals(600, a.getWidth());
        Assertions.assertEquals(600, a.getHeight());
    }

    @Test
    public void testPointInRectangle() {
        Area a = new Rectangle(100, 200, 300, 400);

        Assertions.assertTrue(a.containsPoint(200, 300));
        Assertions.assertFalse(a.containsPoint(50, 400));
        Assertions.assertFalse(a.containsPoint(200, 50));
        Assertions.assertFalse(a.containsPoint(400, 300));
        Assertions.assertFalse(a.containsPoint(700, 100));
        Assertions.assertTrue(a.containsPoint(300, 300));
        Assertions.assertTrue(a.containsPoint(273, 399));

        // edge cases
        Assertions.assertTrue(a.containsPoint(100, 200));
        Assertions.assertTrue(a.containsPoint(100, 400));
        Assertions.assertTrue(a.containsPoint(200, 200));
        Assertions.assertTrue(a.containsPoint(200, 400));
    }

    @Test
    public void testPointInCircle() {
        Area a = new Circle(100, 200, 300);

        Assertions.assertTrue(a.containsPoint(100, 200));

        Assertions.assertTrue(a.containsPoint(235, 319));
        Assertions.assertTrue(a.containsPoint(-50, 12));
        Assertions.assertTrue(a.containsPoint(-100, 159));
        Assertions.assertTrue(a.containsPoint(0, 0));
        Assertions.assertFalse(a.containsPoint(500, 600));
        Assertions.assertTrue(a.containsPoint(-200, 200));
        Assertions.assertFalse(a.containsPoint(-200, 201));
        Assertions.assertFalse(a.containsPoint(400, 300));
        Assertions.assertFalse(a.containsPoint(43, 900));
    }

    @Test
    public void testCollisionRectangleOnRectangle() {
        Area a = new Rectangle(0, 0, 10, 10);
        Area b = new Rectangle(5, 5, 15, 15);
        Area c = new Rectangle(100, 100, 110, 110);
        Area d = new Rectangle(15, 10, 25, 20);

        // a and a
        Assertions.assertTrue(a.isCollidingWith(a));

        // a and b
        Assertions.assertTrue(a.isCollidingWith(b));
        Assertions.assertTrue(b.isCollidingWith(a));

        // a and c
        Assertions.assertFalse(a.isCollidingWith(c));
        Assertions.assertFalse(c.isCollidingWith(a));

        // b and c
        Assertions.assertFalse(b.isCollidingWith(c));
        Assertions.assertFalse(c.isCollidingWith(b));

        // a and d
        Assertions.assertFalse(a.isCollidingWith(d));
        Assertions.assertFalse(d.isCollidingWith(a));

        // b and d
        Assertions.assertFalse(b.isCollidingWith(d));
        Assertions.assertFalse(d.isCollidingWith(b));
    }

    @Test
    public void testCollisionCircleCircle() {
        Area a = new Circle(0, 0, 10);
        Area b = new Circle(5, 5, 15);
        Area c = new Circle(100, 100, 110);
        Area d = new Circle(15, 10, 5);

        // a and a
        Assertions.assertTrue(a.isCollidingWith(a));

        // a and b
        Assertions.assertTrue(a.isCollidingWith(b));
        Assertions.assertTrue(b.isCollidingWith(a));

        // a and d
        Assertions.assertFalse(a.isCollidingWith(d));
        Assertions.assertFalse(d.isCollidingWith(a));

        // b and d
        Assertions.assertTrue(b.isCollidingWith(d));
        Assertions.assertTrue(d.isCollidingWith(b));

        // a and c
        Assertions.assertFalse(a.isCollidingWith(c));
        Assertions.assertFalse(c.isCollidingWith(a));

        // b and c
        Assertions.assertFalse(b.isCollidingWith(c));
        Assertions.assertFalse(c.isCollidingWith(b));
    }

    @Test
    public void testCollisionRectangleCircle() {
        Area a = new Rectangle(0, 0, 10, 10);
        Area b = new Rectangle(13, 13, 23, 23);
        Area c = new Circle(10, 10, 15);
        Area d = new Circle(40, 40, 5);

        // a and a
        Assertions.assertTrue(a.isCollidingWith(a));

        // a and c
        Assertions.assertTrue(a.isCollidingWith(c));
        Assertions.assertTrue(c.isCollidingWith(a));

        // a and d
        Assertions.assertFalse(a.isCollidingWith(d));
        Assertions.assertFalse(d.isCollidingWith(a));

        // b and c
        Assertions.assertTrue(b.isCollidingWith(c));
        Assertions.assertTrue(c.isCollidingWith(b));

        // b and d
        Assertions.assertFalse(b.isCollidingWith(d));
        Assertions.assertFalse(d.isCollidingWith(b));
    }


}
