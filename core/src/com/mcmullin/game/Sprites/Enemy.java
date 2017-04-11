package com.mcmullin.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mcmullin.game.Screens.PlayScreen;

/**
 * Created by Joe on 11/14/2016.
 */

public abstract class Enemy extends Sprite
{

    protected World world;
    protected PlayScreen screen;
    protected TextureAtlas atlas;
    protected float stateTime;
    public Body b2body;
    public Vector2 velocity;
    protected boolean destroyed;

    public Enemy(PlayScreen screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(-.5f,0);
    }
    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract  void hitOnHead();
    public abstract void hitByEnemy(Enemy enemy);

    public void reverseVelocity(boolean x, boolean y)
    {
        if(x) {
            {
                if (velocity.x > 0)
                    velocity = new Vector2(-.5f, 0);

                else {
                    velocity = new Vector2(+.5f, 0);
                }
            }
        }
        if(y)
            velocity.y = -velocity.y;
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public void chasePlayer(Enemy enemy)
    {
        if(getX()==enemy.getX())
        {
            Gdx.app.log("test", "");
        }

    }
}