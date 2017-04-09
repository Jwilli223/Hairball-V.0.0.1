package com.mcmullin.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mcmullin.game.Levels.*;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Scenes.Hud;
import com.mcmullin.game.Sprites.Char;
import com.mcmullin.game.Sprites.Enemy;
import com.mcmullin.game.Sprites.Skeleton;
import com.mcmullin.game.Tools.B2WorldCreator;
import com.mcmullin.game.Tools.WorldContactListener;
import com.mcmullin.game.Movement.TouchInputProcessor;

/**
 * Created by Joe on 11/12/2016.
 */

public class PlayScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private static Char player;
    private int activeTouch = 0;
    private float stateTime;
    private float jumpStartTime; //time a jump started
    private float stateTimeCompare;
    private float stateTimeCompare2;
    private float stateTimeCompare3;
    boolean justTouched = Gdx.input.justTouched();
    private int attackCount = 0;
    private boolean jumpB;

    //The current level
    private Level curLevel;
    private boolean levelComplete;
    //Catches touch input from users
    TouchInputProcessor input;


    public PlayScreen(MyGdxGame game, Level curLevel) {
        this.game = game;
        //COMMENTS- Camera set up
        gamecam = new OrthographicCamera();
        gamePort = new ExtendViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM,MyGdxGame.V_HEIGHT / MyGdxGame.PPM,gamecam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        //COMMENTS- Load tiled map file here
        map = maploader.load(curLevel.getMap());
        this.curLevel = curLevel;
        levelComplete = false;

        renderer = new OrthoCachedTiledMapRenderer(map, 1/ MyGdxGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        player = new Char(this);
        world.setContactListener(new WorldContactListener());
        stateTime = 0;
        //set custom touch input as input processor
        input = new TouchInputProcessor();
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void show() {}

    public void handleInput(float dt)
    {
        if(player.currentState != Char.State.DEAD) {
            //second finger tap or spacebar
            if((input.isTouched(1) && input.isTouched(0)) || Gdx.input.isKeyJustPressed(62)) { //jump key pressed
                if (stateTime - jumpStartTime >= dt) { //check time between jumps
                    jumpStartTime = stateTime; //reset jump start time
                    player.charJump();//jump
                }
            }
            if (input.isTouched(0)) {
                if (input.inputX(0) > (Gdx.graphics.getWidth() / 2)) { //pressing right half of screen
                    player.charRunRight();
                } else { //pressing left half of screen
                    player.charRunLeft();
                }
            }
            // allow for keyboard controls (left and right arrow keys)
            if(Gdx.input.isKeyPressed(22)){
                player.charRunRight();
            } else if (Gdx.input.isKeyPressed(21)){
                player.charRunLeft();
            }
        }
    }
    public void update(float dt)
    {
        stateTime += dt;
        handleInput(dt);

        world.step(1/60f, 6, 2);
        //update character
        player.update(dt);
        //update hud
        hud.update(dt);
        //update level
        curLevel.update(player, dt);

        getPlayerX(dt);

        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y+.75f;

        gamecam.update();
        renderer.setView(gamecam);
    }

    public static float getPlayerX(float dt)
    {
        return player.getX();
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
        if(levelComplete) {
            setCurLevel(curLevel.getNextMap());
        }

        //handles joes level, if the player gets past x=3 goto next level
        if(player.getX() >= 3 && curLevel.getMap().equals("tunnel1.tmx")) {
            game.setScreen(new PlayScreen(game, new SewerLevel()));
            dispose();
        }
        //COMMENTS- if the player dies, sets screen to game over, disposes playscreen
        if(gameOver())
        {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public boolean gameOver()
    {
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

    //Sets the screen to the level passed to it
    //this is called when the "EOL" marker is hit in
    //any level. If the given level is null the end screen
    //is shown.
    public void setCurLevel(String level) {
        if(level == null) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        } else if(level.equals("tunnel1.tmx")) {
            game.setScreen(new PlayScreen(game, new ForestLevel()));
            dispose();
        } else if(level.equals("SewerLevel.tmx")) {
            game.setScreen(new PlayScreen(game, new SewerLevel()));
            dispose();
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
