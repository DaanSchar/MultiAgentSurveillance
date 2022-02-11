package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

import java.util.List;

public interface Area {

    boolean isColliding(Area area);

    Vector getPosition();

    void setPosition(Vector position);

    double getWidth();

    double getHeight();

    boolean containsPoint(int x, int y);

    List<Vector> getPositions();

}
