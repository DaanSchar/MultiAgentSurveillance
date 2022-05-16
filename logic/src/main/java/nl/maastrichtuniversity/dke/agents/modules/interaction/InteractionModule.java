package nl.maastrichtuniversity.dke.agents.modules.interaction;

import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.DoorTile;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.WindowTile;
import nl.maastrichtuniversity.dke.scenario.util.Position;

public class InteractionModule extends AgentModule {

    public InteractionModule(Scenario scenario) {
        super(scenario);
    }

    public void toggleDoor(Position posOfDoor) {
        DoorTile doorTile = (DoorTile) getTileAt(posOfDoor);
        doorTile.toggleDoor();
    }

    public boolean breakWindow(Position posOfWindow) {
        WindowTile windowTile = (WindowTile) getTileAt(posOfWindow);
        return windowTile.breakWindow();
    }

    private Tile getTileAt(Position position) {
        return scenario.getEnvironment().getAt(position);
    }
}
