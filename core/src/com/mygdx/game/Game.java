package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public final class Game extends ApplicationAdapter {

    private SpriteBatch batch;

    @SneakyThrows
    @Override
    public void create() {
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }


}
