package com.blastedstudios.thrall.ui.overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.ui.AbstractScreen;
import com.blastedstudios.thrall.world.Entity;
import com.blastedstudios.thrall.world.World;

public class OverworldScreen extends AbstractScreen {
	private Batch batch;
	private Texture img;
	private World world;
	
	public OverworldScreen(final AbstractGame game){
		super(game, "ui/uiskin.json");
		final Button exitButton = new TextButton("Exit", skin);
		exitButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				game.popScreen();
			}
		});
		Window window = new Window("Options", skin);
		window.add(exitButton);
		window.pack();
		window.setX(Gdx.graphics.getWidth() - window.getWidth());
		window.setY(Gdx.graphics.getHeight() - window.getHeight());
		stage.addActor(window);

		world = new World();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}
	
	@Override public void render(float delta){
		super.render(delta);
		world.render(Gdx.graphics.getDeltaTime());
		
		stage.draw();
		batch.begin();
		for (Entity entity : world.getEntities())
			batch.draw(img, entity.getPosition().x, entity.getPosition().y, 5f, 5f);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
