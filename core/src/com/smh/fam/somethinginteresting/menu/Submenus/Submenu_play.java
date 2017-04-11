package com.smh.fam.somethinginteresting.menu.Submenus;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.CoordinateTransformer;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;
import com.smh.fam.somethinginteresting.menu.Enums.BUTTON_LIST;
import com.smh.fam.somethinginteresting.menu.Enums.TRANSITIONTYPE;
import com.smh.fam.somethinginteresting.menu.Managers.SubmenuManager;
import com.smh.fam.somethinginteresting.menu.Stages.Earth;
import com.smh.fam.somethinginteresting.menu.Utils.Button;

/**
 * Created by Alexander on 2017-04-11.
 */

public class Submenu_play extends com.smh.fam.somethinginteresting.menu.Abstracts.Submenu {

    private Earth earth;
    private Button homeButton;

    public Submenu_play(GameStateManager gsm, SubmenuManager smm, Camera camera, TextureStorage textureStorage) {super(gsm, smm, camera, textureStorage);}

    @Override
    public void init() {
        earth = new Earth(gsm, textureStorage);
        homeButton = new Button(new Vector2(10,93), new Vector2(100,83),"menuResources/button_home.png", false, BUTTON_LIST.SUBMENU_PLAY_HOME);
    }

    @Override
    public void update() {
        earth.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        homeButton.render(batch);
        earth.render(batch);
    }

    @Override
    public void fingerTouchingScreen(int screenX, int screenY, int pointer, int button) {
        Vector2 touched = CoordinateTransformer.convertToGameCoords(screenX, screenY, 1.0f);
        homeButton.beingPressed(touched);
        earth.fingerTouchingScreen(screenX, screenY, pointer, button);
    }

    @Override
    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button) {
        Vector2 touched = CoordinateTransformer.convertToGameCoords(screenX, screenY, 1.0f);
        if(homeButton.beingPressed(touched))
        {
            smm.changeSubmenuTo(new Submenu_main(gsm,smm,camera,textureStorage), TRANSITIONTYPE.FROM_LEFT);
        }
        earth.fingerReleasedFromScreen(screenX, screenY, pointer, button);
    }

    @Override
    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer) {
        earth.fingerDraggedOnScreen(screenX, screenY, pointer);
    }

    @Override
    public void dispose() {

    }
}
