package com.mcmullin.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;

/**
 * Created by Joe on 3/22/2017.
 */

public class Log extends Enemy {
    private float stateTime;

    private Animation walkAnimation;
    private Array<TextureRegion> frames;

    public Log(PlayScreen screen, float x, float y)
    {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas6().findRegion("Log"), 0, 0, 122, 48));
        walkAnimation = new Animation(.1f, frames);
        stateTime = 0;
        setBounds(getX(),getY(),122 / MyGdxGame.PPM , 48 / MyGdxGame.PPM );
    }

    public void update(float dt)
    {
        stateTime += dt;
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnemy()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);
        //fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fdef.filter.categoryBits = MyGdxGame.LOG_BIT;
        fdef.filter.maskBits = MyGdxGame.OBJECT_BIT | MyGdxGame.CHAR_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        //COMMENTS- BOX2D shape that can be used for collision
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
       /* vertice[0] = new Vector2(-8,8).scl(1/MyGdxGame.PPM);
        vertice[1] = new Vector2(8,8).scl(1/MyGdxGame.PPM);
        vertice[2] = new Vector2(8,-8).scl(1/MyGdxGame.PPM);
        vertice[3] = new Vector2(-8,-8).scl(1/MyGdxGame.PPM);*/
        vertice[0] = new Vector2(-58,16).scl(1/MyGdxGame.PPM);
        vertice[1] = new Vector2(58,16).scl(1/MyGdxGame.PPM);
        vertice[2] = new Vector2(58,-16).scl(1/MyGdxGame.PPM);
        vertice[3] = new Vector2(-58,-16).scl(1/MyGdxGame.PPM);
        head.set(vertice);
        fdef.shape = head;
        fdef.density = 20f;
        //fdef.restitution = 1f;
        //fdef.filter.categoryBits = MyGdxGame.ENEMY_HEAD_BIT;
        //fdef.filter.maskBits = MyGdxGame.FIREBALL_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }
    public TextureRegion getFrame(float dt)
    {
        TextureRegion region;
        region = walkAnimation.getKeyFrame(stateTime, true);
        return region;
    }
    //COMMENTS- This and smartreverse make the enemy speed up and follow the character when in range

    public void draw(Batch batch)
    {
        super.draw(batch);
    }

    @Override
    public void hitOnHead()

    {

    }

    @Override
    public void hitByEnemy(Enemy enemy) {
        reverseVelocity(true,false);
    }

    //@Override
    // public void receivedHit() {

    //}
}
