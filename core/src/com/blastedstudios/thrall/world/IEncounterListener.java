package com.blastedstudios.thrall.world;

import com.blastedstudios.thrall.ui.overworld.SkillCheckWindow.ISkillCheckListener;
import com.blastedstudios.thrall.world.encounter.Encounter;

public interface IEncounterListener {
	void triggerEncounter(Encounter encounter);
	void triggerSkillCheck(ISkillCheckListener listener);
}
