package TileGame.states;

import java.awt.Graphics;

import TileGame.Game;
import TileGame.Handler;

public abstract class State {

	private static State currentState = null;
	
	public static void setState(State state){
		currentState = state;
	}
	
	public static State getState(){
		return currentState;
	}
	
	//Handler
	protected Handler handler;
	
	public State(Handler handler){
		this.handler = handler;
	}
	//CLASS
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	
}
