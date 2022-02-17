package nl.maastrichtuniversity.dke.explore;

import lombok.Setter;
import nl.maastrichtuniversity.dke.discrete.TileType;
import nl.maastrichtuniversity.dke.util.Position;

public class AgentTile {

    private Position position;
    private boolean isOpened;
    private @Setter
    AgentTileType type;

//    public AgentTile(Position postion) {
//        this.position = postion;
//        this.type = AgentTileType.DOOR.EMPTY;
//    }

    public AgentTile(Position position, AgentTileType type) {
        this.position = position;
        this.type = type;
    }
}
