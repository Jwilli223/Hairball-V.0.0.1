package com.mcmullin.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmullin.game.Levels.*;
import com.mcmullin.game.MyGdxGame;

/**
 * Created by Jared on 4/30/2017.
 */

public class LoadingScreen implements Screen {
    private AssetManager manager;
    private Viewport viewport;
    private Stage stage;
    private MyGdxGame game;
    Label loadLabel;
    private String label = "loading...";
    private Table table;
    private Label.LabelStyle font;

    public LoadingScreen(MyGdxGame game)
    {
        this.game = game;
        this.manager = game.manager;
        viewport = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);


        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        table = new Table();
        table.center();
        table.setFillParent(true);
        loadLabel = new Label(label, font);
        table.add(loadLabel).expandX();
        stage.addActor(table);
    }

    public void load() {
        //load char textures
        manager.load("idrun.atlas", TextureAtlas.class);
        manager.load("jumper.atlas", TextureAtlas.class);
        //load maps
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("spacelevel.tmx", TiledMap.class);
        manager.load("SewerFinal.tmx", TiledMap.class);
        manager.load("rubylevel.tmx", TiledMap.class);
        manager.load("corelevel.tmx", TiledMap.class);

    }
    @Override
    public void show() {
        //begin loading assets
        load();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(manager.update()) {
            ScreenTools.setLevel(game, "spacelevel.tmx");
            //ScreenTools.setLevel(game, "SewerFinal.tmx");
            dispose();
        } else {
            float prog = (manager.getProgress() * 100);
            loadLabel.setText(label + round(prog, 1) + "%");
        }
        stage.draw();
    }

    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {stage.dispose();}
}
