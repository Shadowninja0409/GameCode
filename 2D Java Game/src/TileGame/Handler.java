package TileGame;

import TileGame.entities.Entity;
import TileGame.entities.statics.StaticEntity;
import TileGame.gfx.GameCamera;
import TileGame.inputs.KeyManager;
import TileGame.inputs.MouseManager;
import TileGame.states.GameState;
import TileGame.states.MenuState;
import TileGame.states.State;
import TileGame.worlds.World;

public class Handler {

	private Game game;
	private World world;
	
	public Handler(Game game){
		this.game = game;
	}

	public GameCamera getGameCamera(){
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager(){
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager(){
		return game.getMouseManager();
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public void mainMenu() {
		State.setState(getGame().menuState);
		getMouseManager().setUIManager(MenuState.uiManager);
	}
	
	public void switchLevels() {
		for(Entity e : getWorld().getEntityManager().getEntities()) {
			if(e.getId() != getWorld().getEntityManager().getPlayer().getId()) {
				e.active = false;
			}
		}
		GameState.level++;
		setWorld(GameState.world2);
		
	}

	
	
}
