package nl.maastrichtuniversity.dke.areas;

public interface Area {

    boolean isHit(int x, int y);

    int getX();

    int getY();

    double getWidth();

    double getHeight();

}
