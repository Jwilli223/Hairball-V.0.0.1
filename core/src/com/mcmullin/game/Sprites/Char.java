package com.mcmullin.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;


/**
 * Created by Joe on 11/12/2016.
 */

public class Char extends Sprite {
    private static final int MAX_X_VELOCITY = 2; //maximum laft/right speed
    //States
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;
    //Game info
    public World world;
    public Body b2body;
    private static float anotherTimer;
    //Animations
    private Animation charStand;
    private TextureRegion charDead;
    private Animation charRun;
    private Animation charJump;
    //State information
    private static boolean runningRight;
    private static boolean iAmDead;
    private static float stateTimer;
    public static final short DEAD_BIT = 1;
    private static int hitCount;
    private static int attackDC = 0;
    private static boolean beenHit;
    private static boolean isTouched;
    //Atlas'
    private TextureAtlas runAtlas;
    private TextureAtlas jumpAtlas;
    private TextureAtlas standingAtlas;
    private TextureAtlas deadAtlas;
    //start coords
    private float startX, startY;

    public Char(PlayScreen screen, float startX, float startY)
    {
        super(new TextureAtlas("idrun.txt").findRegion("1"));
        //this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;

        this.startX  = startX;
        this.startY = startY;

        stateTimer = 0;
        hitCount = 0;
        iAmDead = false; //set to true to test game over screen until we have death working
        runningRight = true;
        beenHit = false;
        anotherTimer = 0;
        //setup atlases
        runAtlas = new TextureAtlas("idrun.txt");
        jumpAtlas = new TextureAtlas("jumper.txt");
        standingAtlas = new TextureAtlas("ballstance.txt");
        deadAtlas = new TextureAtlas("dead.txt");
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //COMMENTS- Loads individual pictures from sprite sheets (android- assets, txt file related to getAtlas)
        //the green numbers represent the title of the image, and the numbers in blue are the dimensions
        frames.add(new TextureRegion(runAtlas.findRegion("2"), 0, 0, 51, 47));
        frames.add(new TextureRegion(runAtlas.findRegion("6"), 0, 0, 55, 47));
        frames.add(new TextureRegion(runAtlas.findRegion("5"), 0, 0, 52, 48));
        frames.add(new TextureRegion(runAtlas.findRegion("8"), 0, 0, 55, 47));
        //frames.add(new TextureRegion(screen.getAtlas().findRegion("9"), 0, 0, 50, 46));

        //COMMENTS- defines animation and the 0.2 is the speed of the animation
        charRun = new Animation(0.2f, frames);
        frames.clear();

        int j = 0;
        frames.add(new TextureRegion(jumpAtlas.findRegion("1"), 0, 0, 36, 45));
        frames.add(new TextureRegion(jumpAtlas.findRegion("2"), 0, 0, 51, 47));
        frames.add(new TextureRegion(jumpAtlas.findRegion("3"), 0, 0, 52, 45));
        frames.add(new TextureRegion(jumpAtlas.findRegion("4"), 0, 0, 58, 49));
        frames.add(new TextureRegion(jumpAtlas.findRegion("5"), 0, 0, 50, 44));
        charJump = new Animation(0.1f,frames);
        Array<TextureRegion> frames2 = new Array<TextureRegion>();
        frames2.add(new TextureRegion(standingAtlas.findRegion("0"), 0, 0, 21, 50));
        frames2.add(new TextureRegion(standingAtlas.findRegion("2"), 0, 0, 28, 46));
        charDead = new TextureRegion(deadAtlas.findRegion("3"), 0, 0, 41, 28);
        Array<TextureRegion> frames3 = new Array<TextureRegion>();
        frames3.add(new TextureRegion(runAtlas.findRegion("2"), 0, 0, 51, 47));
        frames3.add(new TextureRegion(runAtlas.findRegion("1"), 0, 0, 49, 44));
        charStand = new Animation(0.1f, frames3);
        defineChar();
        //defineChar();
        //COMMENTS- Sets size of Char
        setBounds(0,0, 49 / MyGdxGame.PPM, 46 / MyGdxGame.PPM);
    }

    public void update(float dt)
    {
        anotherTimer += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 );
        setRegion(getFrame(dt));
    }

    public void changePosition(float x, float y) {
        setPosition(x, y);
    }

    //COMMENTS- sets appropriate animations for the given states and flipx flips the character images when moving left
    public TextureRegion getFrame(float dt)
    {
        currentState = getState();
        TextureRegion region;
        switch(currentState)
        {
            case DEAD:
                region = charDead;
                break;
            case JUMPING:
            region = charJump.getKeyFrame(stateTimer);
            break;
            case RUNNING:
                region = charRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region =charStand.getKeyFrame(stateTimer);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX() &&!beenHit) {

            region.flip(true, false);
            runningRight = false;
        }

        if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
                region.flip(true, false);
                runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer +dt : 0;
        previousState = currentState;
        return region;

    }

    //COMMENTS- Assigns the current state of the character
    public  State getState(){
        if(iAmDead)
            return State.DEAD;
        else if (b2body.getLinearVelocity().y >0)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y <0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;

    }

    public static boolean playerRunD()
{
    return runningRight;
}

    //COMMENTS- Ignore for now
    public static void setHit(int dead) {
        if (dead == 1) {
            anotherTimer = 0;
            beenHit= true;
            hitCount++;
            Gdx.app.log("you have been", "boned");
        }
        if (hitCount >=4)
        {
            iAmDead = true;
            stateTimer = 0;
            hitCount = 0;
        }
    }

    public static void setTouch(int touch)
    {
        if (touch == 1) {
            Gdx.app.log("you have been", "booo");
            isTouched = true;
        } else if (touch == -1) {

        }
        isTouched = false;
    }
    //float x, float y
    public void defineChar()
    {
        // BOX2D body (circle)- deals with collision. Disable debug render in playscreen if you want to see
        BodyDef bdef = new BodyDef();
        //bdef.position.set(32 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        bdef.position.set(startX, startY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(16 / MyGdxGame.PPM);
        //COMMENTS- Assings the unique identifier to Char
        fdef.filter.categoryBits = MyGdxGame.CHAR_BIT;
        //COMMENTS- What the Char can collide with / touch
        fdef.filter.maskBits =MyGdxGame.GROUND_BIT |
                MyGdxGame.ENEMY_BIT | MyGdxGame.LOG_BIT |
                MyGdxGame.ENEMY_HEAD_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef);
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MyGdxGame.PPM, 6 / MyGdxGame.PPM), new Vector2(2 / MyGdxGame.PPM, 6 / MyGdxGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");
    }

    public void charJump() {
        if(currentState != State.JUMPING && currentState != State.FALLING) {
            this.b2body.applyLinearImpulse(new Vector2(0, 4f), this.b2body.getWorldCenter(), true);
        }
    }

    public void charRunRight() {
        charRun(0.1f);
    }
    public void charRunLeft() {
        charRun(-0.1f);
    }

    //This is a general function to handle character running
    //it takes a float as the vector modifier (controls force of vector)
    //if the character is not moving above max speed the impulse is added
    private void charRun(float vectorMod) {
        if(Math.abs(this.b2body.getLinearVelocity().x) <= MAX_X_VELOCITY) {
            this.b2body.applyLinearImpulse(new Vector2(vectorMod, 0), this.b2body.getWorldCenter(), true);
        }
    }
}
