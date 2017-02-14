package com.blastedstudios.thrall.world.encounter;

import com.blastedstudios.thrall.world.World;

public class EncounterSuccessFailOption extends EncounterOption{
	private final float probability; // [0-1]
	private final IEncounterHandler failure;
	
	public EncounterSuccessFailOption(float probability, String choiceText,
			IEncounterHandler success, IEncounterHandler failure){
		super(choiceText, success);
		this.probability = probability;
		this.failure = failure;
	}

	@Override
	public void execute(World world) {
		if(world.random.nextFloat() <= probability)
			super.execute(world);
		else
			failure.executeResult();
	}
}
