package com.mcmullin.game.Levels;

import com.mcmullin.game.MyGdxGame;
import com.mcmullin.game.Screens.PlayScreen;
import com.mcmullin.game.Sprites.*;

import java.util.List;


/**
 * Created by Produit on 4/8/2017.
 */

public abstract class Level {
    //name of .tmx file related to this level
    protected String map;
    //this is the next map in the sequence (null if it is the final map)
    protected String nextMap;
    //map object that marks the end of the map/level
    protected levelEnd end;
    protected LevelStart start;
    // level name for hud
    protected String levelName;
    //list of all roadbloacks
    protected  List<RoadBlock> roadBlocks;

    public String getMap() {return map;}

    public String getNextMap() {return nextMap;}

    public String getLevelName() {return levelName;}

    public float getEndX() {return end.getX();}

    public float getStartX() {return start.getX();}

    public float getStartY() {return start.getY();}

    public void create(PlayScreen screen) {}

    public void update(Char player, float dt) {}

    public void render(MyGdxGame game){}
}
