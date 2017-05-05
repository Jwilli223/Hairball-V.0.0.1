package com.mcmullin.game.Sprites;

/**
 * Created by Jared on 4/3/2017.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;

public class RoadBlock extends Sprite{
    protected PlayScreen screen;
    protected World world;
    protected Body body;
    protected MapObject object;
    protected Class type;
    protected float width = 0, height = 0;

    public RoadBlock (PlayScreen screen, MapObject object) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.object = object;
        type = object.getClass();
        //gather object position information from map object
        if(type == RectangleMapObject.class) {
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            width = rect.getWidth() / MyGdxGame.PPM;
            height = rect.getHeight() / MyGdxGame.PPM;
            //apply position info to sprite object
            setPosition(rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM);
        } else if (type == EllipseMapObject.class) {
            Ellipse ellipse = ((EllipseMapObject)object).getEllipse();
            width = ellipse.width / MyGdxGame.PPM;
            height = ellipse.height / MyGdxGame.PPM;
            setPosition(ellipse.x / MyGdxGame.PPM, ellipse.y / MyGdxGame.PPM);
        }
        setBounds(getX(), getY(), width, height);
        //define object in game world
        defineRB();
    }

    public void update(float dt) {}

    @Override
    public void draw(Batch batch) {}

    public void defineRB() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(this.getX() + (width / 2), this.getY() + (height / 2));
        body = world.createBody(bodyDef);
        FixtureDef fixture = new FixtureDef();
        Shape fixShape;
        if(type == EllipseMapObject.class) {
            fixShape = new CircleShape();
            fixShape.setRadius(height / 2); // makes a circle from the ellipse
        } else {
            fixShape = new PolygonShape();
            ((PolygonShape)fixShape).setAsBox(width / 2, height / 2);
        }
        //is an enemy
        fixture.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        //collide with char
        fixture.filter.maskBits = MyGdxGame.CHAR_BIT;
        fixture.shape = fixShape;
        body.createFixture(fixture);
    }
}
