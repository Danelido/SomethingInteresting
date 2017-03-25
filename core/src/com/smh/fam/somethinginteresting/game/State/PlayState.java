package com.smh.fam.somethinginteresting.game.State;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.smh.fam.somethinginteresting.game.Core.Box2D_Simulator;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.Game.Obstacle;
import com.smh.fam.somethinginteresting.game.Game.Player;


/**
 * Created by Alexander on 2017-03-24.
 */

public class PlayState extends GameState {

    private Camera camera;

    private Box2D_Simulator box2D_simulator;
    private TextureStorage textureStorage;
    private InputProcessor inputProcessor;

    private Vector2 camera_firstTouch = new Vector2(); // belonds to camera movement code, might be temporary

    protected PlayState(GameStateManager gsm) {
        super(gsm);
    }

    private Player player;
    Array<Obstacle> obstacles;

    @Override
    public void init() {
        inputHandler();
        camera = new OrthographicCamera(CoreValues_Static.VIRTUAL_WIDTH, CoreValues_Static.VIRTUAL_HEIGHT);
        box2D_simulator = new Box2D_Simulator();
        textureStorage = new TextureStorage();


        player = new Player(box2D_simulator.getWorld(), textureStorage, new Vector2(0.f, 0.f), camera);
        Obstacle obstacle =  new Obstacle(box2D_simulator.getWorld(), new Vector2(-100.f, -100.f), new Vector2(-120.f, -200.f));
        Obstacle obstacle2 = new Obstacle(box2D_simulator.getWorld(), new Vector2(100, -200.f), new Vector2(-100.f, -210.f));
        Gdx.input.setInputProcessor(inputProcessor); // Registers input from our "inputProcessor" variable

    }

    @Override
    public void update() {
        float deltaT = Gdx.graphics.getDeltaTime();
        box2D_simulator.simulate(deltaT);

    }

    @Override
    public void render(SpriteBatch batch) {

        camera.update(); // Recalculate matrices and such
        batch.setProjectionMatrix(camera.combined); // Give batch the calculated matrices

        batch.begin();
        player.render(batch);
        batch.end();

        if(player.playerIsTargeted())
        {
            player.displayForceDirection();
        }

        box2D_simulator.debugRenderer.render(box2D_simulator.getWorld(), camera.combined);
    }

    private void inputHandler()
    {
        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {return false;}

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
                player.fingerTouchedScreen(screenX, screenY, pointer, button);
              if(!player.playerIsTargeted()) {
                  // TEMPORARY CODE, TESTING CAMERA
                  camera_firstTouch.x = screenX;
                  camera_firstTouch.y = screenY;
              }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                player.fingerReleasedFromScreen(screenX, screenY, pointer, button);
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                player.fingerDraggedOnScreen(screenX, screenY, pointer);
                if(!player.playerIsTargeted()) {
                   // TEMPORARY CODE, TESTING CAMERA
                   float camera_speed = 10;
                   float x_camera_direction = 0;
                   float y_camera_direction = 0;

                   if (camera_firstTouch.x < screenX) x_camera_direction = -1;
                   else x_camera_direction = 1;
                   if (camera_firstTouch.y < screenY) y_camera_direction = 1;
                   else y_camera_direction = -1;

                   camera_firstTouch.x = screenX;
                   camera_firstTouch.y = screenY;
                   camera.translate(x_camera_direction * camera_speed, y_camera_direction * camera_speed, 0);
               }
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
    public void dispose()
    {
        player.dispose();
    }

}
