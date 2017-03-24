package com.smh.fam.somethinginteresting.game.Core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smh.fam.somethinginteresting.game.Core.Box2D_Simulator;
import com.smh.fam.somethinginteresting.game.State.GameStateManager;

public class GdxGameCore extends ApplicationAdapter {
<<<<<<< HEAD:core/src/com/smh/fam/somethinginteresting/game/GdxGameCore.java



	public static final int VIRTUAL_WIDTH = 1920;
	public static final int VIRTUAL_HEIGHT = VIRTUAL_WIDTH / 16 * 9; // 16:9 format
=======
	private static final int VIRTUAL_WIDTH = 1920;
	private static final int VIRTUAL_HEIGHT = VIRTUAL_WIDTH / 16 * 9; // 16:9 format
>>>>>>> origin/master:core/src/com/smh/fam/somethinginteresting/game/Core/GdxGameCore.java

	private SpriteBatch batch;  // The renderer
	private GameStateManager gsm;
	private Box2D_Simulator box2D_simulator;


	@Override
	public void create () {
		batch = new SpriteBatch();
<<<<<<< HEAD:core/src/com/smh/fam/somethinginteresting/game/GdxGameCore.java

		gsm = new GameStateManager();




=======
		gsm = new GameStateManager();

		box2D_simulator = new Box2D_Simulator();
>>>>>>> origin/master:core/src/com/smh/fam/somethinginteresting/game/Core/GdxGameCore.java
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
