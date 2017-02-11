package com.blastedstudios.thrall.ui;

import java.util.Stack;

import com.badlogic.gdx.Game;

public abstract class AbstractGame extends Game {
	private Stack<AbstractScreen> screenStack = new Stack<>();
	
	public AbstractScreen peekScreen(){
		return screenStack.peek();
	}

	public void pushScreen(AbstractScreen screen){
		screenStack.push(screen);
		setScreen(screen);
	}
	
	public AbstractScreen popScreen(){
		AbstractScreen previous = screenStack.pop();
		if(!screenStack.isEmpty())
			setScreen(screenStack.peek());
		return previous;
	}
}
