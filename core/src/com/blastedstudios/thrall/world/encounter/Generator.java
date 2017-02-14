package com.blastedstudios.thrall.world.encounter;

import java.util.LinkedList;
import java.util.List;

import com.blastedstudios.thrall.world.World;
import com.blastedstudios.thrall.world.entity.Entity;
import com.blastedstudios.thrall.world.entity.FarmEntity;
import com.blastedstudios.thrall.world.entity.MineEntity;
import com.blastedstudios.thrall.world.entity.TownEntity;

public class Generator {
	public static final float ENCOUNTER_DISTANCE = 3f,
			INTIMIDATION_DISPOSITION = .25f;
	
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
					return generateEncounter(world, entity);
				}
			}else if(world.getLastVisited() == entity)
				world.setLastVisited(null);
		}
		return null;
	}
	
	public static Encounter generateEncounter(World world, Entity entity){
		List<EncounterOption> options = new LinkedList<>();
		String encounterText = "";
		if(entity instanceof FarmEntity){
			encounterText = "You have come upon a farm, where the proprieters offer food in return for gentleness.";
			options.add(new EncounterOption("Your generous offer is accepted, and good day to you", () -> {
				world.addFood(world.random.nextFloat()*3f+2);
			}));
			float probability = world.random.nextFloat()/10f - .05f + entity.getPlayerDisposition() + .25f;
			options.add(new EncounterSuccessFailOption(probability, "Cmon pops - grease or get greased", () -> {
				world.addCash(world.random.nextInt(20)+30);
				entity.addPlayerDisposition(-INTIMIDATION_DISPOSITION);
			}, () -> {
				world.addCash(world.random.nextInt(2)+3);
				entity.addPlayerDisposition(-INTIMIDATION_DISPOSITION);
			}));
		}else if(entity instanceof MineEntity){
			encounterText = "You have come upon a mine, where the proprieters offer iron in return for gentleness.";
			options.add(new EncounterOption("Your generous offer is accepted, and good day to you", () -> {
				world.addIron(world.random.nextInt(3)+2);
			}));
		}else if(entity instanceof TownEntity){
			encounterText = "You have come upon a town, where the council offer cash in return for gentleness.";
			options.add(new EncounterOption("Your generous offer is accepted, and good day to you", () -> {
				world.addCash(world.random.nextInt(10)+3);
			}));
			if(world.getCash() >= 100){
				int fuel = world.random.nextInt(4)+3;
				options.add(new EncounterOption("Trade 100$ for " + fuel + " fuel", () -> {
					world.addFuel(fuel);
					world.addCash(-100);
				}));
			}
			if(world.getIron() >= 10){
				int cash = world.random.nextInt(50)+15;
				options.add(new EncounterOption("Trade 10 iron for " + cash + "$", () -> {
					world.addIron(-10);
					world.addCash(cash);
				}));
			}
		}
		return new Encounter(options, encounterText);
	}
}
