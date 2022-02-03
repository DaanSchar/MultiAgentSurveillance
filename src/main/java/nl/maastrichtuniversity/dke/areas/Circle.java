package nl.maastrichtuniversity.dke.areas;

public class Circle implements Area{

    // center position of the circle
    private int x;
    private int y;
    private double radius;

    public Circle(int x, int y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public boolean isHit(int x, int y) {
        return getDistance(x, y) < radius;
    }

    private double getDistance(int x, int y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
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
