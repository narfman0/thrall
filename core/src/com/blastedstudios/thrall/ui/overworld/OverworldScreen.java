package com.blastedstudios.thrall.ui.overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.thrall.Thrall;
import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.ui.AbstractScreen;
import com.blastedstudios.thrall.ui.main.MainScreen;
import com.blastedstudios.thrall.util.Log;
import com.blastedstudios.thrall.world.Entity;
import com.blastedstudios.thrall.world.World;

public class OverworldScreen extends AbstractScreen {
	private static final String TAG = MainScreen.class.getName();
	private final ShapeRenderer renderer = new ShapeRenderer();
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private final boolean DEBUG_DRAW = false;
	private Batch batch = new SpriteBatch();
	private Texture img;
	private World world;
	
	public OverworldScreen(final AbstractGame game){
		super(game, Thrall.SKIN_PATH);
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
		img = new Texture("badlogic.jpg");
		camera.zoom += 3;
	}
	
	@Override public void render(float delta){
		super.render(delta);
		world.render(Gdx.graphics.getDeltaTime());
		
		if(Gdx.input.isKeyPressed(Keys.UP))
			camera.position.y += camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			camera.position.y -= camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			camera.position.x += camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			camera.position.x -= camera.zoom;
		camera.update();
		
		if(DEBUG_DRAW){
			renderer.setColor(Color.GRAY);
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeType.Filled);
			for (Entity entity : world.getEntities())
				renderer.circle(entity.getPosition().x, entity.getPosition().y, 6);
			renderer.end();
		}else{
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			for (Entity entity : world.getEntities())
				batch.draw(img, entity.getPosition().x, entity.getPosition().y, 3f, 3f);
			batch.end();
		}
		stage.draw();
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(.01f, camera.zoom + amount*camera.zoom/4f);
		Log.log(TAG, "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
