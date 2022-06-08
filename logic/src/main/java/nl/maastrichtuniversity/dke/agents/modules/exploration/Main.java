package nl.maastrichtuniversity.dke.agents.modules.exploration;

import nl.maastrichtuniversity.dke.Game;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        Game.setMapFile(new File("core/assets/maps/hardMap1.txt"));

        Train<SequentialGameState> train = new Train<>(
                new SequentialIntruderMDP(Game.getInstance()),
                1000,
                10/3
        );

        train.train(1);
    }

}
