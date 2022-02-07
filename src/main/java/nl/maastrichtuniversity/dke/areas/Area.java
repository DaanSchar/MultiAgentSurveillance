package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

public interface Area {

    boolean isHit(Area area);

    Vector getPosition();

    double getWidth();

    double getHeight();

}
