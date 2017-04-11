package com.smh.fam.somethinginteresting.menu.Stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.CoordinateTransformer;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;
import com.smh.fam.somethinginteresting.game.State.PlayState;
import com.smh.fam.somethinginteresting.menu.Abstracts.Stage;
import com.smh.fam.somethinginteresting.menu.Utils.Level;

import java.util.ArrayList;

/**
 * Created by Alexander on 2017-04-11.
 */

public class Earth extends Stage{

    private ArrayList<Level> levels;
    private float container_size_x,container_size_y;

    public Earth(GameStateManager gsm, TextureStorage textureStorage) {
        super(gsm, textureStorage);
    }

    @Override
    public void init() {
        levels = new ArrayList<Level>();
        container_size_x = 150f;
        container_size_y = 100f;
        int lvl = 1;
      
        for(int y = 1; y <= 2; y++)
        {
            for(int x = 1; x <= 5; x++)
            {
                levels.add(new Level(
                        new Vector2((CoreValues_Static.VIRTUAL_WIDTH/2 - (container_size_x*4.5f)) + ((container_size_x+50) * x),
                                (CoreValues_Static.VIRTUAL_HEIGHT/2 - (container_size_y/2)) + ((container_size_y + 100) * (y-1))),
                        new Vector2(container_size_x,container_size_y),
                        "menuResources/leveltextures/leveltexture_"+(lvl)+".png",
                        textureStorage,
                        "levels/level_"+lvl+".xml"));
                lvl++;
            }
        }

        for(int i = 0; i < levels.size(); i++)
        {
            levels.get(i).setNewMaxScaleValue(1.2f, 1.2f);
        }

    }

    @Override
    public void update() {


    }

    @Override
    public void render(SpriteBatch batch) {
        if(!levels.isEmpty())
            {
                for(int i = 0; i < levels.size(); i++)
                {
                levels.get(i).render(batch);
            }
        }
    }

    @Override
    public void fingerTouchingScreen(int screenX, int screenY, int pointer, int button) {
        Vector2 touched = CoordinateTransformer.convertToGameCoords(screenX, screenY, 1.0f);
        if(!levels.isEmpty())
        {
            for(int i = 0; i < levels.size(); i++)
            {
                levels.get(i).beingPressed(touched);
            }
        }
    }

    @Override
    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button) {
        Vector2 touched = CoordinateTransformer.convertToGameCoords(screenX, screenY, 1.0f);
        if(!levels.isEmpty())
        {
            for(int i = 0; i < levels.size(); i++)
            {
                if(levels.get(i).beingPressed(touched))
                {
                    startLevel(levels.get(i).getPathToLevel());
                }
            }
        }
    }

    @Override
    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer) {

    }


    private void startLevel(String LevelPath)
    {

        gsm.changeStateTo(new PlayState(gsm, LevelPath));
    }

}
