package nl.maastrichtuniversity.dke.discrete;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Scenario {

    private String name;
    private int gameMode;
    private double timeStep;
    private double scaling;

    private Environment environment;

    public Scenario() {}



}
