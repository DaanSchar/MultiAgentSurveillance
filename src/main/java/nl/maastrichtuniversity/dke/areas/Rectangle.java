package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import nl.maastrichtuniversity.dke.util.Vector;

import java.util.List;

public class Rectangle extends Polygon implements Area {

    private @Getter final double width;
    private @Getter final double height;

    public Rectangle(double x1, double y1, double x2, double y2){
        super(x1 + (x2-x1)/2.0, y1 + (y2-y1)/2.0);
        super.setVertices(new Vector[]{
                new Vector(x1, y1),
                new Vector(x2, y1),
                new Vector(x2, y2),
                new Vector(x1, y2)
        });
        this.width = x2 - x1;
        this.height = y2 - y1;
    }


    @Override
    public List<Vector> getPositions() {
        return null;
    }
}
