package com.smh.fam.somethinginteresting.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;

public class GdxGameCore extends ApplicationAdapter {
	private static final int VIRTUAL_WIDTH = 1920;
	private static final int VIRTUAL_HEIGHT = VIRTUAL_WIDTH / 16 * 9; // 16:9 format

	private SpriteBatch batch;  // The renderer
	private GameStateManager gsm;
	private Box2D_Simulator box2D_simulator;


	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();

		box2D_simulator = new Box2D_Simulator();
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(); // update before render, obviously

		batch.begin();
		gsm.render(batch); // Only use batch.draw() in the states
		batch.end();

		box2D_simulator.simulate(0.01f);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
