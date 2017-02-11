package com.blastedstudios.thrall.world.encounter;

import java.util.List;

public class Encounter {
	private final List<EncounterOption> options;
	private final String encounterText;
	
	public Encounter(List<EncounterOption> options, String encounterText){
		this.options = options;
		this.encounterText = encounterText;
	}

	public List<EncounterOption> getOptions() {
		return options;
	}

	public String getEncounterText() {
		return encounterText;
	}
}
