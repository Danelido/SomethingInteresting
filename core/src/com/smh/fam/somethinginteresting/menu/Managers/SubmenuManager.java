package com.smh.fam.somethinginteresting.menu.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.menu.Submenus.Submenu;
import com.smh.fam.somethinginteresting.menu.Utils.TRANSITIONTYPE;

import java.util.Stack;

/**
 * Created by Alexander on 2017-04-07.
 */

public class SubmenuManager {

    private Stack<Submenu> submenus;
    private OrthographicCamera camera; // For cool transitions

    private float transition_zoomAmount = 5f;
    private final float TRANSITION_ZOOMCONSTANT = 0.3f;
    private final float TRANSITION_MOVECONSTANT = 60.f;
    private TRANSITIONTYPE _TRANSITIONTYPE;

    public SubmenuManager(OrthographicCamera camera)
    {
        submenus = new Stack<Submenu>();
        _TRANSITIONTYPE = TRANSITIONTYPE.NONE;
        this.camera = camera;
    }

    public void updateTransition()
    {
        if(_TRANSITIONTYPE != TRANSITIONTYPE.NONE) {
            // ZOOM
            if (_TRANSITIONTYPE == TRANSITIONTYPE.ZOOM) {
                if (camera.zoom != 1) {
                    camera.zoom -= TRANSITION_ZOOMCONSTANT;
                    if (camera.zoom <= 1.0f) {
                        camera.zoom = 1;
                        _TRANSITIONTYPE = TRANSITIONTYPE.NONE;
                    }
                }
            }
            // FROM RIGHT
            if (_TRANSITIONTYPE == TRANSITIONTYPE.FROM_RIGHT) {
                if (camera.position.x < 0) {
                    camera.translate(TRANSITION_MOVECONSTANT,0,0);
                    //camera.position.x += TRANSITION_CONSTANT;
                    if (camera.position.x >= 0) {
                        camera.position.x = 0;
                        _TRANSITIONTYPE = TRANSITIONTYPE.NONE;
                    }
                }
            }
            // FROM LEFT
            if (_TRANSITIONTYPE == TRANSITIONTYPE.FROM_LEFT) {
                if (camera.position.x > 0) {
                    camera.translate(-TRANSITION_MOVECONSTANT,0,0);
                    //camera.position.x -= TRANSITION_CONSTANT;
                    if (camera.position.x <= 0) {
                        camera.position.x = 0;
                        _TRANSITIONTYPE = TRANSITIONTYPE.NONE;
                    }
                }
            }
        }
    }

    public void update()
    {
        if(!submenus.empty())
        {
            submenus.peek().update();
        }
    }

    public void render(SpriteBatch batch)
    {
        if(!submenus.empty())
        {
            submenus.peek().render(batch);
        }
    }


    public void fingerTouchingScreen(int screenX, int screenY, int pointer, int button) {
        if(!submenus.empty())
        {
            submenus.peek().fingerTouchingScreen(screenX, screenY, pointer, button);
        }
    }

    public void fingerReleasedFromScreen(int screenX, int screenY, int pointer, int button) {
        if(!submenus.empty())
        {
            submenus.peek().fingerReleasedFromScreen(screenX, screenY, pointer, button);
        }
    }

    public void fingerDraggedOnScreen(int screenX, int screenY, int pointer) {
        if(!submenus.empty())
        {
            submenus.peek().fingerDraggedOnScreen(screenX, screenY, pointer);
        }
    }

    public void changeSubmenuTo(Submenu newSubMenu, TRANSITIONTYPE _TRANSITIONTYPE)
    {

        if(!submenus.empty())
        {
            this._TRANSITIONTYPE = _TRANSITIONTYPE;
            switch(_TRANSITIONTYPE)
            {
                case ZOOM:
                    camera.zoom = transition_zoomAmount;
                    break;

                case FROM_LEFT:
                    camera.position.x = CoreValues_Static.VIRTUAL_WIDTH;
                    break;

                case FROM_RIGHT:
                    camera.position.x = -CoreValues_Static.VIRTUAL_WIDTH;
                    break;
            }
            submenus.pop();

        }
        submenus.push(newSubMenu);
    }

    public void dispose()
    {
        if(!submenus.empty())
        {
            submenus.peek().dispose();
        }
    }






}
