package com.blastedstudios.thrall.world.encounter;

import com.blastedstudios.thrall.world.World;

public class EncounterOption {
	private final String choiceText;
	private final IEncounterHandler result;
	
	public EncounterOption(String choiceText, IEncounterHandler result){
		this.choiceText = choiceText;
		this.result = result;
	}

	public String getChoiceText() {
		return choiceText;
	}

	public void execute(World world) {
		result.executeResult();
	}
}
