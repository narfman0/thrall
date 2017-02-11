package com.blastedstudios.thrall.ui.overworld;

import com.badlogic.gdx.Gdx;
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
	public EncounterWindow(Skin skin, AbstractGame game, World world,
			IEncounterCloseListener listener, Encounter encounter) {
		super("Encounter", skin);
		final Button closeButton = new TextButton("Close", skin);
		closeButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.closeClicked();
			}
		});
		add(encounter.getEncounterText());
		row();
		for(EncounterOption option : encounter.getOptions()){
			final Button optionButton = new TextButton(option.getChoiceText(), skin);
			optionButton.addListener(new ClickListener() {
				@Override public void clicked(InputEvent event, float x, float y) {
					option.getResult().executeResult(world);
					listener.closeClicked();
				}
			});
			add(optionButton);
			row();
		}
		add(closeButton);
		pack();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
	}
	
	interface IEncounterCloseListener {
		void closeClicked();
	}
}
