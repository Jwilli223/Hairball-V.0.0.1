package com.mcmullin.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();
    private int buttonWH = (height / 100) * 20; //20% screen height
    private int perHeight = height / 100; //1% height
    private int perWidth = width / 100; //1% width

    private Integer worldTimer;
    private float timeCount;
    private String levelName;

    private Image upImg, leftImg, rightImg;

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
        //viewport = new ExtendViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());
        viewport = new ExtendViewport(width, height, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        minutes = 0;

        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("JennieAssets/bebasFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParam.size = perHeight * 5;
        BitmapFont font = fontGen.generateFont(fontParam);

        levelName = curLevel.getLevelName();
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font, Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        levelLabel = new Label(levelName, new Label.LabelStyle(font, Color.WHITE));
        worldLabel =new Label("WORLD", new Label.LabelStyle(font, Color.WHITE));
        hbLabel = new Label("HAIRBALL", new Label.LabelStyle(font, Color.WHITE));
        placeHolderLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        upImg = new Image(new Texture("JennieAssets/UpButton.png"));
        upImg.setSize(buttonWH, buttonWH);
        leftImg = new Image(new Texture("JennieAssets/LeftButton.png"));
        leftImg.setSize(buttonWH, buttonWH);
        rightImg = new Image(new Texture("JennieAssets/RightButton.png"));
        rightImg.setSize(buttonWH, buttonWH);

        //stage.setDebugAll(true); //Show table for debugging

        //Set up the table
        Table table = new Table(); //Create the table for the hud
        table.top();               //Set the table placement as the top of the screen
        table.setFillParent(true); //Make the table the size of the screen
        //used for debugging shows table borders
        //table.debugAll();
        table.add(hbLabel).expandX().padTop(perHeight * 2).padLeft(perWidth * 5);
        table.add(worldLabel).expandX().padTop(perHeight * 2);
        table.add(timeLabel).expandX().padTop(perHeight * 2);
        //new row
        table.row();
        table.add(placeHolderLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        //new row
        table.row().padTop(perHeight * 65).padBottom(perHeight * 2);
        table.add(upImg).size(upImg.getWidth(),upImg.getHeight()).padLeft(perWidth * 2);
        table.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight()).padLeft(perWidth * 65);
        table.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight()).padRight(perWidth * 2).padLeft(perWidth * 2);
        stage.addActor(table);
    }

    //returns the X coordinate of the given button
    //button cases follow class constant rules from playscreen
    //UP = 0, LEFT = 1, RIGHT = 2
    public float getbuttonX(int button) {
        float x;
        switch (button) {
            case 0:
                x = upImg.getX();
                break;
            case 1:
                x = leftImg.getX();
                break;
            case 2:
                x = rightImg.getX();
                break;
            default:
                x = -1; //invalid button
        }
        return x;
    }

    //returns the Y coordinate of the given button
    //button cases follow class constant rules from playscreen
    //UP = 0, LEFT = 1, RIGHT = 2
    public float getbuttonY(int button) {
        float y;
        switch (button) {
            case 0:
                y = upImg.getY() * 10;
                break;
            case 1:
                y = leftImg.getY() * 10;
                break;
            case 2:
                y = rightImg.getY() * 10;
                break;
            default:
                y = -1; //invalid button
        }
        return y;
    }

    public int getButWidthHeight() {
        return buttonWH;
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
