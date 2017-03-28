package com.mcmullin.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;

/**
 * Created by Joe on 11/14/2016.
 */

public class Skeleton extends Enemy
{
    private float stateTime;
    private float stateTime2;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean speedUp;
    private boolean speedComp;
    private float reverseTime;
    private float timeDif;
    private boolean resume;
    private int hitCount;




    private boolean runningRight;




    public Skeleton(PlayScreen screen, float x, float y)
    {
        super(screen, x, y);
        frames = new Array<TextureRegion>();

        frames.add(new TextureRegion(screen.getAtlas2().findRegion("7"), 0 * 46, 0, 22, 54));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("6"), 0 * 46, 0, 19, 54));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("5"), 0 * 46, 0, 22, 53));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("4"), 0 * 46, 0, 24, 52));


            walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(),getY(),24 / MyGdxGame.PPM , 54 / MyGdxGame.PPM );
        setToDestroy = false;
        destroyed = false;
        speedUp = false;
        speedComp = false;
        reverseTime = 0;
        timeDif = 0;
        stateTime2 = 0;
        resume = false;
        hitCount = 0;

    }

    public void update(float dt)
    {
        stateTime += dt;
        stateTime2 += dt;

        if (!setToDestroy) {
            setRegion(getFrame(dt));
        }
inRange(dt);
      speedUp = inRange(dt);

       smartReverse(dt);


        resetC(dt);

        if(setToDestroy && !destroyed)
       {


            world.destroyBody(b2body);
            destroyed = true;
           if(velocity.x > 0) {
               setX(getX() - .25f);

           }
           else if (velocity.x < 0)
           {
           setX(getX() + .25f);
       }
           //COMMENTS- Gives "dead" animation
           rotate(90);


           setRegion(new TextureRegion(screen.getAtlas2().findRegion("0"), 0 * 25, 0, 54, 51));
           stateTime = 0;
        }
       else if(!destroyed) {
           b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
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
        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;

    fdef.filter.maskBits = MyGdxGame.GROUND_BIT |
            MyGdxGame.CHAR_BIT |
            MyGdxGame.OBJECT_BIT |
            MyGdxGame.ENEMY_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

//COMMENTS- BOX2D shape that can be used for collision
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];

        vertice[0] = new Vector2(-8,8).scl(1/MyGdxGame.PPM);
        vertice[1] = new Vector2(8,8).scl(1/MyGdxGame.PPM);
        vertice[2] = new Vector2(8,-8).scl(1/MyGdxGame.PPM);
        vertice[3] = new Vector2(-8,-8).scl(1/MyGdxGame.PPM);





        head.set(vertice);

        fdef.shape = head;




        fdef.restitution = 1f;

        fdef.filter.categoryBits = MyGdxGame.ENEMY_HEAD_BIT;
        //fdef.filter.maskBits = MyGdxGame.FIREBALL_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }
    public TextureRegion getFrame(float dt)
    {
        TextureRegion region;
        region = walkAnimation.getKeyFrame(stateTime, true);

        if(velocity.x > 0 && region.isFlipX() == false)
        {
            region.flip(true, false);
        }
        if(velocity.x < 0 && region.isFlipX() == true){
            region.flip(true, false);
        }



        return region;

    }
    //COMMENTS- This and smartreverse make the enemy speed up and follow the character when in range
    public boolean inRange(float dt)
    {

        if (((Char.playerRunD() && (getX() - PlayScreen.getPlayerX(dt)) > 1.2f) && !destroyed)) {

            return true;

        } else if (((Char.playerRunD() && (PlayScreen.getPlayerX(dt) - getX() ) < 1.2f) && !destroyed)) {

            return true;

        } else if (((!Char.playerRunD() && (getX() - PlayScreen.getPlayerX(dt)) < 1.2f) && !destroyed)) {

            return true;
        }


            return false;


    }



    public void smartReverse(float dt) {
        if (speedUp)
        {

            if (PlayScreen.getPlayerX(dt) < getX() && velocity.x < 0) { //left left
                reverseVelocity(false, false);

            } else if (PlayScreen.getPlayerX(dt) < getX() && velocity.x > 0) { //left right

                chaseL();
                resume = true;

            } else if (PlayScreen.getPlayerX(dt) > getX() && velocity.x > 0) { //right right
                reverseVelocity(false, false);

            } else if (PlayScreen.getPlayerX(dt) > getX() && velocity.x < 0)
            {
                chaseR();
                resume = true;
            }
        }

    }




    public  void chaseR()
    {
        if(speedUp) {
            velocity = new Vector2(.7f, 0);


        }

    }

    public void resetC(float dt)

    {
if (!speedUp &&resume && stateTime2 >= 12)
{
    reverseVelocity(true,false);
    stateTime2 = 0;
}


    }


    public  void chaseL()
    {
        if(speedUp) {
            velocity = new Vector2(-.7f, 0);


        }
    }

    public void draw(Batch batch)
    {
        if(!destroyed || stateTime <1)
        super.draw(batch);
        }

@Override
public void hitOnHead()

        {
            hitCount++;
            if (hitCount >= 2) {
                setToDestroy = true;

            }
        }

    @Override
    public void hitByEnemy(Enemy enemy) {
        reverseVelocity(true,false);

    }

    //@Override
   // public void receivedHit() {

    //}
}
