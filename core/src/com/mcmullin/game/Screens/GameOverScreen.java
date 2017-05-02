package com.mcmullin.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmullin.game.Levels.*;
import com.mcmullin.game.MyGdxGame;


public class GameOverScreen extends ApplicationAdapter implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;


    public GameOverScreen(Game game)
    {
        this.game = game;
        viewport = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);
        Table table = new Table(); //Create a table
        table.center();            //Center the table
        table.setFillParent(true); //Make the table the size of the whole screen
        Image startImg = new Image(new Texture("JennieAssets/GameOverHairball2.png"));
        //Set the image as the end screen design
        startImg.setSize(400,300); //Set the image size
        table.add(startImg).size(startImg.getWidth(),startImg.getHeight());
        //Add the image to the table and set the size to that of the image
        stage.addActor(table);     //Add the table to the stage
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.justTouched())
        {
            game.setScreen(new PlayScreen((MyGdxGame) game, new SpaceLevel()));
            dispose();
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
