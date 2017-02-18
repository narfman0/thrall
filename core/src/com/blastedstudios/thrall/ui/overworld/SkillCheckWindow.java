package com.blastedstudios.thrall.ui.overworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.blastedstudios.thrall.ui.AbstractGame;

public class SkillCheckWindow extends Window{
	private final Slider slider;
	private final TextField resultText;
	private final ISkillCheckListener listener;
	private boolean directionRight = true, enabled = true;
	
	public SkillCheckWindow(Skin skin, AbstractGame game, ISkillCheckListener listener) {
		super("Skill Check", skin);
		this.listener = listener;
		Slider.SliderStyle sliderStyle = new Slider.SliderStyle(
				new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui/checkBar1.png")))),
				new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui/checkKnob1.png")))));
		slider = new Slider(0,  100,  .001f,  false,  sliderStyle);
		slider.setTouchable(Touchable.disabled);
		slider.setWidth(400f);
		resultText = new TextField("", skin);
				
		add("Press the action key 'e' for better outcome. Landing the ticket\n" +
				"in green yields highest success rate, yellow yields average\n" +
				"success rate, red yields lower success rate.").colspan(2);
		row();
		add(slider);
		add(resultText);
		pack();
		setY(Gdx.graphics.getHeight()/2 - getHeight()/3);
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
	}

	@Override public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
		
		if(enabled){
			if(directionRight && slider.getValue() >= 100 || !directionRight && slider.getValue() <= 0)
				directionRight = !directionRight;
			float amount = slider.getValue() + Gdx.graphics.getDeltaTime() * (directionRight ? 200 : -200);
			slider.setValue(amount);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.E)){
			enabled = false;
			float result = slider.getValue() < 33f ? 50f : slider.getValue() > 66f ? 5f : .17f + 1.51f*slider.getValue();
			if(result <= 5f)
				resultText.setText("Bad...");
			else if(result >= 99f)
				resultText.setText("Perfect!");
			else if(result >= 95f)
				resultText.setText("Excellent!");
			else if(result >= 80f)
				resultText.setText("Great!");
			else
				resultText.setText("Fine");
			Timer.schedule(new Task() {
				@Override
				public void run() {
					listener.checked(SkillCheckWindow.this, result/100f);
				}
			}, 1);
		}
	}
	
	public interface ISkillCheckListener{
		/**
		 * @param result % success of skill check. 50 is average, 0 is bad, 100 is good
		 */
		void checked(Window window, float result);
	}
}
