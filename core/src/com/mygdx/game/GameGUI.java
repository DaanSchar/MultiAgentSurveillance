package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.gamecomponent.GameComponent;
import com.mygdx.game.gamecomponent.HUD;
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
    private GameComponent gameComponent;
    private VictoryExperiment victoryExperiment;
    private HUD hud;

    private float totalTimePassed;
    private static float timeInterval = 0.24f;

    private static final float MAX_TIME_INTERVAL = 0.5f;
    private static final float MIN_TIME_INTERVAL = 0f;
    private static final float TIME_INTERVAL_INCREMENT = 0.02f;

    private static boolean isPaused;

    Batch spBatch;

    @SneakyThrows
    @Override
    public void create() {
        setupGame();
        gameComponent = new GameComponent(game);
        victoryExperiment = new VictoryExperiment(game, 2, true);
        Gdx.input.setInputProcessor(gameComponent);
        spBatch = new SpriteBatch();
        hud = new HUD();
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
            game.victoryMessage();
            victoryExperiment.getVictories().add(game.getVictory());
            doExp();
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
        hud.draw();
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
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource("hardMap3.txt")).getFile());
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

    public void doExp() {
        if (!victoryExperiment.isExp()) {
            gameComponent.resetGame();

        } else {
            if (!victoryExperiment.isDone()) {
                gameComponent.resetGame();
            } else {
                Gdx.app.exit();
                log.info("Guards won " + victoryExperiment.countWinner("G") + " times");
                log.info("Intruders won " + victoryExperiment.countWinner("I") + " times");
            }
        }
    }
}
