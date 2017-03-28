package com.mcmullin.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Sprites.Char;

/**
 * Created by Joe on 11/13/2016.
 */

public class StartScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    private Game game;

    public StartScreen(Game game)
    {
        this.game = game;
        viewport = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);
//COMMENTS- Below code is basically just adding text and formatting it
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label startGameLabel = new Label("CLICK TO START NEW GAME", font);
        table.add(startGameLabel).expandX();
        stage.addActor(table);

    }
    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        //COMMENTS- If the user clicks/ touches the screen, it loads the main screen and disposes of the start screen.
        if (Gdx.input.justTouched())
        {
            game.setScreen(new PlayScreen((MyGdxGame) game));


            dispose();

        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
    public void dispose() {

    }
}
