package com.blastedstudios.thrall.world.entity;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class TownEntity extends Entity {
	public TownEntity(Vector2 position, Vector2 velocity, List<NPC> npcs) {
		super(position, velocity, npcs);
	}
}
