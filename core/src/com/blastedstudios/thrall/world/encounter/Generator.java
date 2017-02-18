package com.blastedstudios.thrall.world.encounter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.blastedstudios.thrall.util.Log;
import com.blastedstudios.thrall.world.IEncounterListener;
import com.blastedstudios.thrall.world.World;
import com.blastedstudios.thrall.world.entity.Entity;
import com.blastedstudios.thrall.world.entity.FarmEntity;
import com.blastedstudios.thrall.world.entity.MineEntity;
import com.blastedstudios.thrall.world.entity.NPC;
import com.blastedstudios.thrall.world.entity.TownEntity;

public class Generator {
	private static final String TAG = Generator.class.getName();
	public static final float ENCOUNTER_DISTANCE = 3f,
			INTIMIDATION_DISPOSITION = .25f;

	private static String[] NAMES_BEGINNING = { "Kr", "Ca", "Ra", "Mrok", "Cru",
			"Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
			"Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
			"Mar", "Luk" };
	private static String[] NAMES_MIDDLE = { "air", "ir", "mi", "sor", "mee", "clo",
			"red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
			"marac", "zoir", "slamar", "salmar", "urak" };
	private static String[] NAMES_END = { "d", "ed", "ark", "arc", "es", "er", "der",
			"tron", "med", "ure", "zur", "cred", "mur" };

	/**
	 * Check if we should trigger an encounter. Note: this sets the last visited entity too!
	 * @return generated encounter for the given entity
	 */
	public static Encounter checkEncounter(World world, IEncounterListener listener){
		for(Entity entity : world.getEntities()){
			if(entity == world.getPlayerVehicle())
				continue;
			if(world.getPlayerVehicle().getPosition().dst(entity.getPosition()) < ENCOUNTER_DISTANCE/2f){
				if(world.getLastVisited() != entity){
					world.setLastVisited(entity);
					return generateEncounter(world, entity, listener);
				}
			}else if(world.getLastVisited() == entity)
				world.setLastVisited(null);
		}
		return null;
	}

	public static Encounter generateEncounter(World world, Entity entity, IEncounterListener listener){
		List<EncounterOption> options = new LinkedList<>();
		String encounterText = "";
		if(entity instanceof FarmEntity){
			encounterText = "You have come upon a farm, where the proprieters offer food in return for gentleness.";
			options.add(new EncounterOption("Your generous offer is accepted, and good day to you", () -> {
				world.addFood(world.random.nextFloat()*3f+2);
			}));
			float probability = (float)world.random.nextGaussian()/10f - .05f + entity.getPlayerDisposition() + .25f;
			options.add(new EncounterSuccessFailOption(probability, listener, "Cmon pops - grease or get greased", () -> {
				world.addCash(world.random.nextInt(20)+30);
				entity.addPlayerDisposition(-INTIMIDATION_DISPOSITION);
				world.encounterComplete();
			}, () -> {
				// failed intimidation, fight!
				world.setEncounter(generateFightEncounter(world, entity, listener, "Come to *my* home and intimidate *me*?"));
			}));
		}else if(entity instanceof MineEntity){
			encounterText = "You have come upon a mine, where the proprieters offer iron in return for gentleness.";
			options.add(new EncounterOption("Your generous offer is accepted, and good day to you", () -> {
				world.addIron(world.random.nextInt(3)+2);
			}));
			float probability = (float)world.random.nextGaussian()/10f - .05f + entity.getPlayerDisposition() + .25f;
			options.add(new EncounterSuccessFailOption(probability, listener, "I've killed for less, pony up", () -> {
				world.addCash(world.random.nextInt(20)+30);
				entity.addPlayerDisposition(-INTIMIDATION_DISPOSITION);
				world.encounterComplete();
			}, () -> {
				world.addCash(world.random.nextInt(2)+3);
				entity.addPlayerDisposition(-INTIMIDATION_DISPOSITION);
				world.encounterComplete();
			}));
		}else if(entity instanceof TownEntity){
			encounterText = "You have come upon a town, where the council offer cash in return for gentleness.";
			options.add(new EncounterOption("Your generous offer is accepted, and good day to you", () -> {
				world.addCash(world.random.nextInt(10)+3);
				world.encounterComplete();
			}));
			if(world.getCash() >= 100){
				int fuel = world.random.nextInt(4)+3;
				options.add(new EncounterOption("Trade 100$ for " + fuel + " fuel", () -> {
					world.addFuel(fuel);
					world.addCash(-100);
					world.encounterComplete();
				}));
			}
			if(world.getIron() >= 10){
				int cash = world.random.nextInt(50)+15;
				options.add(new EncounterOption("Trade 10 iron for " + cash + "$", () -> {
					world.addIron(-10);
					world.addCash(cash);
					world.encounterComplete();
				}));
			}
		}
		return new Encounter(options, encounterText);
	}
	
	public static Encounter generateFightEncounter(World world, Entity entity, IEncounterListener listener, String text){
		LinkedList<EncounterOption> options = new LinkedList<>();
		final int npcDifference = entity.getNpcs().size() - world.getPlayerVehicle().getNpcs().size();
		float p = 1f - (float)npcDifference/world.getPlayerVehicle().getNpcs().size() - (float)world.random.nextGaussian();
		Log.log(TAG, "Engaging in fight encounter with npcDifference: " + npcDifference + " p: " + p);
		options.add(new EncounterSuccessFailOption(p, listener,
				"Attack", () -> {
					// won a battle
					world.addCash(world.random.nextInt(70)+30);
					world.encounterComplete();
				}, () -> {
					// lost a battle
					world.addCash(-world.random.nextInt(50));
					for(NPC npc : world.getPlayerVehicle().getNpcs())
						npc.addHpCurrent(-(world.random.nextFloat()/3f));
					world.encounterComplete();
				}));
		return new Encounter(options, text);
	}

	public static String generateName(Random random) {
		return NAMES_BEGINNING[random.nextInt(NAMES_BEGINNING.length)] + 
				NAMES_MIDDLE[random.nextInt(NAMES_MIDDLE.length)]+
				NAMES_END[random.nextInt(NAMES_END.length)];
	}

	public static NPC generateNPC(Random random){
		return new NPC(generateName(random), random.nextFloat() + 1f, random.nextFloat()*2 + 3);
	}

	public static List<NPC> generateNPCs(Random random, int min, int max){
		int count = min + (min == max ? 0 : random.nextInt(max - min));
		List<NPC> npcs = new LinkedList<>();
		for(int i=0; i<count; i++)
			npcs.add(Generator.generateNPC(random));
		return npcs;
	}
}
