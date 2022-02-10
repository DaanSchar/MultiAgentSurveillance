package nl.maastrichtuniversity.dke.agents.modules.vision;

import nl.maastrichtuniversity.dke.scenario.Environment;
import nl.maastrichtuniversity.dke.util.Vector;
import nl.maastrichtuniversity.dke.agents.Agent;
import nl.maastrichtuniversity.dke.areas.Area;

import java.util.List;

public class VisionModule implements IVisionModule{
    List<Vector> Rays;
    Agent a;
    Environment environment;

    public VisionModule(Agent a, Environment environment){
        this.a=a;
        this.environment = environment;
    }

    private void calculateRays(){
        double fov = a.getFov();
        double rays = 50;
        double interval = fov/rays;

        Vector direction  = a.getDirection();
        Vector initialRay = direction.rotate(-fov/2);

        for(int i = 0; i<rays;i++){
            Rays.add(initialRay.rotate(i*interval));
        }




    }

    private void getCollisions(){



    }





    @Override
    public List<Area> getObstacles(Agent a) {
        return null;
    }
}
