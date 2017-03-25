package com.smh.fam.somethinginteresting.game.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.smh.fam.somethinginteresting.game.Core.Box2D_Simulator;
import com.smh.fam.somethinginteresting.game.Core.GdxGameCore;
import com.smh.fam.somethinginteresting.game.Game.Obstacle;
import com.smh.fam.somethinginteresting.game.Game.Player;


/**
 * Created by Alexander on 2017-03-24.
 */

public class PlayState extends GameState {
    private Camera camera;
    private Box2D_Simulator box2D_simulator;
    private Player player;
    private InputProcessor inputProcessor;
    protected PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        inputHandler();
        camera = new OrthographicCamera(GdxGameCore.VIRTUAL_WIDTH, GdxGameCore.VIRTUAL_HEIGHT);
        box2D_simulator = new Box2D_Simulator();
        player = new Player(box2D_simulator.getWorld(), new Vector2(0.f, 0.f));
        Obstacle obstacle = new Obstacle(box2D_simulator.getWorld(), new Vector2(-50.f, -200.f), new Vector2(50.f,-210.f));
    }

    @Override
    public void update() {
        float deltaT = Gdx.graphics.getDeltaTime();
        box2D_simulator.simulate(deltaT);
    }

    @Override
    public void render(SpriteBatch batch) {

        box2D_simulator.debugRenderer.render(box2D_simulator.getWorld(), camera.combined);

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

                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
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

}
