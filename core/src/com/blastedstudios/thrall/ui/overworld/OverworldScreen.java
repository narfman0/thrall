package com.blastedstudios.thrall.ui.overworld;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.blastedstudios.thrall.Thrall;
import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.ui.AbstractScreen;
import com.blastedstudios.thrall.ui.main.MainScreen;
import com.blastedstudios.thrall.util.Log;
import com.blastedstudios.thrall.world.IEncounterListener;
import com.blastedstudios.thrall.world.World;
import com.blastedstudios.thrall.world.encounter.Encounter;
import com.blastedstudios.thrall.world.encounter.Generator;
import com.blastedstudios.thrall.world.entity.Entity;

public class OverworldScreen extends AbstractScreen implements IEncounterListener {
	private static final String TAG = MainScreen.class.getName();
	private final ShapeRenderer renderer = new ShapeRenderer();
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private final boolean DEBUG_DRAW = false;
	private final float MOVEMENT_SCALAR = 400f;
	private final World world;
	private final HashMap<Entity, Texture> worldTextures = new HashMap<>();
	private Batch batch = new SpriteBatch();
	private Texture sandTexture;
	private boolean followVehicle = true;
	private final CurrencyWindow currencyWindow;
	private EncounterWindow encounterWindow = null;
	private Vector2 targetClickedLocation = null; 
	
	public OverworldScreen(final AbstractGame game){
		super(game, Thrall.SKIN_PATH);
		stage.addActor(new OptionsWindow(skin, game));
		stage.addActor(currencyWindow = new CurrencyWindow(skin, game));

		world = new World(System.nanoTime(), this);
		for(Entity entity : world.getEntities()){
			String name = entity.getClass().getSimpleName(),
					imgName = name.substring(0, name.length()-6).toLowerCase();
			worldTextures.put(entity, new Texture("overworld/" + imgName + ".png"));
		}
		sandTexture = new Texture("overworld/sand.jpg");
		camera.zoom += 3;
	}
	
	@Override public void render(float dt){
		super.render(dt);
		world.render(dt);
		currencyWindow.render(dt, world);
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT)){
			int x = Gdx.input.getX(), y = Gdx.input.getY();
			Vector2 stageCoordinates = stage.screenToStageCoordinates(new Vector2(x, y));
		    if(stage.hit(stageCoordinates.x, stageCoordinates.y, false) == null){
		    	Vector3 worldCoordinates = camera.unproject(new Vector3(x, y, 0));
		    	targetClickedLocation = new Vector2(worldCoordinates.x, worldCoordinates.y);
		    }
		}
		
		if(followVehicle){
			camera.position.x = world.getPlayerVehicle().getPosition().x;
			camera.position.y = world.getPlayerVehicle().getPosition().y;
			if(world.getEncounter() == null && world.getFuel() > 0f && world.getPeople() > 0f){
				if(targetClickedLocation != null){
					if(targetClickedLocation.dst(world.getPlayerVehicle().getPosition()) < 1f ||
							Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.DOWN) ||
							Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.LEFT))
						targetClickedLocation = null;
					else{
						Vector2 direction = targetClickedLocation.cpy().sub(world.getPlayerVehicle().getPosition()).nor();
						world.getPlayerVehicle().getVelocity().add(direction.scl(MOVEMENT_SCALAR*dt));
					}
				}else{
					if(Gdx.input.isKeyPressed(Keys.UP))
						world.getPlayerVehicle().getVelocity().add(0, MOVEMENT_SCALAR*dt);
					if(Gdx.input.isKeyPressed(Keys.DOWN))
						world.getPlayerVehicle().getVelocity().add(0, MOVEMENT_SCALAR*-dt);
					if(Gdx.input.isKeyPressed(Keys.RIGHT))
						world.getPlayerVehicle().getVelocity().add(MOVEMENT_SCALAR*dt, 0);
					if(Gdx.input.isKeyPressed(Keys.LEFT))
						world.getPlayerVehicle().getVelocity().add(MOVEMENT_SCALAR*-dt, 0);
				}
			}
		}else{
			if(Gdx.input.isKeyPressed(Keys.UP))
				camera.position.y += camera.zoom;
			if(Gdx.input.isKeyPressed(Keys.DOWN))
				camera.position.y -= camera.zoom;
			if(Gdx.input.isKeyPressed(Keys.RIGHT))
				camera.position.x += camera.zoom;
			if(Gdx.input.isKeyPressed(Keys.LEFT))
				camera.position.x -= camera.zoom;
		}
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
			float tileSize = 32f;
			for(float x=camera.position.x-tileSize*camera.zoom-tileSize; x<camera.position.x+tileSize*camera.zoom+tileSize; x+=tileSize)
				for(float y=camera.position.y-tileSize*camera.zoom-tileSize; y<camera.position.y+tileSize*camera.zoom+tileSize; y+=tileSize)
					batch.draw(sandTexture, x-x%32, y-y%32, 32f, 32f);
			for (Entity entity : world.getEntities())
				batch.draw(worldTextures.get(entity), entity.getPosition().x, entity.getPosition().y, Generator.ENCOUNTER_DISTANCE, Generator.ENCOUNTER_DISTANCE);
			batch.end();
		}
		stage.draw();
	}
	
	@Override public boolean keyDown(int key) {
		switch(key){
		case Keys.SPACE:
			followVehicle = !followVehicle;
			break;
		case Keys.ESCAPE:
			game.popScreen();
			break;
		}
		return super.keyDown(key);
	}
	
	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.min(5, Math.max(1f, camera.zoom + amount*camera.zoom/4f));
		Log.log(TAG, "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		sandTexture.dispose();
		for(Texture texture : worldTextures.values())
			texture.dispose();
	}

	@Override
	public void triggerEncounter(Encounter encounter) {
		stage.addActor(encounterWindow = new EncounterWindow(skin, game, world, () -> {
			if(encounterWindow != null)
				encounterWindow.remove();
			encounterWindow = null;
			world.encounterComplete();
		}, encounter));
	}
}
