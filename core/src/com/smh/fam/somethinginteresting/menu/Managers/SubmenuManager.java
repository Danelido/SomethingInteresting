package com.smh.fam.somethinginteresting.menu.Managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smh.fam.somethinginteresting.menu.Submenus.Submenu;
import java.util.Stack;

/**
 * Created by Alexander on 2017-04-07.
 */

public class SubmenuManager {

    private Stack<Submenu> submenus;

    public SubmenuManager()
    {
        submenus = new Stack<Submenu>();
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

    public void changeSubmenuTo(Submenu newSubMenu)
    {
        if(!submenus.empty())
        {
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
