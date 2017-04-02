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
            case MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.CHAR_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)

                    ((Enemy)fixA.getUserData()).hitOnHead();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixB.getUserData()).hitOnHead();

                break;
            case MyGdxGame.LOG_BIT| MyGdxGame.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                    //Char.setTouch(true);
                    //Gdx.app.log("you have been", "boned");
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                // Gdx.app.log("you have been", "boned22");
                //Char.setTouch(true);
                break;
            case MyGdxGame.ENEMY_BIT | MyGdxGame.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
                break;
            case MyGdxGame.CHAR_BIT | MyGdxGame.ENEMY_BIT:
                Char.setHit(1);
                break;
            case MyGdxGame.CHAR_BIT| MyGdxGame.LOG_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.LOG_BIT)
                    //((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                    //Char.setTouch(true);
                    Gdx.app.log("you have been", "bon");
                    //Gdx.app.log("you have been", "bonooot");
                else
                    //((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                    //Gdx.app.log("you have been", "boned22");
                    Gdx.app.log("you have been", "bont");
                Char.setTouch(1);
                break;
           /* case MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.FIREBALL_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)

                    ((Enemy)fixA.getUserData()).hitOnHead();
                else if (fixB.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixB.getUserData()).hitOnHead();

                break;*/
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
