package com.blastedstudios.thrall.util;

import com.badlogic.gdx.Gdx;

public class Log {
	public static void debug(String tag, String message){
		Gdx.app.debug(tag, message);
	}
	
	public static void log(String tag, String message){
		Gdx.app.log(tag, message);
	}
	
	public static void error(String tag, String message){
		Gdx.app.error(tag, message);
	}
}
