package com.mcmullin.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmullin.game.MyGdxGame;

/**
 * Created by Joe on 11/13/2016.
 */
//COMMENTS- Wallpaper (background/gamover.png) was coded poorly- it is basically hardcoded for the nexus 6p
public class GameOverScreen extends ApplicationAdapter implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    Texture background;
    private SpriteBatch batch;

public GameOverScreen(Game game)
{
    this.game = game;
    viewport = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport, ((MyGdxGame) game).batch);
background = new Texture("gameover.png");
    Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
    Table table = new Table();
    table.center();
    table.setFillParent(true);
Label gameOverLabel = new Label("GAME OVER", font);
    Label playAgainLabel = new Label("Click to Play Again", font);
    table.add(gameOverLabel).expandX();
table.row();
    table.add(playAgainLabel).expandX().padTop(10f);
    stage.addActor(table);

    batch = new SpriteBatch();


}
    @Override
    public void show() {

    }

   @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(background,0,0,MyGdxGame.V_WIDTH*10,MyGdxGame.V_HEIGHT*7 );
        batch.end();

        //COMMENTS- if you click / touch screen, opens playscreen, disposes game over screen
        if (Gdx.input.justTouched())
        {


            game.setScreen(new PlayScreen((MyGdxGame) game));
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
