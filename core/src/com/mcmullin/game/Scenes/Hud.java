package com.mcmullin.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

/**
 * Created by Joe on 11/12/2016.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label charLabel;
Image hudframes;
    Image hudframes2;
    Image hudframes3;
    Image hudframes4;
    //Texture hud100,hud75,hud50,hud25;
    //TextureRegion[] health = new TextureRegion[3];
    Image hud100,hud75,hud50,hud25,hud0;
    Image[] images = new Image[5];
    Image hudImage;
    int i;
    int j;
    int k;
    Table table = new Table();

    public Hud(SpriteBatch sb)
    {
        viewport = new ExtendViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        i = 0;
        j =0;

        //Image hudframes = new Image();
        Image hud100 = new Image();
        Image hud75 = new Image();
        Image hud50 = new Image();
        Image hud25 = new Image();
        Image hud0 = new Image();
        hud100.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud100.png")))));
        images[0] = hud100;
        hud75.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud75.png")))));
        images[1] = hud75;
        hud50.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud50.png")))));
        images[2] = hud50;
        hud25.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud25.png")))));
        images[3] = hud25;
        hud0.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud0.png")))));
        images[4] = hud0;

        //table.top();

       // table.setFillParent(true);

        for(i = 0; i<5;i++) {
            images[i].setScaling(Scaling.fit);
            images[i].scaleBy(-.7f);
        }


        //worldLabel =new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.top();

        table.setFillParent(true);
           // table.add(images[j]).pad(10).fillY().align(Align.top);
        //table.add(images[j]).pad(10).fillY().align(Align.top);
       // table.add(images[j]).expandX().fill();




       /* countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel =new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        charLabel = new Label("CHAR", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(hudImage).expandX();
        table.add(hudframes).expandX().padTop(10);
        table.add(hudImage).expandX().padTop(10);
        table. add(worldLabel).expandX().padTop(10);
        table. add(timeLabel).expandX().padTop(10);

        table.row();
        table. add(scoreLabel).expandX();
        table. add(levelLabel).expandX();
        table. add(countdownLabel).expandX();*/

        stage.addActor(table);
    }

    public void update(float dt)
    {

        /*if(Char.amIhit() == 0) {
            //Gdx.app.log("test 0", "");
            k = 0;

            //hudframes.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud100.png")))));
        }
         if (Char.amIhit() == 1) {
             k = 1;
             table.reset();
             table.top();

             table.setFillParent(true);
             table.add(images[k]).expandX().fill();


         }
        else if (Char.amIhit() == 2) {
            k = 2;
            table.reset();
            //table.add(images[k]).pad(10).fillY().align(Align.top);
             table.add(images[k]).expandX().fill();
            table.top();
            table.setFillParent(true);

        }
        else if (Char.amIhit() == 3) {
            k = 3;
            table.reset();
            //table.add(images[k]).pad(10).fillY().align(Align.top);
             table.add(images[k]).expandX().fill();
            table.top();
            table.setFillParent(true);

        }
        else if (Char.amIDead())
            k = 4;
            table.reset();
            //able.add(images[k]).pad(10).fillY().align(Align.top);
        table.add(images[k]).expandX().fill();
            table.top();
           table.setFillParent(true);






        //Gdx.app.log("test 1", "");
            //hudframes.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud75.png")))));
       /* else if (Char.amIhit() == 2)
            k =2;
       // Gdx.app.log("test 2", "");
            //hudframes.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud50.png")))));
        else if (Char.amIhit() == 3)
            k = 3;
       // Gdx.app.log("test 3", "");
            //hudframes.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud25.png")))));
        else
            k =4;
            //hudframes.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud0.png")))));
       /* if(Char.amIhit() == 0) {
            hudframes.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud100.png")))));
        }

        else
            hudframes.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("hud75.png")))));*/

    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
