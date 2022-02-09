package nl.maastrichtuniversity.dke.areas;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.util.Vector;

public class Rectangle implements Area {

    private @Getter @Setter Vector position; // bottom left

    private int width;
    private int height;

    public Rectangle(int x1, int y1, int x2, int y2){
        this.position = new Vector(x1, y1);
        this.width = x2 - x1;
        this.height = y2 - y1;
    }

    @Override
    public boolean isHit(Area area) {
        return Collider.isColliding(this, area);
    }


    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
