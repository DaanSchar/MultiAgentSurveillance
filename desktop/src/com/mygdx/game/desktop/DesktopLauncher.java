package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Game;

public class DesktopLauncher {

	private final static double screenRatio = 16.0 / 9.0;
	private final static int width = 1400;
	private final static int height = (int) (width / screenRatio);

	public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = width;
        config.height = height;
        new LwjglApplication(new Game(), config);
    }
}
