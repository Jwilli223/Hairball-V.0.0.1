package com.mcmullin.game.Levels;

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
import com.mcmullin.game.Sprites.Char;
import com.mcmullin.game.Sprites.levelEnd;

/**
 * Created by wenzi on 4/24/2017.
 */

public class DinoLevel extends Level {
    public DinoLevel() {
        this.map = "rubylevel.tmx";
        this.nextMap = null; //currently last map
        this.levelName = "Fantastic Fossils";
    }

    public void create(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //COMMENTS- This creates the ground in tiled
        for(MapLayer layer: map.getLayers()) {
            if(layer.getName().equals("ground")) { //builds platforms layer
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
                MapObject endObject = layer.getObjects().get("levelend"); //this is the object drawn in tiled
                Rectangle rect = ((RectangleMapObject) endObject).getRectangle();
                this.end = new levelEnd(screen, rect.getX()/MyGdxGame.PPM, rect.getY()/MyGdxGame.PPM);
            }
        }
    }

    public void update(Char player, float dt) {
        end.update(player);
    }

    public void render(MyGdxGame game){}
}