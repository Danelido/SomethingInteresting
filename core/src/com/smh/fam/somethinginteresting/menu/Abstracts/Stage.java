package com.smh.fam.somethinginteresting.menu.Abstracts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;

/**
 * Created by Alexander on 2017-04-11.
 */

public abstract class Stage {

    protected TextureStorage textureStorage;
    protected GameStateManager gsm;

    public Stage(GameStateManager gsm, TextureStorage textureStorage)
    {
        this.gsm = gsm;
        this.textureStorage = textureStorage;
        init();
    }

    public abstract void init();
    public abstract void update();
    public abstract void render(SpriteBatch batch);

    public abstract void fingerTouchingScreen(int screenX, int screenY, int pointer, int button);
    public abstract void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button);
    public abstract void fingerDraggedOnScreen(int screenX, int screenY, int pointer);




}
