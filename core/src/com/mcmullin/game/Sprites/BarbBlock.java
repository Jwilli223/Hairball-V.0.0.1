package com.mcmullin.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;

/**
 * Created by Produit on 4/16/2017.
 */

public class BarbBlock extends RoadBlock {
    TextureAtlas atlas = new TextureAtlas("tunnelPics/tunnelAtlas.atlas");
    public BarbBlock(PlayScreen screen, MapObject object) {
        super(screen, object);
        setRegion(new TextureRegion(atlas.findRegion("barbSmol")));
    }

    @Override
    public void defineRB() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        FixtureDef fixture = new FixtureDef();
        CircleShape fixShape = new CircleShape();
        fixShape.setRadius(12 / MyGdxGame.PPM);
        //----------this code makes the collision box a square rather than a circle----
        //PolygonShape fixShape = new PolygonShape();
        //fixShape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
        fixture.shape = fixShape;
        body.createFixture(fixture);
    }

    @Override
    public boolean kill() {
        return false;
    }

    @Override
    public void update(float dt) {
        setRegion(new TextureRegion(atlas.findRegion("barbSmol")), 0, 0, 120, 75);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
