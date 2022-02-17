package nl.maastrichtuniversity.dke.discrete;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tile {

    private int x, y;
    private boolean isOpened;
    private @Setter TileType type;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = TileType.EMPTY;
    }

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

}
