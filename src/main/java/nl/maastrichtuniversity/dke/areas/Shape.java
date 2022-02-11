package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.util.Vector;

public class Shape{

    private @Getter @Setter Vector position;
    private final @Getter double height;
    private final @Getter double width;

    public Shape(Vector position, double width, double height) {
        this.position = position;
        this.height = height;
        this.width = width;
    }

}
