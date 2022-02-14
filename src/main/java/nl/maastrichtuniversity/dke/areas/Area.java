package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.maastrichtuniversity.dke.util.DebugSettings;
import nl.maastrichtuniversity.dke.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ToString
public abstract class Area {

    private static int idCount = 0;
    private @Getter int id;

    private @Getter @Setter Vector position;
    private final @Getter double height;
    private final @Getter double width;

    protected static Logger logger = LoggerFactory.getLogger(Area.class);

    public Area(Vector position, double width, double height) {
        this.position = position;
        this.height = height;
        this.width = width;
        this.id = idCount++;

        if (DebugSettings.AREA_DEBUG) logger.info("Created " + this.getClass().getSimpleName() + " " + this);
    }

    public void translate(Vector vector) {
        this.position = this.position.add(vector);
    }

    public abstract boolean isCollidingWith(Area area);

    public abstract boolean containsPoint(double x, double y);

    public abstract List<Vector> getPositions();

}
