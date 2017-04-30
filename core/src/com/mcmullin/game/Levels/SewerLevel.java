package com.mcmullin.game.Levels;

<<<<<<< HEAD
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
=======
import com.badlogic.gdx.graphics.g2d.Sprite;
>>>>>>> refs/remotes/origin/master
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
<<<<<<< HEAD
import com.mcmullin.game.Sprites.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;



import java.util.ArrayList;
=======
import com.mcmullin.game.Sprites.Char;
import com.mcmullin.game.Sprites.*;

import java.util.ArrayList;
import java.util.List;
>>>>>>> refs/remotes/origin/master

/**
 * Created by Jared on 4/8/2017.
 */

public class SewerLevel extends Level{
<<<<<<< HEAD
    private PlayScreen screen;
    private Sprite sprite;
=======
    private List<Sprite> RBs;

>>>>>>> refs/remotes/origin/master
    public SewerLevel() {
        this.map = "SewerLevel2.tmx";
        this.nextMap = "rubylevel.tmx"; //currently last map
        this.levelName = "Dank Sewer";
<<<<<<< HEAD
        this.roadBlocks = new ArrayList<RoadBlock>();
=======
        RBs = new ArrayList<Sprite>();
>>>>>>> refs/remotes/origin/master
    }

    public void create(PlayScreen screen) {
        this.screen = screen;
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
<<<<<<< HEAD
            }
            if (layer.getName().equals("EOL")) { //builds EOL
                MapObject endObject = layer.getObjects().get("level end"); //this is the object drawn in tiled
                Rectangle rect = ((RectangleMapObject) endObject).getRectangle();
                this.end = new levelEnd(screen, rect.getX()/MyGdxGame.PPM, rect.getY()/MyGdxGame.PPM);
=======
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
>>>>>>> refs/remotes/origin/master
            }
            if(layer.getName().equals("blocks")) {
                for (MapObject block: layer.getObjects()) {
                    if(block.getName().equals("barb")) {
                        //TextureAtlas atlas = new TextureAtlas("tunnelPics/tunnelAtlas.atlas");
                        //TextureRegion texture = atlas.findRegion("barbSmol");
                        roadBlocks.add(new BarbBlock(screen, block));
                    }
                }
            }
        }
    }

<<<<<<< HEAD
    public void update(Char player, float dt) {
        end.update(player);
        for (RoadBlock block: roadBlocks) {
            block.update(dt);
        }
    }
=======
    public void update(Char player, float dt) {end.update(player);}
>>>>>>> refs/remotes/origin/master

    public void render(MyGdxGame game){
        for (RoadBlock block: roadBlocks) {
            block.draw(game.batch);
        }
    }
}
