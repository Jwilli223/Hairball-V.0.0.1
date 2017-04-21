package com.mcmullin.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Levels.*;
import com.mcmullin.game.Screens.PlayScreen;

/**
 * Created by Joe on 11/12/2016.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    private Level curLevel;
    private String levelName;



    Label countdownLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label hbLabel;
    Label placeHolderLabel;

    Integer minutes;

    public Hud(SpriteBatch sb, Level curLevel)
    {
        worldTimer = 0;
        timeCount = 0;
        viewport = new ExtendViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        minutes = 0;



        this.curLevel = curLevel;
        levelName = curLevel.getLevelName();
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(levelName, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel =new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        hbLabel = new Label("HAIRBALL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        placeHolderLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Image upImg = new Image(new Texture("JennieAssets/UpButton.png"));
        upImg.setSize(((int)(Gdx.graphics.getHeight()* 0.05)),((int)(Gdx.graphics.getHeight()* 0.05)));
        Image leftImg = new Image(new Texture("JennieAssets/LeftButton.png"));
        leftImg.setSize(((int)(Gdx.graphics.getHeight()* 0.05)),((int)(Gdx.graphics.getHeight()* 0.05)));
        Image rightImg = new Image(new Texture("JennieAssets/RightButton.png"));
        rightImg.setSize(((int)(Gdx.graphics.getHeight()* 0.05)),((int)(Gdx.graphics.getHeight()* 0.05)));

        //stage.setDebugAll(true); //Show table for debugging

        //Set up the table
        Table table = new Table(); //Create the table for the hud
        table.top();               //Set the table placement as the top of the screen
        table.setFillParent(true); //Make the table the size of the screen
        table.add(hbLabel).expandX().padTop(((int)(Gdx.graphics.getHeight()* 0.01))).padLeft(((int)(Gdx.graphics.getHeight()* 0.011)));
        table.add(worldLabel).expandX().padTop(((int)(Gdx.graphics.getHeight()* 0.01)));
        table.add(timeLabel).expandX().padTop(((int)(Gdx.graphics.getHeight()* 0.01)));
        table.row(); //everything below this will be on a new row
        table.add(placeHolderLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        table.row().padTop(((int)(Gdx.graphics.getHeight()* 0.13))).padBottom(((int)(Gdx.graphics.getHeight()* 0.05)));
        table.add(upImg).size(upImg.getWidth(),upImg.getHeight()).padLeft(((int)(Gdx.graphics.getWidth()* 0.01)));
        table.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight()).padLeft(((int)(Gdx.graphics.getWidth()* 0.13)));
        table.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight()).padRight(((int)(Gdx.graphics.getWidth()* 0.01))).padLeft(((int)(Gdx.graphics.getWidth()* 0.01)));
        stage.addActor(table);


    }

    public void update(float dt)
    {

        timeCount+=dt;
        if (timeCount >= 1){
            worldTimer++;

            if ((worldTimer %60) == 0 ){
                minutes++;
                worldTimer = 0;
            }
            countdownLabel.setText(String.format("%02d:%02d", minutes,worldTimer));
            timeCount=0;
        }

    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
