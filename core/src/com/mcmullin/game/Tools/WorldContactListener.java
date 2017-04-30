package com.mcmullin.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Sprites.Char;
import com.mcmullin.game.Sprites.Enemy;
import com.mcmullin.game.Sprites.Skeleton;

/**
 * Created by Joe on 11/13/2016.
 */
//COMMENTS- This class handles collision type events

public class WorldContactListener implements ContactListener {
    @Override

    public void beginContact(Contact contact) {
        //Gdx.app.log("Start Contact", "");
        //Gdx.app.log("Begin Contact", "");
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head")
        {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;
        }

        switch (cDef)
        {
            case MyGdxGame.CHAR_BIT | MyGdxGame.ENEMY_BIT:
                Char.setHit();
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
