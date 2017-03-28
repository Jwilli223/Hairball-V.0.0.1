package com.mcmullin.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mcmullin.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = MyGdxGame.V_WIDTH;
		//config.height = MyGdxGame.V_HEIGHT;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
