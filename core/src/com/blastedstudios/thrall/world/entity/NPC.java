package com.blastedstudios.thrall.world.entity;

public class NPC {
	private final String name;
	private float strength, hpMax, hpCurrent;
	
	public NPC(String name, float strength, float hp){
		this.name = name;
		this.strength = strength;
		this.hpMax = this.hpCurrent = hp;
	}

	public String getName() {
		return name;
	}

	public float getStrength() {
		return strength;
	}

	public void setStrength(float strength) {
		this.strength = strength;
	}

	public float getHpMax() {
		return hpMax;
	}

	public void setHpMax(float hpMax) {
		this.hpMax = hpMax;
	}

	public float getHpCurrent() {
		return hpCurrent;
	}

	public void addHpCurrent(float hp) {
		hpCurrent = Math.max(0, Math.min(hpMax, hpCurrent + hp));
	}


	public void setHpCurrent(float hpCurrent) {
		this.hpCurrent = hpCurrent;
	}
}
