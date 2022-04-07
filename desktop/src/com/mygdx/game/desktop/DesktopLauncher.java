package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameGUI;

public final class DesktopLauncher {

    private static final double SCREEN_RATIO = 16.0 / 9.0;
    private static final int WIDTH = 1400;
    private static final int HEIGHT = (int) (WIDTH / SCREEN_RATIO);

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        editConfig(config);
        new LwjglApplication(new GameGUI(), config);
    }

    private static void editConfig(LwjglApplicationConfiguration config) {
        config.width = WIDTH;
        config.height = HEIGHT;
        config.vSyncEnabled = false;
        config.foregroundFPS = 0;
        config.backgroundFPS = 0;
    }

    private DesktopLauncher() {
    }
}
