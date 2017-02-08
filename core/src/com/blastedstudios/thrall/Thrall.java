package com.blastedstudios.thrall;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blastedstudios.thrall.components.MovementComponent;
import com.blastedstudios.thrall.components.PositionComponent;

public class Thrall extends ApplicationAdapter {
	Batch batch;
	Texture img;
	PooledEngine engine;
	MovementSystem movementSystem;
	PositionSystem positionSystem;
	Listener listener;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		engine = new PooledEngine();

		movementSystem = new MovementSystem();
		positionSystem = new PositionSystem();

		engine.addSystem(movementSystem);
		engine.addSystem(positionSystem);

		listener = new Listener();
		engine.addEntityListener(listener);

		for (int i = 0; i < 10; i++) {
			Entity entity = engine.createEntity();
			entity.add(new PositionComponent(10, 0));
			if (i > 5) entity.add(new MovementComponent(10, 2));

			engine.addEntity(entity);
		}

		log("MovementSystem has: " + movementSystem.entities.size() + " entities.");
		log("PositionSystem has: " + positionSystem.entities.size() + " entities.");
	}

	@Override
	public void render () {
		for (int i = 0; i < 10; i++) {
			engine.update(0.25f);
			if (i > 5) engine.removeSystem(movementSystem);
		}
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		engine.removeEntityListener(listener);
		batch.dispose();
		img.dispose();
	}
	
	public static class PositionSystem extends EntitySystem {
		public ImmutableArray<Entity> entities;

		@Override
		public void addedToEngine (Engine engine) {
			entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
			log("PositionSystem added to engine.");
		}

		@Override
		public void removedFromEngine (Engine engine) {
			log("PositionSystem removed from engine.");
			entities = null;
		}
	}

	public static class MovementSystem extends EntitySystem {
		public ImmutableArray<Entity> entities;

		private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
		private ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

		@Override
		public void addedToEngine (Engine engine) {
			entities = engine.getEntitiesFor(Family.all(PositionComponent.class, MovementComponent.class).get());
			log("MovementSystem added to engine.");
		}

		@Override
		public void removedFromEngine (Engine engine) {
			log("MovementSystem removed from engine.");
			entities = null;
		}

		@Override
		public void update (float deltaTime) {

			for (int i = 0; i < entities.size(); ++i) {
				Entity e = entities.get(i);

				PositionComponent p = pm.get(e);
				MovementComponent m = mm.get(e);

				p.x += m.velocityX * deltaTime;
				p.y += m.velocityY * deltaTime;
			}

			log(entities.size() + " Entities updated in MovementSystem.");
		}
	}

	public static class Listener implements EntityListener {

		@Override
		public void entityAdded (Entity entity) {
			log("Entity added " + entity);
		}

		@Override
		public void entityRemoved (Entity entity) {
			log("Entity removed " + entity);
		}
	}

	public static void log (String string) {
		System.out.println(string);
	}
}
