package com.blastedstudios.thrall.world.encounter;

import com.blastedstudios.thrall.world.World;

public class EncounterOption {
	protected final String choiceText;
	protected final IEncounterHandler result;
	
	public EncounterOption(String choiceText, IEncounterHandler result){
		this.choiceText = choiceText;
		this.result = result;
	}

	public String getChoiceText() {
		return choiceText;
	}

	public void execute(World world) {
		result.execute();
	}
}
