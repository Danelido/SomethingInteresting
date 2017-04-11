package com.smh.fam.somethinginteresting.menu.Submenus;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.CoordinateTransformer;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;
import com.smh.fam.somethinginteresting.menu.Enums.BUTTON_LIST;
import com.smh.fam.somethinginteresting.menu.Enums.TRANSITIONTYPE;
import com.smh.fam.somethinginteresting.menu.Managers.SubmenuManager;
import com.smh.fam.somethinginteresting.menu.Utils.Button;

import java.util.ArrayList;

/**
 * Created by Alexander on 2017-04-07.
 */

public class Submenu_main extends com.smh.fam.somethinginteresting.menu.Abstracts.Submenu {

    private ArrayList<Button> buttons;

    public Submenu_main(GameStateManager gsm, SubmenuManager smm, Camera camera,TextureStorage textureStorage) {super(gsm, smm, camera,textureStorage);}

    @Override
    public void init() {
        buttons = new ArrayList<Button>();
        addButton((int)CoreValues_Static.VIRTUAL_WIDTH/2, (int)(CoreValues_Static.VIRTUAL_HEIGHT/2)- 50,260,100, "menuResources/button_play.png", true, BUTTON_LIST.SUBMENU_MAIN_PLAY);
        addButton((int)CoreValues_Static.VIRTUAL_WIDTH/2, (int)CoreValues_Static.VIRTUAL_HEIGHT/2 + 100,260,100, "menuResources/button_settings.png", true, BUTTON_LIST.SUBMENU_MAIN_SETTINGS);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        if(!buttons.isEmpty())
        {
            for(int i = 0; i < buttons.size(); i++)
            {
                buttons.get(i).render(batch);
            }
        }
    }

    private void handleButtonEvents(BUTTON_LIST _BUTTON_TYPE)
    {
        switch(_BUTTON_TYPE ){
            // Play
            case SUBMENU_MAIN_PLAY:
               smm.changeSubmenuTo(new Submenu_play(gsm, smm, camera, textureStorage), TRANSITIONTYPE.FROM_RIGHT);
                break;
            // Settings
            case SUBMENU_MAIN_SETTINGS:
                smm.changeSubmenuTo(new Submenu_settings(gsm,smm,camera,textureStorage), TRANSITIONTYPE.FROM_RIGHT);
                break;

        }
    }

    @Override
    public void fingerTouchingScreen(int screenX, int screenY, int pointer, int button) {
        Vector2 touched = CoordinateTransformer.convertToGameCoords(screenX, screenY, 1.0f);
        if(!buttons.isEmpty()) {
            for(int i = 0; i < buttons.size(); i++)
            {
                if(buttons.get(i).beingPressed(touched)) return;
            }
        }

    }

    @Override
    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button) {
        Vector2 touched = CoordinateTransformer.convertToGameCoords(screenX, screenY, 1.0f);
        if(!buttons.isEmpty()){
            for(int i = 0; i < buttons.size(); i++) {
                if(buttons.get(i).isActivated()) {
                    if(buttons.get(i).beingPressed(touched))
                    {
                        handleButtonEvents(buttons.get(i).getButtonType());
                    }
                }
            }
        }

    }

    @Override
    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer) {

    }

    @Override
    public void dispose() {

    }


    private void addButton(int x, int y, int width, int height, String textureFilePath, boolean positionOriginCentered, BUTTON_LIST _BUTTON_TYPE)
    {
        buttons.add(new Button(
                new Vector2(x, y),
                new Vector2(width,height),
                textureFilePath,
                positionOriginCentered,
                _BUTTON_TYPE));
    }

}
