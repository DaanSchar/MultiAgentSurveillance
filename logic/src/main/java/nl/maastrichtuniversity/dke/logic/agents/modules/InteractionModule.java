package nl.maastrichtuniversity.dke.logic.agents.modules;

import nl.maastrichtuniversity.dke.logic.scenario.Scenario;
import nl.maastrichtuniversity.dke.logic.scenario.environment.DoorTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.WindowTile;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

public class InteractionModule extends AgentModule {

    public InteractionModule(Scenario scenario) {
        super(scenario);
    }

    public void toggleDoor(Position posOfDoor) {
        DoorTile doorTile = (DoorTile) getTileAt(posOfDoor);
        doorTile.toggleDoor();
    }

    public void breakWindow(Position posOfWindow) {
        WindowTile windowTile = (WindowTile) getTileAt(posOfWindow);
        windowTile.breakWindow();
    }

    private Tile getTileAt(Position position) {
        return scenario.getEnvironment().getAt(position);
    }
}