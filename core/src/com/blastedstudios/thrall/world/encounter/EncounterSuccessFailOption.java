package com.blastedstudios.thrall.world.encounter;

import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.blastedstudios.thrall.util.Log;
import com.blastedstudios.thrall.world.IEncounterListener;
import com.blastedstudios.thrall.world.World;

public class EncounterSuccessFailOption extends EncounterOption{
	private static final String TAG = EncounterSuccessFailOption.class.getCanonicalName();
	protected final float probability; // [0-1] success
	protected final IEncounterHandler failure;
	private final IEncounterListener listener;
	
	public EncounterSuccessFailOption(float probability, IEncounterListener listener,
			String choiceText, IEncounterHandler success, IEncounterHandler failure){
		super(choiceText, success);
		this.probability = probability;
		this.listener = listener;
		this.failure = failure;
	}
	
	@Override
	public String getChoiceText() {
		return String.format("%.2f", probability*100) + "%: " + choiceText;
	}

	@Override
	public void execute(World world) {
		listener.triggerSkillCheck((Window window, float resultProbability) -> {
			float roll = world.random.nextFloat();
			float successChance = probability*resultProbability;
			if(roll <= probability*resultProbability)
				result.execute();
			else
				failure.execute();
			Log.log(TAG, String.format("Rolled %.2f vs p=%.2f*%.2f=%.2f",
					1f-roll, probability, resultProbability, successChance));
		});
	}
}
