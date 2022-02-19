package nl.maastrichtuniversity.dke.discrete;

import lombok.*;
import nl.maastrichtuniversity.dke.util.Position;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tile {

    private Position position;
    private boolean isOpened;
    private @Setter TileType type;

    public Tile(Position postion) {
        this.position = postion;
        this.type = TileType.EMPTY;
    }

    public Tile(Position position, TileType type) {
        this.position = position;
        this.type = type;
    }


    public boolean isEmpty(){
        return type == TileType.EMPTY;
    }


}
