package TileGame;

import TileGame.entities.statics.StaticEntity;
import TileGame.gfx.GameCamera;
import TileGame.inputs.KeyManager;
import TileGame.inputs.MouseManager;
import TileGame.worlds.World;

public class Handler {

	private Game game;
	private World world;
	private StaticEntity staticEntity;
	
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

}
