package nl.maastrichtuniversity.dke.discrete;

import lombok.*;
import nl.maastrichtuniversity.dke.util.Position;

import java.util.ArrayList;
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
        this.communicationMarks = new ArrayList<>();
    }

    public Tile(Position position, TileType type) {
        this.position = position;
        this.type = type;
        this.communicationMarks = new ArrayList<>();

    }

    public boolean isEmpty(){
        return type == TileType.EMPTY;
    }



}
