package com.blastedstudios.thrall.world;

import com.blastedstudios.thrall.world.encounter.Encounter;

public interface IEncounterListener {
	void triggerEncounter(Encounter encounter);
}
