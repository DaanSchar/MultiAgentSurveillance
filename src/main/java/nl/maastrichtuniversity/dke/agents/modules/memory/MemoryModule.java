package nl.maastrichtuniversity.dke.agents.modules.memory;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.agents.modules.AgentModule;
import nl.maastrichtuniversity.dke.discrete.Environment;
import nl.maastrichtuniversity.dke.discrete.EnvironmentFactory;
import nl.maastrichtuniversity.dke.discrete.Scenario;
import nl.maastrichtuniversity.dke.discrete.Tile;
import nl.maastrichtuniversity.dke.util.Position;

@Getter
public class MemoryModule extends AgentModule implements IMemoryModule {

    private final Environment map;

    private @Setter Position position;

    public MemoryModule(Scenario scenario) {
        super(scenario);

        var builder = new EnvironmentFactory();

        this.map = builder.build();
    }

    public void update(Tile tile) {
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();

        map.getTileMap()[x][y] = tile;
    }



}
