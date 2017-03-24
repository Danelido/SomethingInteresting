package com.smh.fam.somethinginteresting.game.State;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Alexander on 2017-03-24.
 */

public abstract class GameState  {

    protected GameStateManager gsm;

    protected GameState(GameStateManager gsm)
    {
        this.gsm = gsm;
        init();
    }

    public abstract void init();
    public abstract void update();
    public abstract void render(SpriteBatch batch);





}
