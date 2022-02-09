package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

public interface Area {

    boolean isHit(Area area);

    Vector getPosition();

    void setPosition(Vector position);

    double getWidth();

    double getHeight();

}
