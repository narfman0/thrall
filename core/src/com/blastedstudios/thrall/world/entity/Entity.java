package com.blastedstudios.thrall.world.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected final Vector2 position, velocity;
	
	public Entity(Vector2 position, Vector2 velocity){
		this.position = position;
		this.velocity = velocity;
	}
	
	public void render(float dt){
		position.add(velocity.cpy().scl(dt));
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
}
