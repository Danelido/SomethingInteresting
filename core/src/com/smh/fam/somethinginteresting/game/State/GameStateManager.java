package com.smh.fam.somethinginteresting.game.State;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smh.fam.somethinginteresting.game.Core.GdxGameCore;

import java.util.Stack;

/**
 * Created by Alexander on 2017-03-24.
 */

public class GameStateManager {

    private Stack<GameState> Gamestates;

        public GameStateManager()
        {
            Gamestates = new Stack<GameState>();
        }

        public void update()
        {
            if(!Gamestates.empty())
                Gamestates.peek().update();
        }

        public void render(SpriteBatch batch)
        {
            if(!Gamestates.empty())
                Gamestates.peek().render(batch);
        }

        public void dispose()
        {
            if(!Gamestates.empty())
                Gamestates.peek().dispose();
        }

        public void changeStateTo(GameState newState)
        {
            if(!Gamestates.empty()){
                Gamestates.pop();
            }
            Gamestates.push(newState);
        }


}
