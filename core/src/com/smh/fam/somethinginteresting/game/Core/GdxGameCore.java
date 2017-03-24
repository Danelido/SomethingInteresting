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

<<<<<<< HEAD
	private SpriteBatch batch;  // The renderer
=======


>>>>>>> origin/master
	private GameStateManager gsm;
	private SpriteBatch batch;


	@Override
	public void create () {
		gsm = new GameStateManager();
		batch = new SpriteBatch();
	}

	@Override
	public void render () {

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
