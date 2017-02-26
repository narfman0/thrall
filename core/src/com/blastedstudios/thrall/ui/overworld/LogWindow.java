package com.blastedstudios.thrall.ui.overworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.thrall.util.Log;
import com.blastedstudios.thrall.util.Log.LogListener;

public class LogWindow extends Window implements LogListener {
	private final Table logsArea;
	
	public LogWindow(Skin skin) {
		super("Log", skin);
		logsArea = new Table(skin);
		final TextField textField = new TextField("", skin);
		textField.setMessageText("<enter command>");
		final Button sendButton = new TextButton("Send", skin);
		sendButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
			}
		});
		add(new ScrollPane(logsArea)).colspan(2).fill();
		row();
		add(textField);
		add(sendButton);
		setWidth(400f);
		setHeight(150f);
		Log.addListener(this);
	}
	
	@Override public boolean remove(){
		Log.removeListener(this);
		return true;
	}
	
	private void addLog(String tag, String message, Color color){
		logsArea.add(message, "default-font", color);
		logsArea.row();
	}

	@Override
	public void error(String tag, String message) {
		addLog(tag, message, Color.RED);
	}

	@Override
	public void log(String tag, String message) {
		addLog(tag, message, Color.WHITE);
	}

	@Override
	public void debug(String tag, String message) {
		// meh we can skip this for now
//		addLog(tag, message, Color.GRAY);
	}
}
