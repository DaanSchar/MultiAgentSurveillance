package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

import java.util.List;

public interface Area {

    boolean isColliding(Polygon polygon);

    Vector getPosition();

    void setPosition(Vector position);

    double getWidth();

    double getHeight();

    List<Vector> getPositions();

}
