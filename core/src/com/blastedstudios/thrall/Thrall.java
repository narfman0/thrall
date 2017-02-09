package com.blastedstudios.thrall;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blastedstudios.thrall.components.MovementComponent;
import com.blastedstudios.thrall.components.PositionComponent;
import com.blastedstudios.thrall.systems.MovementSystem;
import com.blastedstudios.thrall.systems.PositionSystem;
import com.blastedstudios.thrall.world.World;

public class Thrall extends ApplicationAdapter {
	Batch batch;
	Texture img;
	PooledEngine engine;
	MovementSystem movementSystem;
	PositionSystem positionSystem;
	World world;
	ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	
	@Override
	public void create () {
		world = new World();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		engine = new PooledEngine();
		movementSystem = new MovementSystem();
		positionSystem = new PositionSystem();
		engine.addSystem(movementSystem);
		engine.addSystem(positionSystem);

		for (int i = 0; i < 10; i++) {
			Entity entity = engine.createEntity();
			entity.add(new PositionComponent(world.random.nextFloat()*30f, world.random.nextFloat()*30));
			entity.add(new MovementComponent(world.random.nextFloat()/3f, world.random.nextFloat()/3f));
			engine.addEntity(entity);
		}

		log("MovementSystem has: " + movementSystem.entities.size() + " entities.");
		log("PositionSystem has: " + positionSystem.entities.size() + " entities.");
	}

	@Override
	public void render () {
		for (int i = 0; i < 10; i++)
			engine.update(0.25f);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (int i = 0; i < positionSystem.entities.size(); ++i) {
			Entity e = positionSystem.entities.get(i);
			PositionComponent p = pm.get(e);
			batch.draw(img, p.position.x, p.position.y, 5f, 5f);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public static void log (String string) {
		System.out.println(string);
	}
}
