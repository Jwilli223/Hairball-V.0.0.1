package com.mcmullin.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmullin.game.Levels.*;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Scenes.Hud;
import com.mcmullin.game.Sprites.Char;
import com.mcmullin.game.Tools.B2WorldCreator;
import com.mcmullin.game.Tools.WorldContactListener;
import com.mcmullin.game.Movement.TouchInputProcessor;

/**
 * Created by Joe on 11/12/2016.
 */

public class PlayScreen implements Screen {
    //constants to denote hud buttons for use in input handling
    private static final int UP = 0, LEFT = 1, RIGHT = 2;

    private MyGdxGame game;
    private OrthographicCamera gamecam;
    //offsets used to calculate camera stopping position (see fct. adjustCam)
    private float offsetLeft = 3.25f;
    private float offsetRight = 6.5f;
    private Viewport gamePort;
    private Hud hud;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private static Char player;
    private float stateTime;
    private float jumpStartTime; //time a jump started

    //The current level
    private Level curLevel;
    private boolean levelComplete;
    int deaths;
    //Catches touch input from users
    TouchInputProcessor input;

    public PlayScreen(MyGdxGame game, Level curLevel, int deaths) {
        this.game = game;
        //COMMENTS- Camera set up
        gamecam = new OrthographicCamera();
        gamePort = new ExtendViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gamecam);
        hud = new Hud(game.batch, curLevel, deaths);
        //Load tiled map
        map = game.manager.get(curLevel.getMap(), TiledMap.class);
        this.curLevel = curLevel;
        levelComplete = false;
        this.deaths = deaths;

        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        player = new Char(this, curLevel.getStartX(), curLevel.getStartY());
        world.setContactListener(new WorldContactListener());
        stateTime = 0;
        //set custom touch input as input processor
        input = new TouchInputProcessor();
        Gdx.input.setInputProcessor(input);
        //set gamecam limits and starting position
        float playerX =  player.b2body.getPosition().x;
        float camStartX = playerX + (gamePort.getWorldWidth() / offsetLeft); //just to the right of jump button
        float camStartY = gamePort.getWorldHeight() + 1.25f; //full screen
        gamecam.position.set(camStartX, camStartY, 0);
        gamecam.update();
    }

    @Override
    public void show() {}

    public void handleInput(float dt) {
        if(player.currentState != Char.State.DEAD) {
            if (input.isTouched(0)) {
                inputAct(0, dt);
            }
            if(input.isTouched(1)) {
                inputAct(1, dt);
            }
            // allow for keyboard controls (left and right arrow keys)
            if(Gdx.input.isKeyPressed(22)){
                adjustCam(RIGHT);
                player.charRunRight();
            } else if (Gdx.input.isKeyPressed(21)){
                adjustCam(LEFT);
                player.charRunLeft();
            }
            if(Gdx.input.isKeyJustPressed(62)) { //jump key pressed
                if (stateTime - jumpStartTime >= dt) { //check time between jumps
                    jumpStartTime = stateTime; //reset jump start time
                    player.charJump();//jump
                }
            }
        }
    }

    public void update(float dt) {
        stateTime += dt;
        handleInput(dt);
        world.step(1/60f, 6, 2);
        //update character
        player.update(dt);
        //update hud
        hud.update(dt);
        //update level
        curLevel.update(player, dt);
        //update camera position -- this is set in through handleInput -> inputAct
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        //background color
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glEnable(GL20.GL_BLEND); //enable transparent images
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render
        renderer.render();
        //b2dr.render(world, gamecam.combined); //box2d debug render - Can uncomment and it shows the box2d debug lines/shapes
        game.batch.enableBlending(); //allows multiple tiled tile layers to draw over eachother
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        //render character
        player.draw(game.batch);
        //render current level
        curLevel.render(game);
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //draw hud
        hud.stage.draw();

        //if the level has been completed, move on to the next level
        if(levelComplete)
            setCurLevel(curLevel.getNextMap());

        //If the player dies, sets screen to game over, disposes playscreen
        if(gameOver()) {
            this.deaths++;
            game.setScreen(new GameOverScreen(game, curLevel.getMap(), this.deaths));
            dispose();
        }
    }

    public boolean gameOver() {
        if (player.currentState == Char.State.DEAD)
            return true;
        return false;
    }

    public void setLevelComplete(boolean complete) {
        levelComplete = complete;
    }

    public Level getCurLevel() {
        return curLevel;
    }

    public MyGdxGame getGame() {return game;}

    //Sets the screen to the level passed to it
    //this is called when the "EOL" marker is hit in
    //any level. If the given level is null the end screen
    //is shown.
    public void setCurLevel(String level) {
        ScreenTools.setLevel(game, level, 1);
        dispose();
    }

    //checks the x and y coordinates of the given input
    //against the area of the given button, if it is within
    //the bounds of the button return true
    //uses class constant buttons UP, LEFT and RIGHT as button
    public boolean goodPress(float x, float y, int button) {
        int buttonWH = hud.getButWidthHeight();
        //get data for button x dimentions
        float buttonX = hud.getbuttonX(button);
        float buttonFarX = buttonX + buttonWH;
        //get data for button y dimentions
        float buttonY = hud.getbuttonY(button);
        float buttonFarY = buttonY - buttonWH;
        //checks if given x & y are in bounds of button
        boolean goodX = x >= buttonX && x <= buttonFarX;
        boolean goodY = y <= buttonY && y >= buttonFarY;
        //if both values are within button return true
        if(goodX && goodY) {
            return true;
        }
        return false;
    }

    //calls actions on the character when input is recieved
    //pointer is the touch input the user would like to check against
    //this assumes that the pointer has valid data attached to it
    private void inputAct(int pointer, float dt) {
        //coordinates of the touch input
        float inX = input.inputX(pointer);
        float inY = input.inputY(pointer);

        //check if input should cause an action and call the action
        if(goodPress(inX, inY, UP)) {
            if (stateTime - jumpStartTime >= dt) { //check time between jumps
                jumpStartTime = stateTime; //reset jump start time
                player.charJump();//jump
            }
        } else if(goodPress(inX, inY, LEFT)) {
            adjustCam(LEFT);
            player.charRunLeft();
        } else if (goodPress(inX, inY, RIGHT)) {
            adjustCam(RIGHT);
            player.charRunRight();
        }
    }

    //Takes in a direction of movement and adjusts camera position
    //based on the current location of the player
    //The camera uses left and right offsets to bound its movement
    //ensuring it does not overtake the character
    //When the camera is far from the player (eg when switching running direction)
    //the camera moves quickly to catch the character, when it is near the character
    //it simply maintains its distance at the edge of the camera bounds
    private void adjustCam(int direction) {
        //Player's current x position
        float playerX =  player.b2body.getPosition().x;
        //Calculate stopping position of camera on either side of screen
        float camRightStop = playerX - (gamePort.getWorldWidth() / offsetRight); //just to the left of buttons
        float camLeftStop = playerX + (gamePort.getWorldWidth() / offsetLeft); //just to the right of jump
        //Current position of the camera
        float camX = gamecam.position.x;

        //Update cam position
        if(direction == RIGHT) {
            if (camX < camLeftStop - .05f) //far from position -- catch up
                gamecam.position.x += .075f;
            else if (camX <= camLeftStop)
                gamecam.position.x = camLeftStop;
        } else if (direction == LEFT){
            if(camX > camRightStop + .05f) //far from position -- catch up
                gamecam.position.x -= .075f;
            else if (camX >= camRightStop)
                gamecam.position.x = camRightStop;
        }
    }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width,height);
    }

    public TiledMap getMap()
    {
        return map;
    }

    public World getWorld()
    {
        return world;
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
