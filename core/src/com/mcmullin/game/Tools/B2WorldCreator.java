package com.mcmullin.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;
import com.mcmullin.game.Sprites.Enemy;
import com.mcmullin.game.Sprites.Log;
import com.mcmullin.game.Sprites.River;
import com.mcmullin.game.Sprites.Skeleton;
import com.mcmullin.game.Sprites.Squirrel;

/**
 * Created by Joe on 11/12/2016.
 */

public class B2WorldCreator {
    private Array<Skeleton> skeleton;
    private Array<River> river;
    private Array<Log> log;
    private Array<Squirrel> squirrel;

    public B2WorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //COMMENTS- This creates the ground in tiled. 1 is the index (starts at 0- map layer and goes up vertically).
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //COMMENTS- Can ignore
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MyGdxGame.OBJECT_BIT;
            body.createFixture(fdef);
        }
        //COMMENTS- Part 1 of skeleton spawning with tiled
        skeleton = new Array<Skeleton>();
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            skeleton.add(new Skeleton(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }

        river = new Array<River>();
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            river.add(new River(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }

        log = new Array<Log>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            log.add(new Log(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }

        squirrel = new Array<Squirrel>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            squirrel.add(new Squirrel(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }


    }
    //COMMENTS- Part 2 of skeleton spawning with tiled. There are two other pieces of code in playscreen that are required along with these.
    public Array<Skeleton> getSkeleton() {
        return skeleton;
    }
    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(skeleton);
        return enemies;
    }

    public Array<River> getRiver() {
        return river;
    }
    public Array<Enemy> getEnemies2(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(river);
        return enemies;
    }

    public Array<Log> getLog() {
        return log;
    }
    public Array<Enemy> getEnemies3(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(log);
        return enemies;
    }

    public Array<Squirrel> getSquirrel() {
        return squirrel;
    }
    public Array<Enemy> getEnemies4(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(squirrel);
        return enemies;
    }



}



