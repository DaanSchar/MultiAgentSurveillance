package nl.maastrichtuniversity.dke.agents;

import nl.maastrichtuniversity.dke.agents.modules.communication.ICommunicationModule;
import nl.maastrichtuniversity.dke.agents.modules.listening.IListeningModule;
import nl.maastrichtuniversity.dke.agents.modules.memory.IMemoryModule;
import nl.maastrichtuniversity.dke.agents.modules.noiseGeneration.INoiseModule;
import nl.maastrichtuniversity.dke.agents.modules.movement.IMovement;
import nl.maastrichtuniversity.dke.agents.modules.spawn.ISpawnModule;
import nl.maastrichtuniversity.dke.agents.modules.vision.IVisionModule;

public class Guard extends Agent {

    public Guard(ISpawnModule spawnModule, IMovement movement, INoiseModule noiseModule, IVisionModule visionModule, ICommunicationModule communicationModule, IMemoryModule memoryModule, IListeningModule listeningModule) {
        super(spawnModule,movement, visionModule,noiseModule, communicationModule, memoryModule, listeningModule);
    }

    /**
     * if guard see intruder the intruder is out otf the game
     * @param intruder intruder
     */
    public void getIntruder(Intruder intruder){
        intruder.setAlive(false);
    }

}
