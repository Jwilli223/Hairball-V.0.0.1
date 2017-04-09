package com.mcmullin.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcmullin.game.Screens.PlayScreen;

/**
 * Created by Jared on 4/8/2017.
 */

public class levelEnd extends Sprite {
    PlayScreen screen;

    public levelEnd (PlayScreen screen, float x, float y) {
        this.screen = screen;
        setPosition(x, y);
    }

    //checks if the player has met the end of the level
    //if they have, marks the level as complete in playscreen
    public void update(Char player) {
        if(this.getX() <= player.getX()) {
            screen.setLevelComplete(true);
        }
    }
}
