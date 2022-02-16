package nl.maastrichtuniversity.dke.discrete;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Tile {

    private int x, y;
    private @Setter TileType type;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = TileType.EMPTY;
    }

}
