package util;

import nl.maastrichtuniversity.dke.util.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VectorTest {

    /**
     * Test of magnitude method, of class Vector.
     * @author Daan
     */

    // -------------------------------------------- cross product -------------------------------------------- \\

    @Test
    public void cross1() {
        var v1 = new Vector(1, 2, 3);
        var v2 = new Vector(1, 5, 7);

        var result = v1.cross(v2);
        var actual = new Vector(-1, -4, 3);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void cross2() {
        var v1 = new Vector(5, -32, 53);
        var v2 = new Vector(0, 2, 107);

        var result = v1.cross(v2);
        var actual = new Vector(-3530, -535, 10);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void cross3() {
        var v1 = new Vector(2, -2, 0);
        var v2 = new Vector(5, 3, 1);

        var result = v1.cross(v2);
        var actual = new Vector(-2, -2, 16);

        Assertions.assertEquals(actual, result);
    }

    // -------------------------------------------- dot product -------------------------------------------- \\

    @Test
    public void dot1() {
        var v1 = new Vector(1, 2, 3);
        var v2 = new Vector(1, 5, 7);

        var result = v1.dot(v2);
        var actual = 32;

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void dot2() {
        var v1 = new Vector(2, -2, 0);
        var v2 = new Vector(5, 3, 1);

        var result = v1.dot(v2);
        var actual = 4;

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void dot3() {
        var v1 = new Vector(100, -301, 0);
        var v2 = new Vector(37, 20, 0);

        var result = v1.dot(v2);
        var actual = -2320;

        Assertions.assertEquals(actual, result);
    }

    // -------------------------------------------- vector add/sub -------------------------------------------- \\

    @Test
    public void vectorAdd1() {
        var v1 = new Vector(50, 30, 10);
        var v2 = new Vector(20, 10, 5);

        var result = v1.add(v2);
        var actual = new Vector(70, 40, 15);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void vectorAdd2() {
        var v1 = new Vector(102, 1, 5);
        var v2 = new Vector(-100, -5, 21);

        var result = v1.add(v2);
        var actual = new Vector(2, -4, 26);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void vectorSub1() {
        var v1 = new Vector(100, -301, 2);
        var v2 = new Vector(20, 10, 5);

        var result = v1.sub(v2);
        var actual = new Vector(80, -311, -3);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void vectorSub2() {
        var v1 = new Vector(10, 11, 12);
        var v2 = new Vector(10, 11, 12);

        var result = v1.sub(v2);
        var actual = new Vector(0, 0, 0);

        Assertions.assertEquals(actual, result);
    }


    // -------------------------------------------- scalar -------------------------------------------- \\

    @Test
    public void multiply1() {
        var v1 = new Vector(100, -301, 2);
        var scalar = -1;

        var result = v1.mul(scalar);
        var actual = new Vector(-100, 301, -2);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void multiply2() {
        var v1 = new Vector(100, -301, 2);
        var scalar = 3;

        var result = v1.mul(scalar);
        var actual = new Vector(300, -903, 6);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void add1() {
        var v1 = new Vector(100, -301, 2);
        var scalar = 3;

        var result = v1.add(scalar);
        var actual = new Vector(103, -298, 5);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void add2() {
        var v1 = new Vector(100, -301, 2);
        var scalar = 205;

        var result = v1.add(scalar);
        var actual = new Vector(305, -96, 207);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void subtract1() {
        var v1 = new Vector(50, 30, 10);
        var scalar = 20;

        var result = v1.sub(scalar);
        var actual = new Vector(30, 10, -10);

        Assertions.assertEquals(actual, result);
    }

    @Test
    public void subtract2() {
        var v1 = new Vector(9, 1, 0);
        var scalar = 17;

        var result = v1.sub(scalar);
        var actual = new Vector(-8, -16, -17);

        Assertions.assertEquals(actual, result);
    }


    // -------------------------------------------- other -------------------------------------------- \\

    @Test
    public void angle() {
        var v1 = new Vector(1, 0);
        var v2 = new Vector(0, 1);
        var v3 = new Vector(1, 1);
        var v4 = new Vector(1, -1);
        var v5  = new Vector(-1, -1);

        Assertions.assertEquals(0, (int) v1.angle());
        Assertions.assertEquals(90, (int) v2.angle());
        Assertions.assertEquals(45, (int) v3.angle());
        Assertions.assertEquals(-45, (int) v4.angle());
        Assertions.assertEquals(-135, (int) v5.angle());

        Assertions.assertEquals(90, (int) v1.angle(v2));
        Assertions.assertEquals(90, (int) v2.angle(v1));

        Assertions.assertEquals(45, (int) v1.angle(v3));
        Assertions.assertEquals(45, (int) v3.angle(v1));

        Assertions.assertEquals(45, (int) v1.angle(v4));
        Assertions.assertEquals(45, (int) v4.angle(v1));
    }

    @Test
    public void length() {
        var v1 = new Vector(1, 0);
        var v2 = new Vector(0, 1);
        var v3 = new Vector(3, 4);
        var v4 = new Vector(300, 400);

        Assertions.assertEquals(1, v1.length());
        Assertions.assertEquals(1, v2.length());
        Assertions.assertEquals(5, v3.length());
        Assertions.assertEquals(500, v4.length());
    }

    @Test
    public void norm() {
        var v1 = new Vector(3, 0);
        var v2 = new Vector(25, 31);
        var v3 = new Vector(-100, 250);

        Assertions.assertEquals(1, (int) v1.norm().length());
        Assertions.assertEquals(1, (int) v2.norm().length());
        Assertions.assertEquals(1, (int) v3.norm().length());

        var v4 = new Vector(0, 0);

        Assertions.assertEquals(0, (int) v4.norm().length());
        Assertions.assertEquals(0, (int) v4.norm().getX());
        Assertions.assertEquals(0, (int) v4.norm().getY());
        Assertions.assertFalse(Double.isNaN(v4.norm().getX()));
        Assertions.assertFalse(Double.isNaN(v4.norm().getY()));
    }

    @Test
    public void rotate() {
        var v1 = new Vector(1, 0);
        v1 = v1.rotate(90);

        Assertions.assertEquals(0, (int) v1.getX());
        Assertions.assertEquals(1, (int) v1.getY());
        Assertions.assertEquals(90, (int) v1.angle());

        v1 = v1.rotate(90);

        Assertions.assertEquals(-1, (int) v1.getX());
        Assertions.assertEquals(0, (int) v1.getY());
        Assertions.assertEquals(180, (int) v1.angle());

    }


}
