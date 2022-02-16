package nl.maastrichtuniversity.dke.agents.modules.vision;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.StaticEnvironment;
import nl.maastrichtuniversity.dke.util.Vector;
import nl.maastrichtuniversity.dke.areas.Area;

import java.util.ArrayList;
import java.util.List;

public class VisionModule extends AgentModule implements IVisionModule{

    private final int FOV_RESOLUTION = 50;
    private final int RAY_RESOLUTION = 50;
    private final double VISION_LENGTH = 10;
    private final double fov;

    private List<Ray> rays;

    public VisionModule(Scenario scenario, double fov){
        super(scenario);
        this.fov = fov;
    }

    private void calculateRays(Vector direction){
        double totalRays = FOV_RESOLUTION;
        double interval = fov/totalRays;
        this.rays = new ArrayList<>();

        Vector initialRay = direction.rotate(-fov/2).mul(VISION_LENGTH);

        for(int i = 0; i < totalRays; i++)
            this.rays.add(new Ray(initialRay.rotate(i*interval)));
    }

    @Override
    public List<Area> getObstacles(Vector direction) {
        calculateRays(direction);

        List<Area> obstacles = new ArrayList<>();
        /*
            important! we need to sort the objects in the environment by their distance to the agent,
            so that the closest objects is checked first.
         */
        for (Area area : scenario.getObjects()) {
            for (Ray ray : rays) {
                if (ray.hasPointInArea(area)) obstacles.add(area);
            }
        }

        return obstacles;
    }

    private class Ray {

        private List<Vector> points;

        public Ray(Vector ray) {
            points = new ArrayList<>();
            for (int i = 1; i <= RAY_RESOLUTION; i++) {
                points.add(ray.mul(i / (double) RAY_RESOLUTION));
            }
        }
        public boolean hasPointInArea(Area area) {
            for (Vector point : points) {
             //   if (pointIsInArea(point, area)) { can we not just use this one?
                if(area.containsPoint(point.getX(),point.getY())) {
                    deleteOtherPoints(point);
                    return true;
                }
            }
            return false;
        }

        /**
         * Deletes all residual points inside ray, as these points
         * are blocked by the area.
         */
        private void deleteOtherPoints(Vector point) {
            int end_index = points.indexOf(point);
            points = points.subList(0,end_index);
        }

        /**
         * Checks if a point is inside an area.
            kind don't need the method, since Area objects have containsPoint method
         */
        public boolean pointIsInArea(Vector point, Area area) {
            return false;
        }

    }


}
