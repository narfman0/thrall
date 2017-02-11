package com.blastedstudios.thrall.world;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.thrall.util.Log;

public class World {
	private static final String TAG = World.class.getName();
	private final Terrain world;
	private final List<Entity> entities;
	
	public World() {
		world = new Terrain(System.nanoTime());
		entities = new LinkedList<>();
		
		for (int i = 0; i < 10; i++) {
			Vector2 position = new Vector2(world.random.nextFloat()*30f, world.random.nextFloat()*30);
			Vector2 velocity = new Vector2(world.random.nextFloat()/3f, world.random.nextFloat()/3f);
			entities.add(new Entity(position, velocity));
		}

		Log.log(TAG, "Entities generated: " + entities.size());
	}

	public void render (float dt) {
		for (Entity entity : entities)
			entity.render(dt);
	}

	public List<Entity> getEntities() {
		return entities;
	}
}
