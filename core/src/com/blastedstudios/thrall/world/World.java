package com.blastedstudios.thrall.world;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.thrall.util.Log;
import com.blastedstudios.thrall.world.encounter.Encounter;
import com.blastedstudios.thrall.world.encounter.Generator;
import com.blastedstudios.thrall.world.entity.Entity;
import com.blastedstudios.thrall.world.entity.FarmEntity;
import com.blastedstudios.thrall.world.entity.MineEntity;
import com.blastedstudios.thrall.world.entity.NPC;
import com.blastedstudios.thrall.world.entity.TownEntity;
import com.blastedstudios.thrall.world.entity.VehicleEntity;

public class World {
	private static final String TAG = World.class.getName();
	private final Terrain terrain;
	private final List<Entity> entities;
	private final VehicleEntity playerVehicle;
	public final Random random;
	private float food, fuel;
	private int cash, iron;
	private Entity lastVisited = null;
	private final IEncounterListener encounterListener;
	private Encounter encounter = null;
	private float dxSinceRandom = 0f;
	
	public World(long seed, IEncounterListener encounterListener) {
		this.encounterListener = encounterListener;
		random = new Random(seed);
		terrain = new Terrain(random);
		entities = new LinkedList<>();
		
		for (int i = 0; i < 10; i++) {
			Vector2 position = new Vector2(random.nextFloat()*100f-50f, random.nextFloat()*100-50f);
			switch(random.nextInt(3)){
			case 0:
				entities.add(new TownEntity(position, new Vector2(), Generator.generateNPCs(random, 12, 35)));
				break;
			case 1:
				entities.add(new MineEntity(position, new Vector2(), Generator.generateNPCs(random, 7, 15)));
				break;
			case 2:
				entities.add(new FarmEntity(position, new Vector2(), Generator.generateNPCs(random, 3, 5)));
				break;
			}
		}
		entities.add(playerVehicle = new VehicleEntity(new Vector2(), new Vector2(), Generator.generateNPCs(random, 3, 3)));
		food = 10f;
		fuel = 100f;
		Log.log(TAG, "Entities generated: " + entities.size());
	}

	public void render (float dt) {
		if(encounter != null)
			return;
		for (Entity entity : entities)
			entity.render(dt);
		float dx = playerVehicle.getVelocity().len() * dt;
		if(dx > .0001f){
			// only count time as moving when she ship is going. save food+people from starvation when AFK.
			food = Math.max(0, food-dt/10f*playerVehicle.getNpcs().size());
			if(food <= 0f && random.nextGaussian() > .99f){
				Log.debug(TAG, "NPCs starving!");
				for(Iterator<NPC> iter=playerVehicle.getNpcs().iterator(); iter.hasNext();){
					NPC npc = iter.next();
					npc.addHpCurrent(-random.nextFloat()/2f);
					if(npc.getHpCurrent() <= 0f)
						iter.remove();
				}
			}
		}
		fuel = Math.max(0, fuel-dx/10f);
		setEncounter(Generator.checkEncounter(this, encounterListener));
		dxSinceRandom += dx;
		// TODO use Math.sin (or whatever) to have low probability equation for low dxSinceRandom,
		// medium and rising for medium dxSinceRandom, then high probability for high dxSinceRandom
		if(this.encounter == null && dx > 1f && random.nextFloat()*dx < .001f){
			dxSinceRandom = 0f;
			setEncounter(Generator.generateRandom(this, encounterListener));
		}
	}
	
	public void setEncounter(Encounter encounter){
		this.encounter = encounter;
		if(encounter != null){
			Log.log(TAG, "Starting encounter: " + encounter);
			encounterListener.triggerEncounter(encounter);
			playerVehicle.getVelocity().set(0,  0);
		}
	}

	public void encounterComplete() {
		encounter = null;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public VehicleEntity getPlayerVehicle() {
		return playerVehicle;
	}

	public float getFood() {
		return food;
	}

	public void setFood(float food) {
		this.food = food;
	}
	
	public void addFood(float food) {
		this.food += food;
	}

	public float getFuel() {
		return fuel;
	}

	public void setFuel(float fuel) {
		this.fuel = fuel;
	}
	
	public void addFuel(float fuel) {
		this.fuel += fuel;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}
	
	public void addCash(int cash) {
		this.cash += cash;
	}

	public int getIron() {
		return iron;
	}

	public void setIron(int iron) {
		this.iron = iron;
	}
	
	public void addIron(int iron) {
		this.iron += iron;
	}

	public Entity getLastVisited() {
		return lastVisited;
	}

	public void setLastVisited(Entity lastVisited) {
		this.lastVisited = lastVisited;
	}

	public Encounter getEncounter() {
		return encounter;
	}
}
