package com.smh.fam.somethinginteresting.menu.Core;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.State.GameState;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;
import com.smh.fam.somethinginteresting.menu.Managers.SubmenuManager;
import com.smh.fam.somethinginteresting.menu.Submenus.Submenu_main;

/**
 * Created by Alexander on 2017-04-07.
 */


 /*************************************************************************************************************************************
 * Note that this class works as our "/game/core/GdxGameCore" and the coding for each submenu gets done in their own individual class *
 * Also the startup menu is labeled as submenu_main in the "submenus"-package                                                         *
 **************************************************************************************************************************************/

public class MenuState extends GameState {

    private InputProcessor inputProcessor;
    private OrthographicCamera camera;
    private TextureStorage textureStorage;
    private SubmenuManager smm;



    public MenuState(GameStateManager gsm) {super(gsm);}

    @Override
    public void init() {
        inputHandler();
        camera = new OrthographicCamera(CoreValues_Static.VIRTUAL_WIDTH, CoreValues_Static.VIRTUAL_HEIGHT);
        textureStorage = new TextureStorage();
        smm = new SubmenuManager();
        smm.changeSubmenuTo(new Submenu_main(gsm,smm,camera, textureStorage));

        Gdx.input.setInputProcessor(inputProcessor); // Registers input from our "inputProcessor" variable
    }

    @Override
    public void update() {
        camera.update(); // Recalculate matrices and such
        smm.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined); // Give batch the calculated matrices, has to be done before batch.begin()
        batch.begin();
        smm.render(batch);
        batch.end();
    }

    private void inputHandler()
    {
        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                smm.fingerTouchingScreen(screenX, screenY, pointer,button);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                smm.fingerReleasedFromScreen(screenX, screenY, pointer,button);
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                smm.fingerDraggedOnScreen(screenX, screenY, pointer);
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };

    }

    @Override
    public void dispose() {

        smm.dispose();
    }
}
