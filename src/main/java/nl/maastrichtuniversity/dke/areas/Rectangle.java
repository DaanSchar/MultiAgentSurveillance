package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Rectangle implements Area {

    // bottom left point of the square
    private Vector position;

    private int width;
    private int height;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Rectangle(int x1, int y1, int x2, int y2){
        this.position = new Vector(x1, y1);
        this.width = x2 - x1;
        this.height = y2 - y1;
        this.x1 =x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
    }

    @Override
    public boolean isHit(Area area) {
        return Collider.isColliding(this, area);
    }


    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public List<Vector> getPositions() {

        List<Vector> positionsVector = new ArrayList<>();

        int firstpointx = Math.min(x1,x2);
        int lastpointx = Math.max(x1,x2);
        int firstpointy=Math.min(y1,y2);
        int lsatpointy=Math.max(y1,y2);
        for (int i = firstpointy; i<=lsatpointy ;i++ ) {
            for (int j =firstpointx;j<=lastpointx ;j++ ) {
                positionsVector.add(new Vector(j,i));
            }
        }
        return positionsVector;
    }
}
