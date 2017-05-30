package com.mcmullin.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcmullin.game.Screens.StartScreen;

public class MyGdxGame extends Game {
	//COMMENTS- Size of the camera view
	public static final int V_WIDTH = 300;
	public static final int V_HEIGHT = 240;
	public static final float PPM = 100;
	public SpriteBatch batch;
	//COMMENTS- These are just unique identifiers for things that are used in code that relates to collision
	public static final short CHAR_BIT = 2;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short GROUND_BIT = 1;
	public static final short ENEMY_BIT = 64;
	public static final short OBJECT_BIT = 32;
	public static final short LOG_BIT = 256;
	//asset manager
	public AssetManager manager = new AssetManager();

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

}
