package com.blastedstudios.thrall.world.entity;

import com.badlogic.gdx.math.Vector2;

public class VehicleEntity extends Entity {
	public VehicleEntity(Vector2 position, Vector2 velocity) {
		super(position, velocity);
	}
	
	public void render(float dt){
		super.render(dt);
		velocity.scl(45f*dt);
	}
}
