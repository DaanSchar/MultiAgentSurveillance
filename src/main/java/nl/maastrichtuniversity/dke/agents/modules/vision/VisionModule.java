package nl.maastrichtuniversity.dke.agents.modules.vision;

import nl.maastrichtuniversity.dke.agents.Direction;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.discrete.TileType;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.ArrayList;
import java.util.List;

public class VisionModule extends AgentModule implements IVisionModule {

    private final int FOV_RESOLUTION = 50;
    private final int RAY_RESOLUTION = 50;
    private final double VISION_LENGTH = 10;
    private final double fov;

//    private List<Ray> rays;

    public VisionModule(Scenario scenario, double fov) {
        super(scenario);
        this.fov = fov;
    }


    @Override
    public List<Tile> getObstacles(Position position, Direction direction) {
        Tile[][] tilemap = scenario.getEnvironment().getTileMap();
        List<Tile> obstacles = new ArrayList<>();

        boolean[] canMove = canMove(position, direction);

        boolean canMove1 = canMove[0];
        boolean canMove2 = canMove[1];

        boolean obstruct0 = false;
        boolean obstruct1 = false;
        boolean obstruct2 = false;
        for (int i = 1; i <tilemap.length; i++) {

            obstruct0 = addTmp(tilemap,obstacles,position,direction,obstruct0,true,0,0,i);
            obstruct1 = addTmp(tilemap,obstacles,position,direction,obstruct1,canMove1,-1,-1,i);
            obstruct2 = addTmp(tilemap,obstacles,position,direction,obstruct2,canMove2,1,1,i);

            }
        return obstacles;
    }




    private boolean addTmp(Tile[][] tilemap,List<Tile> obstacles,Position position,Direction direction,
                        boolean obstruct,boolean canMove,int moveX,int moveY,int i){
        Tile tmp;
        if (!obstruct) {
            if (canMove) {
                if (direction.name().equals("NORTH")||direction.name().equals("SOUTH")) {
                    int x = (position.getX() +moveX) + direction.getMoveX() * i;
                    int y = (position.getY()) + direction.getMoveY() * i;
                    if(x<0 || y<0)return true;

                    tmp = tilemap[x][y];
                } else {
                    int x = (position.getX()) + direction.getMoveX() * i;
                    int y = (position.getY()+moveY) + direction.getMoveY()*i;

                    if(x<0 || y<0)return true;


                    tmp = tilemap[x][y];
                }
                if (!tmp.isEmpty()) {
                    if (!tmp.isOpened()) {
                        obstruct = true;
                    }
                    obstacles.add(tmp);
                    //System.out.println("Added " + tmp + " to Obstacles!");
                }
            }
        }
        return obstruct;
    }

    private boolean[] canMove(Position position, Direction direction) {
        boolean canMove1 = false;
        boolean canMove2 = false;

        if (direction.name().equals("NORTH")||direction.name().equals("SOUTH")) {
            if (position.getX() - 1 >= 0) {
                canMove1 = true;
            }
            if (position.getX() + 1 < scenario.getEnvironment().getWidth()) {
                canMove2 = true;
            }
        } else {
            if (position.getY() - 1 >=0) {
                canMove1 = true;
            }
            if (position.getY() + 1 < scenario.getEnvironment().getHeight()) {
                canMove2 = true;
            }
        }

        return new boolean[]{canMove1, canMove2};
    }
//    public List<Area> sortEnvironmentObjects(List<Area> environmentObjects){
//        List<Area> sortedEnvironmentObjects = new LinkedList<>();
//        List<Double> distance = new ArrayList<>();
//
//        Vector agent_position = new Vector(1,1); //  !!! will need to have Agent Position in the constructor
//
//        for (Area environmentObject : environmentObjects) {
//            distance.add(agent_position.distance(environmentObject.getPosition()));
//        }
//
//        Map<Double, Integer> map = new TreeMap<>();
//        for (int i = 0; i < distance.size(); ++i) {
//            map.put(distance.get(i), i);
//        }
//        Object[] indices = map.values().toArray();
//
//        for(int i = 0; i <environmentObjects.size();i++){
//            sortedEnvironmentObjects.add(environmentObjects.get((Integer) indices[i]));
//        }
//
//
//        return sortedEnvironmentObjects;
//    }
//
//    private void calculateRays(Vector direction){
//        double totalRays = FOV_RESOLUTION;
//        double interval = fov/totalRays;
//        this.rays = new ArrayList<>();
//
//        Vector initialRay = direction.rotate(-fov/2).mul(VISION_LENGTH);
//
//        for(int i = 0; i < totalRays; i++)
//            this.rays.add(new Ray(initialRay.rotate(i*interval)));
//    }
//
//    @Override
//    public List<Area> getObstacles(Vector direction) {
//        calculateRays(direction);
//
//        List<Area> obstacles = new ArrayList<>();
//        /*
//            important! we need to sort the objects in the environment by their distance to the agent,
//            so that the closest objects is checked first.
//         */
////        for (Area area : scenario.getObjects()) {
////            for (Ray ray : rays) {
////                if (ray.hasPointInArea(area)) obstacles.add(area);
////            }
////        }
//
//        return obstacles;
//    }
//
//    private class Ray {
//
//        private List<Vector> points;
//
//        public Ray(Vector ray) {
//            points = new ArrayList<>();
//            for (int i = 1; i <= RAY_RESOLUTION; i++) {
//                points.add(ray.mul(i / (double) RAY_RESOLUTION));
//            }
//        }
//        public boolean hasPointInArea(Area area) {
//            for (Vector point : points) {
//             //   if (pointIsInArea(point, area)) { can we not just use this one?
//                if(area.containsPoint(point.getX(),point.getY())) {
//                    deleteOtherPoints(point);
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        /**
//         * Deletes all residual points inside ray, as these points
//         * are blocked by the area.
//         */
//        private void deleteOtherPoints(Vector point) {
//            int end_index = points.indexOf(point);
//            points = points.subList(0,end_index);
//        }
//
//        /**
//         * Checks if a point is inside an area.
//            kind don't need the method, since Area objects have containsPoint method
//         */
//        public boolean pointIsInArea(Vector point, Area area) {
//            return false;
//        }
//
//    }


}
