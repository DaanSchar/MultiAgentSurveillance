package nl.maastrichtuniversity.dke.areas;

import nl.maastrichtuniversity.dke.util.DebugSettings;
import nl.maastrichtuniversity.dke.util.Vector;

import java.util.logging.Logger;


/**
 * Collision handling class for rectangles and circles.
 *
 *
 * source for circle-rectangle : https://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
 *
 * @Author Daan
 */

public class Collider {

    private static Logger logger = Logger.getLogger(Collider.class.getName());

    public static boolean collides(Area area1, Area area2) {
        if (isColliding(area1, area2)) {
            if (DebugSettings.DEBUG_COLLISION)
                logger.info("Collision detected between shape " +
                        "[id=" + area1.getId() + " (" + area1.getClass().getSimpleName() + ")] " + " and shape " +
                        "[id=" + area2.getId() + " (" + area2.getClass().getSimpleName() + ")]"
                );
            return true;
        }
        return false;
    }

    private static boolean isColliding(Area s1, Area s2) {

        if (s1 instanceof Rectangle)
            if (s2 instanceof Rectangle)
                return isColliding((Rectangle) s1, (Rectangle) s2);
            else if (s2 instanceof Circle)
                return isColliding((Rectangle) s1, (Circle) s2);

        if (s1 instanceof Circle)
            if (s2 instanceof Rectangle)
                return isColliding((Rectangle) s2, (Circle) s1);
            else if (s2 instanceof Circle)
                return isColliding((Circle) s1, (Circle) s2);

        return false;
    }


    // rectangle-rectangle collision detection
    private static boolean isColliding(Rectangle r1, Rectangle r2) {
       return (r1.getPosition().getX() < r2.getPosition().getX() + r2.getWidth() &&
               r1.getPosition().getX() + r1.getWidth() > r2.getPosition().getX()
       ) && (
               r1.getPosition().getY() < r2.getPosition().getY() + r2.getHeight() &&
               r1.getPosition().getY() + r1.getHeight() > r2.getPosition().getY()
       );
    }

    // circle-circle collision detection
    private static boolean isColliding(Circle c1, Circle c2) {
        logger.info(c1.getPosition().distance(c2.getPosition()) + " < " + (c1.getRadius() + c2.getRadius()));
        return c1.getPosition().distance(c2.getPosition()) < c1.getRadius() + c2.getRadius();
    }

    // rectangle-circle collision detection
    private static boolean isColliding(Rectangle rec, Circle cir) {
        Vector recCenter = rec.getPosition().add(new Vector(rec.getWidth()/2.0 , rec.getHeight()/2.0 ));
        Vector circleDistance = cir.getPosition().sub(recCenter).abs();

        if (circleDistance.getX() > (rec.getWidth() / 2.0 + cir.getRadius())) return false;
        if (circleDistance.getY() > (rec.getHeight() / 2.0 + cir.getRadius())) return false;

        if (circleDistance.getX() <= (rec.getWidth() / 2.0)) return true;
        if (circleDistance.getY() <= (rec.getHeight() / 2.0)) return true;

        double cornerDistance_sq = Math.pow(circleDistance.getX() - (rec.getWidth() / 2.0), 2) +
                Math.pow(circleDistance.getY() - (rec.getHeight() / 2.0), 2);

        return (cornerDistance_sq <= Math.pow(cir.getRadius(), 2));
    }



}
