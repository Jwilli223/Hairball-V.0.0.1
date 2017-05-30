package com.mcmullin.game.Screens;

import com.mcmullin.game.Levels.*;
import com.mcmullin.game.MyGdxGame;

/**
 * Created by Jared on 5/5/2017.
 */

public class ScreenTools {
    //Sets the screen to the level passed to it
    //this is called when the "EOL" marker is hit in
    //any level. If the given level is null the winner screen
    //is shown.
    public static void setLevel(MyGdxGame game, String level, int deaths) {
        if(level.equals("end")) {
            game.setScreen(new WinnerScreen(game));
        } else if(level.equals("SewerFinal.tmx")) {
            game.setScreen(new PlayScreen(game, new SewerLevel(), deaths));
        }else if(level.equals("rubylevel.tmx")) {
            game.setScreen(new PlayScreen(game, new DinoLevel(), deaths));
        } else if(level.equals("spacelevelTEST.tmx")) {
            game.setScreen(new PlayScreen(game, new SpaceLevel(), deaths));
        } else if(level.equals("corelevel.tmx")) {
            game.setScreen(new PlayScreen(game, new CoreLevel(), deaths));
        }
    }
}
