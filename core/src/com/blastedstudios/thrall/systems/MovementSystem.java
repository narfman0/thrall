package com.blastedstudios.thrall.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.blastedstudios.thrall.Thrall;
import com.blastedstudios.thrall.components.MovementComponent;
import com.blastedstudios.thrall.components.PositionComponent;

public class MovementSystem extends EntitySystem {
	public ImmutableArray<Entity> entities;

	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

	@Override
	public void addedToEngine (Engine engine) {
		entities = engine.getEntitiesFor(Family.all(PositionComponent.class, MovementComponent.class).get());
		Thrall.log("MovementSystem added to engine.");
	}

	@Override
	public void removedFromEngine (Engine engine) {
		Thrall.log("MovementSystem removed from engine.");
		entities = null;
	}

	@Override
	public void update (float deltaTime) {

		for (int i = 0; i < entities.size(); ++i) {
			Entity e = entities.get(i);
			PositionComponent p = pm.get(e);
			MovementComponent m = mm.get(e);
			p.position.add(m.velocity.cpy().scl(deltaTime));
		}

		Thrall.log(entities.size() + " Entities updated in MovementSystem.");
	}
}