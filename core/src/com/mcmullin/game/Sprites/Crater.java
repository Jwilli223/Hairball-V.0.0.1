package com.mcmullin.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;

/**
 * Created by jenni on 5/2/2017.
 */

public class Crater extends RoadBlock {
    public Crater(PlayScreen screen, MapObject object) {
        super(screen, object);
    }

    //sets body as a circle w/ radius 24
    public void defineRB() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        FixtureDef fixture = new FixtureDef();
        CircleShape fixShape = new CircleShape();
        fixShape.setRadius(24 / MyGdxGame.PPM);
        //is an enemy
        fixture.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        //collide with char
        fixture.filter.maskBits = MyGdxGame.CHAR_BIT;
        fixture.shape = fixShape;
        body.createFixture(fixture);
    }
}
