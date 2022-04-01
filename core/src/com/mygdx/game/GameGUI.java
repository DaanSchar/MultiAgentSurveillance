package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.justforfun.ColorStage;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nl.maastrichtuniversity.dke.logic.Game;

import java.io.File;

@Getter
@Slf4j
public final class GameGUI extends ApplicationAdapter {

    private Game game;
    private GameComponent gameComponent;

    private float totalTimePassed;
    private final float timeInterval = 0.25f;

    @SneakyThrows
    @Override
    public void create() {
        setupGame();
        gameComponent = new GameComponent(
                new EnvironmentView(game.getScenario().getEnvironment()),
                new AgentView(game.getScenario().getGuards(), game.getScenario().getIntruders())
        );
    }

    @Override
    public void render() {
        totalTimePassed += Gdx.graphics.getDeltaTime();

        if (totalTimePassed > timeInterval) {
            totalTimePassed = 0;
            update();
        }

        draw();
    }

    private void update() {
        game.update();
    }

    private void draw() {
        ScreenUtils.clear(0, 0, 0, 10);
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
        Game.setMapFile(new File("core/assets/testmap.txt"));
        game = Game.getInstance();
        game.init();
    }

}
