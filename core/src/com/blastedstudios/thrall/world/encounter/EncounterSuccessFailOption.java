package com.blastedstudios.thrall.world.encounter;

import com.blastedstudios.thrall.world.World;

public class EncounterSuccessFailOption extends EncounterOption{
	protected final float probability; // [0-1]
	protected final IEncounterHandler failure;
	
	public EncounterSuccessFailOption(float probability, String choiceText,
			IEncounterHandler success, IEncounterHandler failure){
		super(choiceText, success);
		this.probability = probability;
		this.failure = failure;
	}
	
	@Override
	public String getChoiceText() {
		return String.format("%.2f", probability) + "%: " + choiceText;
	}

	@Override
	public void execute(World world) {
		if(world.random.nextFloat() <= probability)
			result.execute();
		else
			failure.execute();
	}
}
