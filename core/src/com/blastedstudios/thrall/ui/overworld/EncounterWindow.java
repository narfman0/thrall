package com.blastedstudios.thrall.ui.overworld;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.world.World;
import com.blastedstudios.thrall.world.encounter.Encounter;
import com.blastedstudios.thrall.world.encounter.EncounterOption;

public class EncounterWindow extends Window{
	private final HashMap<Integer, Button> buttonMap = new HashMap<>();
	
	public EncounterWindow(Skin skin, AbstractGame game, World world,
			IEncounterCloseListener listener, Encounter encounter) {
		super("Encounter", skin);
		add(encounter.getEncounterText()).colspan(2);
		row();
		int optionNumber = 1;
		for(EncounterOption option : encounter.getOptions()){
			final Button optionButton = new TextButton(option.getChoiceText(), skin);
			optionButton.addListener(new ClickListener() {
				@Override public void clicked(InputEvent event, float x, float y) {
					option.getResult().executeResult(world);
					listener.closeClicked();
				}
			});
			add(optionNumber + ". ");
			add(optionButton);
			buttonMap.put(optionNumber++, optionButton);
			row();
		}
		pack();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
	}
	
	interface IEncounterCloseListener {
		void closeClicked();
	}

	public void dialogPressed(int i) {
		if(buttonMap.containsKey(i))
			for(EventListener listener : buttonMap.get(i).getListeners())
				if(listener instanceof ClickListener)
					((ClickListener)listener).clicked(null, 0, 0);
	}
}
