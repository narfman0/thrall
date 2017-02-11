package com.blastedstudios.thrall.ui.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.ui.AbstractScreen;
import com.blastedstudios.thrall.ui.overworld.OverworldScreen;

public class MainScreen extends AbstractScreen {
	public MainScreen(final AbstractGame game){
		super(game, "ui/uiskin.json");
		final Button newButton = new TextButton("New", skin);
		final Button exitButton = new TextButton("Exit", skin);
		newButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				game.pushScreen(new OverworldScreen(game));
			}
		});
		exitButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		Window window = new Window("Thrall", skin);
		window.add(newButton);
		window.row();
		window.add(exitButton);
		window.pack();
		window.setX(Gdx.graphics.getWidth()/2 - window.getWidth()/2);
		window.setY(Gdx.graphics.getHeight()/2 - window.getHeight()/2);
		stage.addActor(window);
	}
	
	@Override public void render(float delta){
		super.render(delta);
		stage.draw();
	}
}
