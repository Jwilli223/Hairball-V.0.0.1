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
import com.mcmullin.game.Levels.ForestLevel;
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

    public B2WorldCreator(PlayScreen screen)
    {
        screen.getCurLevel().create(screen);
    }
}



