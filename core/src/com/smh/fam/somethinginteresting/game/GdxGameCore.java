package com.smh.fam.somethinginteresting.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;

public class GdxGameCore extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	Box2D_Simulator box2D_simulator;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		box2D_simulator = new Box2D_Simulator();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		box2D_simulator.simulate(0.01f);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
