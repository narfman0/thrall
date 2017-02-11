package com.blastedstudios.thrall.world.encounter;

public class EncounterOption {
	private final String choiceText;
	private final IEncounterResult result;
	
	public EncounterOption(String choiceText, IEncounterResult result){
		this.choiceText = choiceText;
		this.result = result;
	}

	public String getChoiceText() {
		return choiceText;
	}

	public IEncounterResult getResult() {
		return result;
	}
}
