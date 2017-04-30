package com.mcmullin.game.Sprites;

/**
 * Created by Jared on 4/3/2017.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;

public abstract class RoadBlock extends Sprite{
    protected PlayScreen screen;
    protected World world;
    protected Body body;
    protected Rectangle rect;

    public RoadBlock (PlayScreen screen, MapObject object) {
        this.screen = screen;
        this.world = screen.getWorld();
        //gather object position information from map object
        this.rect = ((RectangleMapObject) object).getRectangle();
        float width = rect.getWidth() / 2 / MyGdxGame.PPM;
        float height = rect.getHeight() / 2 / MyGdxGame.PPM;
        //apply position info to sprite object
        setPosition(rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM);
        setBounds(getX(), getY(), width, height);
        //define object in game world
        defineRB();
    }

    public void update(float dt) {}

    @Override
    public void draw(Batch batch) {}

    //may be different for each roadblock as it will be the
    //body type (may want a circle or rectangle depending
    //on the actual shape of the image
    public void defineRB() {}
}
