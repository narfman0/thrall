package com.blastedstudios.thrall.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blastedstudios.thrall.Thrall;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
		config.width = 1280;
		config.height = 1024;
		new LwjglApplication(new Thrall(), config);
	}
}
