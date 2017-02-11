package com.blastedstudios.thrall;

import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.ui.main.MainScreen;

public class Thrall extends AbstractGame {
	public static String SKIN_PATH = "ui/uiskin.json";
	
	@Override
	public void create () {
		pushScreen(new MainScreen(this));
	}
}
