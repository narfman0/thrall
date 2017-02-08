package com.blastedstudios.thrall.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.blastedstudios.thrall.Thrall;
import com.blastedstudios.thrall.components.PositionComponent;

public class PositionSystem extends EntitySystem {
	public ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine (Engine engine) {
		entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
		Thrall.log("PositionSystem added to engine.");
	}

	@Override
	public void removedFromEngine (Engine engine) {
		Thrall.log("PositionSystem removed from engine.");
		entities = null;
	}
}