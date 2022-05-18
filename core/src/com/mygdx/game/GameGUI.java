package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.gamecomponent.GameComponent;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.Game;
import nl.maastrichtuniversity.dke.experiments.VictoryExperiment;

import java.io.File;
import java.util.Objects;

@Getter
@Slf4j
public final class GameGUI extends ApplicationAdapter {

    private Game game;
    private int gameNumber = 0;
    private GameComponent gameComponent;
    private VictoryExperiment victoryExperiment;

    private float totalTimePassed;
    private static float timeInterval = 0.24f;

    private static final float MAX_TIME_INTERVAL = 0.5f;
    private static final float MIN_TIME_INTERVAL = 0f;
    private static final float TIME_INTERVAL_INCREMENT = 0.02f;

    private static boolean isPaused;

    @SneakyThrows
    @Override
    public void create() {
        setupGame();
        gameComponent = new GameComponent(game);
        victoryExperiment = new VictoryExperiment(2, true);
        Gdx.input.setInputProcessor(gameComponent);
    }

    @Override
    public void render() {
        totalTimePassed += Gdx.graphics.getDeltaTime();
        if (!game.checkVictory()) {
            if (totalTimePassed > timeInterval) {
                totalTimePassed = 0;

                if (!isPaused) {
                    update();
                }
            }

        } else {
            gameNumber++;
            game.victoryMessage(gameNumber);

            victoryExperiment.getVictories().add(game.getVictory());
            victoryExperiment.printVictories();
            log.info("Guards won " + victoryExperiment.countWinner("G") + " times");
            log.info("Intruders won " + victoryExperiment.countWinner("I") + " times");

            if (!victoryExperiment.isExp()) {
                gameComponent.resetGame();

            } else {
                if (gameNumber < victoryExperiment.getNumOfGames()) {
                    gameComponent.resetGame();
                } else {
                    Gdx.app.exit();
                }
            }
        }

        draw();
    }

    private void update() {
        game.update();
        gameComponent.update();
    }

    private void draw() {
        ScreenUtils.clear(0, 0, 0, 1);
        gameComponent.draw();
    }

    @Override
    public void dispose() {
        gameComponent.dispose();
    }

    @Override
    public void resize(int width, int height) {
        gameComponent.resize(width, height);
    }

    private void setupGame() {
        Game.setMapFile(getMapFile());
        game = Game.getInstance();
        game.init();
    }

    private File getMapFile() {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testmap.txt")).getFile());
    }

    public static boolean togglePause() {
        isPaused = !isPaused;
        return isPaused;
    }

    public static void incrementTimeInterval() {
        if (timeInterval < MAX_TIME_INTERVAL) {
            timeInterval += TIME_INTERVAL_INCREMENT;
        }
    }

    public static void decrementTimeInterval() {
        if (timeInterval > MIN_TIME_INTERVAL) {
            timeInterval -= TIME_INTERVAL_INCREMENT;
        }
    }

}
