package nl.maastrichtuniversity.dke.agents;

import nl.maastrichtuniversity.dke.agents.modules.communication.ICommunicationModule;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;

public class Guard extends Agent {

    public Guard(ISpawnModule spawnModule, IMovement movement, IVisionModule visionModule, ICommunicationModule communicationModule) {
        super(spawnModule,movement, visionModule, communicationModule);
    }

    /**
     * if guard see intruder the intrudet is out otf the game
     * @param intruder
     */
    public void getIntruder(Intruder intruder){
        intruder.setAlive(false);
    }

}
