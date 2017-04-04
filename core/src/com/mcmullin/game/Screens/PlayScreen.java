package com.mcmullin.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Scenes.Hud;
import com.mcmullin.game.Sprites.Char;
import com.mcmullin.game.Sprites.Enemy;
import com.mcmullin.game.Sprites.Skeleton;
import com.mcmullin.game.Tools.B2WorldCreator;
import com.mcmullin.game.Tools.WorldContactListener;
import com.mcmullin.game.Movement.TouchInputProcessor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joe on 11/12/2016.
 */

public class PlayScreen implements Screen {
    private MyGdxGame game;
    private TextureAtlas atlas;
    private TextureAtlas atlas2;
    private TextureAtlas atlas3;
    private TextureAtlas atlas4;
    private TextureAtlas atlas5;
    private TextureAtlas atlas6;
    private TextureAtlas atlas7;
    private TextureAtlas atlas11;
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

    TouchInputProcessor input;


    public PlayScreen(MyGdxGame game) {
        //COMMENTS- all atlases load a sprite sheet. Variable declared above and getAtlases down below. These atlases are referenced in the sprite
        //classes to handle animations.
        atlas= new TextureAtlas("idrun.txt");
        atlas2 = new TextureAtlas("grass.txt");
        atlas3 = new TextureAtlas("ballstance.txt");
        atlas4 = new TextureAtlas("dead.txt");
        atlas5 = new TextureAtlas("river.atlas");
        atlas6 = new TextureAtlas("platform.atlas");
        atlas7 = new TextureAtlas("squirrel.atlas");
        atlas11 = new TextureAtlas("jumper.txt");
        this.game = game;
        //COMMENTS- Camera set up
        gamecam = new OrthographicCamera();
        gamePort = new ExtendViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM,MyGdxGame.V_HEIGHT / MyGdxGame.PPM,gamecam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        //COMMENTS- Load tiled map file here
        map = maploader.load("SewerLevel.tmx");

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

    public TextureAtlas getAtlas()
    {
        return atlas;
    }
    public TextureAtlas getAtlas2()
    {
        return atlas2;
    }
    public TextureAtlas getAtlas3()
    {
        return atlas3;
    }
    public TextureAtlas getAtlas4()
    {
        return atlas4;
    }
    public TextureAtlas getAtlas5()
    {
        return atlas5;
    }
    public TextureAtlas getAtlas6()
    {
        return atlas6;
    }
    public TextureAtlas getAtlas7()
    {
        return atlas7;
    }
    public TextureAtlas getAtlas11()
    {
        return atlas11;
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

        player.update(dt);
        hud.update(dt);

        //COMMENTS- this code is required to spawn enemies or whatever we choose to add using tiled
        for(Enemy enemy : creator.getEnemies())
        {
            enemy.update(dt);
            if(!enemy.isDestroyed() && enemy.getX() < player.getX() + 224 / MyGdxGame.PPM)
                enemy.b2body.setActive(true);
        }
        for(Enemy enemy : creator.getEnemies2())
        {
            enemy.update(dt);
            if(!enemy.isDestroyed() && enemy.getX() < player.getX() + 224 / MyGdxGame.PPM)
                enemy.b2body.setActive(true);
        }
        for(Enemy enemy : creator.getEnemies3())
        {
            enemy.update(dt);
            if(!enemy.isDestroyed() && enemy.getX() < player.getX() + 224 / MyGdxGame.PPM)
                enemy.b2body.setActive(true);
        }

        for(Enemy enemy : creator.getEnemies4())
        {
            enemy.update(dt);
            if(!enemy.isDestroyed() && enemy.getX() < player.getX() + 224 / MyGdxGame.PPM)
                enemy.b2body.setActive(true);
        }

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

        renderer.render();

        //b2dr.render(world, gamecam.combined); //box2d debug render - Can uncomment and it shows the box2d debug lines/shapes
        game.batch.enableBlending();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        //COMMNENTS- Required for enemy tiled spawn.
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for (Enemy enemy : creator.getEnemies2())
            enemy.draw(game.batch);
        for (Enemy enemy : creator.getEnemies3())
            enemy.draw(game.batch);
        for (Enemy enemy : creator.getEnemies4())
            enemy.draw(game.batch);

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
