package com.blastedstudios.thrall;

import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.ui.main.MainScreen;

public class Thrall extends AbstractGame {
	@Override
	public void create () {
		pushScreen(new MainScreen(this));
	}
}
