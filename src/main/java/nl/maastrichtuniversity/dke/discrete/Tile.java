package nl.maastrichtuniversity.dke.discrete;

import lombok.*;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tile {

    private @Getter Position position;
    private boolean isOpened;
    private @Setter TileType type;
    private @Getter List<CommunicationMark> communicationMarks;

    public Tile(Position position) {
        this.position = position;
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
