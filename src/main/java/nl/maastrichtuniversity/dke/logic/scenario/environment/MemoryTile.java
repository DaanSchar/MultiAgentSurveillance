package nl.maastrichtuniversity.dke.logic.scenario.environment;

import lombok.Getter;
import lombok.Setter;
import nl.maastrichtuniversity.dke.logic.scenario.util.Position;

@Getter
@Setter
public class MemoryTile extends Tile{

    private boolean visited;
    private boolean explored;

    public MemoryTile(Position position) {
        super(position);
    }

    public MemoryTile(Position position, TileType type) {
        super(position, type);
    }

    public MemoryTile(Tile tile) {
        super(tile.getPosition(), tile.getType());
    }

}
