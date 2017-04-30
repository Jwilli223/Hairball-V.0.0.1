package com.mcmullin.game.Levels;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;
import com.mcmullin.game.Sprites.*;
import java.util.ArrayList;
import com.mcmullin.game.Sprites.Char;
import java.util.List;


/**
 * Created by Jared on 4/8/2017.
 */

public class SewerLevel extends Level{
    private List<Sprite> RBs;

    public SewerLevel() {
        this.map = "SewerLevel2.tmx";
        this.nextMap = "rubylevel.tmx"; //currently last map
        this.levelName = "Dank Sewer";
        RBs = new ArrayList<Sprite>();
    }

    public void create(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapLayer layer: map.getLayers()) {
            //COMMENTS- This creates the ground in tiled
            if(layer.getName().equals("platforms")) { //builds platforms layer
                for(MapObject object: layer.getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);
                    body = world.createBody(bdef);
                    shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
                    fdef.shape = shape;
                    body.createFixture(fdef);
                }
            } else if (layer.getName().equals("EOL")) { //builds EOL
                for(MapObject object: layer.getObjects()) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    if(object.getName().equals("level end")) {
                        this.end = new levelEnd(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM);
                    } else if (object.getName().equals("level start")) {
                        this.start = new LevelStart(rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM);
                    }
                }
            } else if (layer.getName().equals("RB")) {
                for(MapObject object: layer.getObjects()) {
                    if(object.getName().equals("barb")) {
                        RBs.add(new Barb(screen, object));
                    }
                }
            }
        }
    }

    public void update(Char player, float dt) {end.update(player);}

    public void render(MyGdxGame game){}
}
