package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smh.fam.somethinginteresting.game.Core.Box2D_Simulator;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;

public class GdxGameCore extends ApplicationAdapter {
	public static final int VIRTUAL_WIDTH = 1920;
	public static final int VIRTUAL_HEIGHT = VIRTUAL_WIDTH / 16 * 9; // 16:9 format

	private GameStateManager gsm;
	private SpriteBatch batch;


	@Override
	public void create () {
		gsm = new GameStateManager(this);
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update();

		batch.begin();
		gsm.render(batch);
		batch.end();

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
