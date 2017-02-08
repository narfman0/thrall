package com.blastedstudios.thrall.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
	public Vector2 velocity;

	public MovementComponent(float x, float y) {
		velocity = new Vector2(x, y);
	}

	public MovementComponent(Vector2 position) {
		this.velocity = position;
	}
}