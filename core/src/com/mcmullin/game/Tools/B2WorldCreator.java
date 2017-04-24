package com.mcmullin.game.Tools;
import com.mcmullin.game.Screens.PlayScreen;

/**
 * Created by Joe on 11/12/2016.
 */

public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen)
    {
        screen.getCurLevel().create(screen);
    }
}



