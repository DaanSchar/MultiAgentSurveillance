package nl.maastrichtuniversity.dke.discrete;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class EnvironmentFactory {

    private @Setter int width;
    private @Setter int height;
    private Tile[][] timeMap;

    public void addArea(int x1, int y1, int x2, int y2, TileType type) {
        if (this.timeMap == null && width >0 && height > 0)
            this.timeMap = new Tile[width][height];

    }

}
