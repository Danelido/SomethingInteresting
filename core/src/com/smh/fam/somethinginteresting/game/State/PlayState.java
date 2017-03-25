package com.smh.fam.somethinginteresting.game.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.smh.fam.somethinginteresting.game.Core.Box2D_Simulator;
import com.smh.fam.somethinginteresting.game.Core.GdxGameCore;


/**
 * Created by Alexander on 2017-03-24.
 */

public class PlayState extends GameState {
    private Camera camera;
    private Box2D_Simulator box2D_simulator;
    protected PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        camera = new OrthographicCamera(GdxGameCore.VIRTUAL_WIDTH, GdxGameCore.VIRTUAL_HEIGHT);
        box2D_simulator = new Box2D_Simulator();
    }

    @Override
    public void update() {
        float deltaT = Gdx.graphics.getDeltaTime();
        box2D_simulator.simulate(deltaT);
    }

    @Override
    public void render(SpriteBatch batch) {

        box2D_simulator.debugRenderer.render(box2D_simulator.world, camera.combined);

    }
}
