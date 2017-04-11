package com.smh.fam.somethinginteresting.menu.Abstracts;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;
import com.smh.fam.somethinginteresting.menu.Managers.SubmenuManager;

/**
 * Created by Alexander on 2017-04-07.
 */

public abstract class Submenu {

    protected GameStateManager gsm;     // Able to change Gamestate in every submenu if needed
    protected SubmenuManager smm;       // Able to change to a different Submenu in every submenu
    protected Camera camera;            // Every submenu uses the same camera (initialized in "menu/Core/MenuState"), efficiency!

    protected TextureStorage textureStorage;

    public Submenu(GameStateManager gsm, SubmenuManager smm, Camera camera, TextureStorage textureStorage){
        this.gsm = gsm;
        this.smm = smm;
        this.camera = camera;
        this.textureStorage = textureStorage;
        init();
    }

    public abstract void init();

    public abstract void update();
    public abstract void render(SpriteBatch batch);

    public abstract void fingerTouchingScreen(int screenX, int screenY, int pointer, int button);
    public abstract void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button);
    public abstract void fingerDraggedOnScreen(int screenX, int screenY, int pointer);

    public abstract void dispose();




}
