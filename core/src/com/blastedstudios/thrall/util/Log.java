package com.blastedstudios.thrall.util;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;

public class Log {
	private static final LinkedList<LogListener> listeners = new LinkedList<>();
	
	public static void debug(String tag, String message){
		Gdx.app.debug(tag, message);
		for(LogListener listener : listeners)
			listener.debug(tag, message);
	}
	
	public static void log(String tag, String message){
		Gdx.app.log(tag, message);
		for(LogListener listener : listeners)
			listener.log(tag, message);
	}
	
	public static void error(String tag, String message){
		Gdx.app.error(tag, message);
		for(LogListener listener : listeners)
			listener.error(tag, message);
	}
	
	public static void addListener(LogListener listener){
		listeners.add(listener);
	}
	
	public static void removeListener(LogListener listener){
		listeners.remove(listener);
	}
	
	public static void clearListeners(){
		listeners.clear();
	}
	
	public interface LogListener {
		void error(String tag, String message);
		void log(String tag, String message);
		void debug(String tag, String message);
	}
}
