package com.smh.fam.somethinginteresting.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smh.fam.somethinginteresting.game.Core.GdxGameCore;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GdxGameCore(), config);
		config.title = "Something interesting";
		config.width = 1280;
		config.height = 720;
	}
}
