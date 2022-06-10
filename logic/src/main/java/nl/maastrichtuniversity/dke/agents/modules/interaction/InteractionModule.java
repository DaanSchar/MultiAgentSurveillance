package nl.maastrichtuniversity.dke.agents.modules.interaction;

import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.agents.Intruder;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.scenario.Scenario;
import nl.maastrichtuniversity.dke.scenario.environment.DoorTile;
import nl.maastrichtuniversity.dke.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.scenario.environment.WindowTile;
import nl.maastrichtuniversity.dke.scenario.util.Position;

@Slf4j
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

    public void catchIntruder(Position intruderPosition) {
        Intruder intruder = getIntruderAt(intruderPosition);

        if (intruder != null) {
            intruder.setCaught(true);
            scenario.getIntruders().remove(intruder);
            log.info("CAUGHT");
        }
    }

    private Tile getTileAt(Position position) {
        return scenario.getEnvironment().getAt(position);
    }

    private Intruder getIntruderAt(Position position) {
        return scenario.getIntruders().getAt(position);
    }

}
