package com.smh.fam.somethinginteresting.game.State;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.smh.fam.somethinginteresting.game.Core.Box2D_Simulator;
import com.smh.fam.somethinginteresting.game.Core.CoreValues_Static;
import com.smh.fam.somethinginteresting.game.Core.Level;
import com.smh.fam.somethinginteresting.game.Core.TextureStorage;
import com.smh.fam.somethinginteresting.game.Game.Lights.LightManager;
import com.smh.fam.somethinginteresting.game.Game.Lights.PointLight;
import com.smh.fam.somethinginteresting.game.Game.BlackHole;
import com.smh.fam.somethinginteresting.game.Game.Obstacle;
import com.smh.fam.somethinginteresting.game.Game.Player;
import com.smh.fam.somethinginteresting.game.Game.Target;


/**
 * Created by Alexander on 2017-03-24.
 */

public class PlayState extends GameState {

    private OrthographicCamera camera;
    private OrthographicCamera box2DCamera;

    private Box2D_Simulator box2D_simulator;
    private TextureStorage textureStorage;
    private ShapeRenderer shapeRenderer;
    private InputProcessor inputProcessor;

    private final boolean ACCELEROMETER_AVAILABLE;

    private Vector2 camera_firstTouch = new Vector2();
    private Vector2 camera_momentum = new Vector2(0.0f, 0.0f);
    private float camera_momentumDecay = 0.001f;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        ACCELEROMETER_AVAILABLE = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer) && false;
    }

    private Player player;
    private Array<Obstacle> obstacles;
    private Array<Target> targets;
    private Array<BlackHole> blackHoles;

    private LightManager lightManager;
    private PointLight playerLight;


    @Override
    public void init() {
        inputHandler();

        camera = new OrthographicCamera(CoreValues_Static.VIRTUAL_WIDTH, CoreValues_Static.VIRTUAL_HEIGHT);
        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false,CoreValues_Static.VIRTUAL_WIDTH/CoreValues_Static.PPM, CoreValues_Static.VIRTUAL_HEIGHT/CoreValues_Static.PPM);
        box2D_simulator = new Box2D_Simulator();

        textureStorage = new TextureStorage();
        shapeRenderer = new ShapeRenderer();

        Level level = new Level(box2D_simulator.getWorld(), textureStorage);
        level.readFromXML("levels/level_1.xml");

        box2D_simulator.setGravity(level.getGravityVector());
        obstacles = level.getObstacles();
        targets = level.getTargets();
        blackHoles = level.getBlackHoles();

        //player = new Player(box2D_simulator.getWorld(), textureStorage, level.getPlayerPosition(), box2DCamera);
        player = new Player(box2D_simulator.getWorld(), textureStorage, new Vector2(500,600), box2DCamera);


        Obstacle obstacle =  new Obstacle(box2D_simulator.getWorld(), new Vector2(500, 100.f), new Vector2(520.f, 200.f), 45f, Obstacle.Type.REGULAR);
        Obstacle obstacle2 = new Obstacle(box2D_simulator.getWorld(), new Vector2(500, 200.f), new Vector2(600.f, 210.f), 2f, Obstacle.Type.REGULAR);

        obstacles.add(obstacle);
        obstacles.add(obstacle2);
        
        //Temporary walls around game area.

        Obstacle wall_upper =  new Obstacle(box2D_simulator.getWorld(), new Vector2(CoreValues_Static.VIRTUAL_WIDTH/2, (CoreValues_Static.VIRTUAL_HEIGHT)), new Vector2(CoreValues_Static.VIRTUAL_WIDTH, (CoreValues_Static.VIRTUAL_HEIGHT ) + 10 ), 0f, Obstacle.Type.REGULAR);
        Obstacle wall_bottom =  new Obstacle(box2D_simulator.getWorld(), new Vector2(CoreValues_Static.VIRTUAL_WIDTH/2, 0), new Vector2(CoreValues_Static.VIRTUAL_WIDTH,  10 ), 0f, Obstacle.Type.REGULAR);
        Obstacle wall_left =  new Obstacle(box2D_simulator.getWorld(), new Vector2(0, (CoreValues_Static.VIRTUAL_HEIGHT)), new Vector2(10, (CoreValues_Static.VIRTUAL_HEIGHT/2) ), 0f, Obstacle.Type.REGULAR);
        Obstacle wall_right =  new Obstacle(box2D_simulator.getWorld(), new Vector2(CoreValues_Static.VIRTUAL_WIDTH, (CoreValues_Static.VIRTUAL_HEIGHT)), new Vector2((CoreValues_Static.VIRTUAL_WIDTH) - 10, (CoreValues_Static.VIRTUAL_HEIGHT)/2 ), 0f, Obstacle.Type.REGULAR);

        obstacles.add(wall_upper);
        obstacles.add(wall_bottom);
        obstacles.add(wall_left);
        obstacles.add(wall_right);


        lightManager = new LightManager(box2D_simulator.getWorld());
        lightManager.getRayHandler().setAmbientLight(0.1f, 0.1f, 0.1f, 0.5f);
        lightManager.getRayHandler().setShadows(true);

        lightManager.addPointLight(new Vector2(50,670),90, new Color(1.f,0.5f,.4f,0.70f),600,null);
        lightManager.addPointLight(new Vector2(500,600),90, new Color(0.f,0.5f,1.0f,0.70f),250,"playerLight");

        playerLight = lightManager.getLightByID("playerLight");

        Gdx.input.setInputProcessor(inputProcessor); // Registers input from our "inputProcessor" variable

    }

    @Override
    public void update() {
        float deltaT = Gdx.graphics.getDeltaTime();

        box2D_simulator.simulate(deltaT);
        if (ACCELEROMETER_AVAILABLE){
            Vector2 gravityVec = new Vector2(-Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerX());
            box2D_simulator.setGravity(gravityVec);
        }

        Vector2 summedForce = new Vector2(0,0);
        for (BlackHole blackHole: blackHoles) {
            blackHole.update(deltaT);
            summedForce = summedForce.add(blackHole.getForce(player.getPosition()));

        }

        player.applyForceToPlayer( summedForce.scl(deltaT) );

        camera_momentum = camera_momentum.scl((float) Math.pow(camera_momentumDecay, deltaT));
        camera.translate(camera_momentum.x, camera_momentum.y, 0);
        box2DCamera.translate(camera_momentum.x /CoreValues_Static.PPM, camera_momentum.y/CoreValues_Static.PPM, 0);

        camera.update(); // Recalculate matrices and such
        box2DCamera.update();
        lightManager.updateCamera(box2DCamera);
    }

    @Override
    public void render(SpriteBatch batch) {


        batch.setProjectionMatrix(camera.combined); // Give batch the calculated matrices, has to be done before batch.begin()
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Render obstacles before player
        // Because the direction sprite needs to be renderer over everything and that includes obstacles
        // the direction sprite need so be between begin() and end() so this was the only option
        for (Obstacle obstacle: obstacles){
            obstacle.render(batch, shapeRenderer);
        }
        playerLight.setPosition(player.getPosition().x , player.getPosition().y );
        lightManager.pointLights_updateAndRender();
        batch.begin();

        for (Target target: targets) {
            target.render(batch);
        }

        for (BlackHole blackHole: blackHoles) {
            blackHole.render(batch);
        }

        player.render(batch); // Render player

        if(player.playerIsTargeted()) {player.displayForceDirection(batch);} // Render direction line above player

        batch.end();






        //box2D_simulator.debugRenderer.render(box2D_simulator.getWorld(), box2DCamera.combined);
    }

    private void inputHandler()
    {
        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                // Reset camera zoom
                if(Input.Keys.R == keycode) resetCameraZoom();

                if(Input.Keys.W == keycode){
                    lightManager.moveLights(0.f, 10.f);
                }
                    if(Input.Keys.A == keycode){
                        lightManager.moveLights(-10.f, 0.f);
                    }
                        if(Input.Keys.S == keycode){
                            lightManager.moveLights(0.f, -10.f);
                        }
                            if(Input.Keys.D == keycode){
                                lightManager.moveLights(10.f, 0.f);
                            }

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
                player.fingerTouchedScreen(screenX, screenY, pointer, button);
              if(!player.playerIsTargeted()) {
                  camera_firstTouch = new Vector2(screenX, screenY);
                  camera_momentum = new Vector2(0.0f, 0.0f);
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
                    Vector2 delta = new Vector2(camera_firstTouch.x-screenX, screenY-camera_firstTouch.y);
                    camera_momentum = camera_momentum.add(delta.scl(0.1f));
                    camera_firstTouch.set(screenX, screenY);
                }
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
               zoomCamera(amount);
                return false;
            }
        };
        
    }

    @Override
    public void dispose()
    {
        player.dispose();
        shapeRenderer.dispose();
        lightManager.disposeAll();

    }

    private void zoomCamera(float amount){
        camera.zoom += amount;
        box2DCamera.zoom += amount;
        player.setCameraZoom(camera.zoom);
    }
    private void resetCameraZoom(){
        camera.zoom = 1.0f;
        player.setCameraZoom(camera.zoom);
    }

}

