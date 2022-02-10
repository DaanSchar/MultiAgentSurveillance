package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import nl.maastrichtuniversity.dke.util.Vector;

import java.util.ArrayList;
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

        List<Vector> positionsVector = new ArrayList<>();

        double x1 = super.getVertices()[0].getX();
        double y1 = super.getVertices()[0].getY();
        double x2 = super.getVertices()[2].getX();
        double y2 = super.getVertices()[2].getY();

        int firstpointx = (int) Math.min(x1,x2);
        int lastpointx = (int) Math.max(x1,x2);
        int firstpointy= (int) Math.min(y1,y2);
        int lsatpointy= (int) Math.max(y1,y2);
        for (int i = firstpointy; i<=lsatpointy ;i++ ) {
            for (int j =firstpointx;j<=lastpointx ;j++ ) {
                positionsVector.add(new Vector(j,i));
            }
        }
        return positionsVector;
    }
}
