package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
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

    @SneakyThrows
    @Override
    public void create() {
        setupGame();
        gameComponent = new GameComponent(game.getScenario().getEnvironment());
    }

    @Override
    public void render() {
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
        Game.setMapFile(new File("core/assets/testmap.txt"));
        game = Game.getInstance();
    }

}
