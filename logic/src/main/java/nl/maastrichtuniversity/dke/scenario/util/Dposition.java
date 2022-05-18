package nl.maastrichtuniversity.dke.scenario.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.maastrichtuniversity.dke.agents.util.Direction;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Dposition {
    private double x;
    private double y;

    public Dposition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}