package nl.maastrichtuniversity.dke.agents.modules.vision;

import lombok.Getter;
import nl.maastrichtuniversity.dke.scenario.Environment;
import nl.maastrichtuniversity.dke.util.Vector;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.areas.Area;

import java.util.ArrayList;
import java.util.List;

public class VisionModule implements IVisionModule{
    List<Ray> Rays;
    private final int FOV_RESOLUTION = 50;
    private final int RAY_RESOLUTION = 50;
    private final double VISION_LENGTH = 10;
    Agent a;
    Environment environment;
    private  List<Area> seenObjects;

    public VisionModule(Agent a, Environment environment){
        this.a=a;
        this.environment = environment;
        seenObjects = new ArrayList<>();
    }

    private void calculateRays(){
        double fov = a.getFov();
        double rays = FOV_RESOLUTION;
        double interval = fov/rays;

        Vector direction  = a.getDirection();
        Vector initialRay = direction.rotate(-fov/2).mul(VISION_LENGTH);

        for(int i = 0; i<rays;i++){
            Rays.add(new Ray(initialRay.rotate(i*interval)));
        }

        for (Area area : environment.getObjects()) {
            for (Ray ray : Rays) {
                if (ray.hasPointInArea(area)) {
                    seenObjects.add(area);
                }
            }
        }


    }

    @Override
    public List<Area> getObstacles() {
        return seenObjects;
    }


    private class Ray {

        private final Vector ray;
        private List<Vector> points;

        public Ray(Vector ray) {
            this.ray = ray;
            points = new ArrayList<>();
            for (int i = 1; i <= RAY_RESOLUTION; i++) {
                points.add(ray.mul(i / (double) RAY_RESOLUTION));
            }

        }

        public boolean hasPointInArea(Area area) {
            for (Vector point : points) {
                if (pointIsInArea(point, area)) {
                    deleteOtherPoints();
                    return true;
                }
            }
            return false;
        }

        /**
         * Deletes all residual points inside ray, as these points
         * are blocked by the area.
         */
        private void deleteOtherPoints() {
        }

        /**
         * Checks if a point is inside an area.

         */
        public boolean pointIsInArea(Vector point, Area area) {
            return false;
        }



    }


}
