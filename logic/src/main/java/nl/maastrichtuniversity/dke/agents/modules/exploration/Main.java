package nl.maastrichtuniversity.dke.agents.modules.exploration;

import nl.maastrichtuniversity.dke.Game;

import java.io.File;

public final class Main {

    public static void main(String[] args) {
        Game.setMapFile(new File("core/assets/maps/hardMap1.txt"));

        Train<NeuralGameState> train = new Train<>(
                new IntruderMDP(Game.getInstance()),
                1000,
                10 / 3
        );

        train.train(10);
    }

}
