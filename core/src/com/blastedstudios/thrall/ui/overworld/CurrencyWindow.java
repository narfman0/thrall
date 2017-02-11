package com.blastedstudios.thrall.ui.overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.blastedstudios.thrall.ui.AbstractGame;
import com.blastedstudios.thrall.world.World;

public class CurrencyWindow extends Window {
	private final TextField cashField, ironField, peopleField, foodField, fuelField;
	
	public CurrencyWindow(Skin skin, AbstractGame game) {
		super("Currency", skin);
		cashField = new TextField("", skin);
		cashField.setDisabled(true);
		ironField = new TextField("", skin);
		ironField.setDisabled(true);
		peopleField = new TextField("", skin);
		peopleField.setDisabled(true);
		foodField = new TextField("", skin);
		foodField.setDisabled(true);
		fuelField = new TextField("", skin);
		fuelField.setDisabled(true);
		add("Cash: ");
		add(cashField);
		row();
		add("Iron: ");
		add(ironField);
		row();
		add("People: ");
		add(peopleField);
		row();
		add("Food: ");
		add(foodField);
		row();
		add("Fuel: ");
		add(fuelField);
		pack();
		setY(Gdx.graphics.getHeight() - getHeight());
	}
	
	public void render(float dt, World world){
		cashField.setText(world.getCash()+"");
		ironField.setText(world.getIron()+"");
		peopleField.setText(world.getPeople()+"");
		foodField.setText((int)world.getFood()+"");
		fuelField.setText((int)world.getFuel()+"");
	}
}
