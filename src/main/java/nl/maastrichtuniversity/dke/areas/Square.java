package nl.maastrichtuniversity.dke.areas;

public class Square implements Area {

    // bottom left point of the square
    private int x;
    private int y;

    private int width;
    private int height;

    public Square(int x1, int y1, int x2, int y2){
        this.x = x1;
        this.y = y1;
        this.width = x2 - x1;
        this.height = y2 - y1;
    }

    @Override
    public boolean isHit(int x, int y) {
        return isWithinXBound(x) && isWithinYBound(y);
    }

    private boolean isWithinXBound(int x) {
        return (x >= this.x && x <= this.x + this.width);
    }

    private boolean isWithinYBound(int y) {
        return (y >= this.y && y <= this.y + this.height);
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
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
