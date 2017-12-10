package TileGame.states;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.worlds.World;

public class GameState extends State{
	
	//private Player player;
	private World world;
	
	public static String worldSaveFile = "res/worlds/Start.txt";
	public static String entitySaveFile = "res/worlds/entitySaveFile.txt";
	public static String itemSaveFile = "res/worlds/itemSaveFile.txt";
	
	public GameState(Handler handler){
		super(handler);
		if(MenuState.load == false){
			world = new World(handler, worldSaveFile, "res/worlds/OriginalFile.txt", itemSaveFile);
		} else
		world = new World(handler, worldSaveFile, entitySaveFile, itemSaveFile);
		handler.setWorld(world);
	}
	
	@Override
	public void tick() {
		world.tick();
	}

	@Override
	
	public void render(Graphics g) {
		world.render(g);
	}
	


}
