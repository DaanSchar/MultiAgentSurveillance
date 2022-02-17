package nl.maastrichtuniversity.dke.discrete;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.GUI.GameComponent;
import nl.maastrichtuniversity.dke.util.Position;

public class GameRepository {

    private static @Setter @Getter Scenario scenario;
    private static @Setter @Getter GameComponent gameComponent;

    public static void run() {
        scenario.getGuards().get(0).setPosition(new Position(
                (int) Math.random()*80,
                (int) Math.random()*80
        ));
    }

}
