package com.blastedstudios.thrall.world.encounter;

import java.util.LinkedList;
import java.util.List;

import com.blastedstudios.thrall.world.World;
import com.blastedstudios.thrall.world.entity.Entity;
import com.blastedstudios.thrall.world.entity.FarmEntity;

public class Generator {
	public static final float ENCOUNTER_DISTANCE = 3f;
	
	/**
	 * Check if we should trigger an encounter. Note: this sets the last visited entity too!
	 * @return generated encounter for the given entity
	 */
	public static Encounter checkEncounter(World world){
		for(Entity entity : world.getEntities()){
			if(entity == world.getPlayerVehicle())
				continue;
			if(world.getPlayerVehicle().getPosition().dst(entity.getPosition()) < ENCOUNTER_DISTANCE/2f){
				if(world.getLastVisited() != entity){
					world.setLastVisited(entity);
					return generateEncounter(entity);
				}
			}else if(world.getLastVisited() == entity)
				world.setLastVisited(null);
		}
		return null;
	}
	
	public static Encounter generateEncounter(Entity entity){
		List<EncounterOption> options = new LinkedList<>();
		String encounterText = "";
		if(entity instanceof FarmEntity){
			encounterText = "You have come upon a farm, where the proprieters offer food in return for gentleness.";
			options.add(new EncounterOption("Your generous offer is accepted, and good day to you", (world) -> {
				world.addFood(world.random.nextFloat()*3f+2);
			}));
		}
		return new Encounter(options, encounterText);
	}
}
