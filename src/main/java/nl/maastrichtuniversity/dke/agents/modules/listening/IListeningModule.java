package nl.maastrichtuniversity.dke.agents.modules.listening;

import java.util.Vector;

public interface IListeningModule {
    public boolean getSound(); //returns whether the agent is in an area of sound or not
    public Vector getSoundDirection(); //returns the direction where the sound is coming from
}
