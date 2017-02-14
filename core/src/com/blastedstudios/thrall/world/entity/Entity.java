package com.blastedstudios.thrall.world.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected final Vector2 position, velocity;
	private float playerDisposition = .5f; // [0-1], .5 is neutral. Do we like the player? Probably not.
	
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

	public float getPlayerDisposition() {
		return playerDisposition;
	}

	public void addPlayerDisposition(float playerDisposition) {
		this.playerDisposition = Math.max(0f, Math.min(1f, this.playerDisposition + playerDisposition));
	}
}
