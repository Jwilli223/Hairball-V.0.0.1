package com.mcmullin.game.Sprites;

/**
 * Created by Jared on 4/3/2017.
 */

<<<<<<< HEAD
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
=======
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
>>>>>>> refs/remotes/origin/master
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
<<<<<<< HEAD
import com.badlogic.gdx.utils.Array;
=======
>>>>>>> refs/remotes/origin/master
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;

public abstract class RoadBlock extends Sprite{
    protected PlayScreen screen;
    protected World world;
    protected Body body;
    protected Rectangle rect;
<<<<<<< HEAD
    protected TextureRegion tex;
=======
>>>>>>> refs/remotes/origin/master

    public RoadBlock (PlayScreen screen, MapObject object) {
        this.screen = screen;
        this.world = screen.getWorld();
        //gather object position information from map object
        this.rect = ((RectangleMapObject) object).getRectangle();
<<<<<<< HEAD
=======
        this.rect = ((RectangleMapObject) object).getRectangle();
>>>>>>> refs/remotes/origin/master
        float width = rect.getWidth() / 2 / MyGdxGame.PPM;
        float height = rect.getHeight() / 2 / MyGdxGame.PPM;
        //apply position info to sprite object
        setPosition(rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM);
        setBounds(getX(), getY(), width, height);
<<<<<<< HEAD
        //define obect in game world
        defineRB();
    }

    public abstract void defineRB ();
    public abstract boolean kill ();
    public void update(float dt) {
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
=======
        //define object in game world
        defineRB();
    }

    //may be different for each roadblock as it will be the
    //body type (may want a circle or rectangle depending
    //on the actual shape of the image
    public void defineRB() {}
>>>>>>> refs/remotes/origin/master
}
