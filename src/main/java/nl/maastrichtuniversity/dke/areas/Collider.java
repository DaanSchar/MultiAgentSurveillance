package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.Vector;

public class Collider {


    public static boolean isColliding(Area a1, Area a2) {
        if (a1 instanceof Circle)

            if (a2 instanceof Circle)
                return isColliding((Circle) a1, (Circle) a2);
            else if (a2 instanceof Rectangle)
                return isColliding((Circle) a1, (Rectangle) a2);

        if (a1 instanceof Rectangle)

            if (a2 instanceof Rectangle)
                return isColliding((Rectangle) a1, (Rectangle) a2);
            else if (a2 instanceof Circle)
                return isColliding((Circle) a2, (Rectangle) a1);

        return false;
    }

    // Circle-Circle
    private static boolean isColliding(Circle c1, Circle c2) {
        Vector positionC1 = c1.getPosition();
        Vector positionC2 = c2.getPosition();
        double distance = positionC1.getDistance(positionC2);

        return distance < c1.getWidth() + c2.getWidth();
    }

    // Rectangle-Rectangle
    private static boolean isColliding(Rectangle r1, Rectangle r2) {
        boolean xCollision = r1.getPosition().getX() < r2.getPosition().getX() + r2.getWidth();
        boolean yCollision = r1.getPosition().getY() < r2.getPosition().getY() + r2.getHeight();

        return xCollision && yCollision;
    }

    // Circle-Rectangle
    private static boolean isColliding(Circle c, Rectangle r) {
        // TODO: implement this method
        return false;
    }

}
