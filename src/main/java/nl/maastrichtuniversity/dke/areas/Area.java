package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

import java.util.List;

public interface Area {

    boolean isHit(Area area);

    Vector getPosition();

    double getWidth();

    double getHeight();

    List<Vector> getPositions();

}
