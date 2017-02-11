package com.blastedstudios.thrall;

import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.ui.overworld.OverworldScreen;

public class Thrall extends AbstractGame {
	@Override
	public void create () {
		pushScreen(new OverworldScreen(this));
	}
}
