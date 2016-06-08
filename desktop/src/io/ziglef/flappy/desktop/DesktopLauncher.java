package io.ziglef.flappy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.ziglef.flappy.Flappy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Flappy.WIDTH;
		config.height = Flappy.HEIGHT;
		config.title = Flappy.TITLE;
		new LwjglApplication(new Flappy(), config);
	}
}
