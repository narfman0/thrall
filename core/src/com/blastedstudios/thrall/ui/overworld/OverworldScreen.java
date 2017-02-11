package com.blastedstudios.thrall.ui.overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
		final Button newButton = new TextButton("New", skin);
		final Button loadButton = new TextButton("Load", skin);
		final Button exitButton = new TextButton("Exit", skin);
		newButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				//game.pushScreen(new EncounterScreen(game));
			}
		});
		exitButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		Window window = new Window("GDX World Editor", skin);
		window.add(newButton);
		window.row();
		window.add(loadButton).colspan(2);
		window.row();
		window.add(exitButton).colspan(2);
		window.pack();
		window.setX(Gdx.graphics.getWidth()/2 - window.getWidth()/2);
		window.setY(Gdx.graphics.getHeight()/2 - window.getHeight()/2);
		stage.addActor(window);

		world = new World();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}
	
	@Override public void render(float delta){
		super.render(delta);
		stage.draw();

		world.render(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
