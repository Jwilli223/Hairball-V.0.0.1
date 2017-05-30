package com.mcmullin.game.Screens;

import com.mcmullin.game.Levels.*;
import com.mcmullin.game.MyGdxGame;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.exit;

/**
 * Created by Jared on 5/5/2017.
 */

public class ScreenTools {
    //Sets the screen to the level passed to it
    //this is called when the "EOL" marker is hit in
    //any level. If the given level is null the winner screen
    //is shown.
    public static void setLevel(MyGdxGame game, String level) {
        if(level.equals("end")) {
            game.setScreen(new WinnerScreen(game));
        } else if(level.equals("SewerFinal.tmx")) {
            game.setScreen(new PlayScreen(game, new SewerLevel()));
        }else if(level.equals("rubylevel.tmx")) {
            game.setScreen(new PlayScreen(game, new DinoLevel()));
        } else if(level.equals("spacelevelFINAL.tmx")) {
            game.setScreen(new PlayScreen(game, new SpaceLevel()));
        } else if(level.equals("corelevel.tmx")) {
            game.setScreen(new PlayScreen(game, new CoreLevel()));
        }
        else{
            game.setScreen(new PlayScreen(game, new SpaceLevel()));
        }
    }
}
