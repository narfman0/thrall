package com.blastedstudios.thrall.world;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.thrall.util.Log;
import com.blastedstudios.thrall.world.entity.Entity;
import com.blastedstudios.thrall.world.entity.FarmEntity;
import com.blastedstudios.thrall.world.entity.MineEntity;
import com.blastedstudios.thrall.world.entity.TownEntity;

public class World {
	private static final String TAG = World.class.getName();
	private final Terrain terrain;
	private final List<Entity> entities;
	public final Random random;
	
	public World(long seed) {
		random = new Random(seed);
		terrain = new Terrain(random);
		entities = new LinkedList<>();
		
		for (int i = 0; i < 10; i++) {
			Vector2 position = new Vector2(random.nextFloat()*100f, random.nextFloat()*100);
			switch(random.nextInt(3)){
			case 0:
				entities.add(new TownEntity(position, Vector2.Zero));
				break;
			case 1:
				entities.add(new MineEntity(position, Vector2.Zero));
				break;
			case 2:
				entities.add(new FarmEntity(position, Vector2.Zero));
				break;
			}
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

	public Terrain getTerrain() {
		return terrain;
	}
}
