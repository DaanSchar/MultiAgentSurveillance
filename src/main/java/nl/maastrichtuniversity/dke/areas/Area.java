package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

public interface Area {

    boolean isColliding(Polygon polygon);

    Vector getPosition();

    void setPosition(Vector position);

    double getWidth();

    double getHeight();

}
