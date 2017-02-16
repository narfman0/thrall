package com.blastedstudios.thrall.world.entity;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class VehicleEntity extends Entity {
	public VehicleEntity(Vector2 position, Vector2 velocity, List<NPC> npcs) {
		super(position, velocity, npcs);
	}
	
	public void render(float dt){
		super.render(dt);
		velocity.scl(45f*dt);
	}
}
