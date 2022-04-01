package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GUIGame;

public class DesktopLauncher {

	private final static double SCREEN_RATIO = 16.0 / 9.0;
	private final static int WIDTH = 1400;
	private final static int HEIGHT = (int) (WIDTH / SCREEN_RATIO);

	public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = WIDTH;
        config.height = HEIGHT;
        new LwjglApplication(new GUIGame(), config);
    }
}
