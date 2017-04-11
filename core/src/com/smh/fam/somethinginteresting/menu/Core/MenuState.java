package com.smh.fam.somethinginteresting.menu.Core;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.State.GameState;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;
import com.smh.fam.somethinginteresting.menu.Managers.SubmenuManager;
import com.smh.fam.somethinginteresting.menu.Submenus.Submenu_main;
import com.smh.fam.somethinginteresting.menu.Utils.TRANSITIONTYPE;

import java.io.FileNotFoundException;

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
    public static TextureStorage textureStorage;
    private SubmenuManager smm;

    private Texture backgroundImage;

     private Vector2 camera_momentum = new Vector2(0.0f, 0.0f);
     private float camera_momentumDecay = 0.001f;

    public MenuState(GameStateManager gsm) {super(gsm);}

    @Override
    public void init() {
        inputHandler();
        camera = new OrthographicCamera(CoreValues_Static.VIRTUAL_WIDTH, CoreValues_Static.VIRTUAL_HEIGHT);
        textureStorage = new TextureStorage();

        try {
            backgroundImage = textureStorage.getTexture("menuResources/background.png");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        smm = new SubmenuManager(camera);
        smm.changeSubmenuTo(new Submenu_main(gsm,smm,camera, textureStorage), TRANSITIONTYPE.NONE);
        Gdx.input.setInputProcessor(inputProcessor); // Registers input from our "inputProcessor" variable
    }

    @Override
    public void update() {
        float deltaT = Gdx.graphics.getDeltaTime();
        smm.updateTransition();

        camera.update(); // Recalculate matrices and such

        smm.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined); // Give batch the calculated matrices, has to be done before batch.begin()
        batch.begin();
        batch.draw(backgroundImage,((-CoreValues_Static.VIRTUAL_WIDTH * camera.zoom)/2) + camera.position.x,((-CoreValues_Static.VIRTUAL_HEIGHT* camera.zoom)/2) + camera.position.y, CoreValues_Static.VIRTUAL_WIDTH * camera.zoom, CoreValues_Static.VIRTUAL_HEIGHT * camera.zoom);
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
