package TileGame.states;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.worlds.World;

public class GameState extends State{
	
	//private Player player;
	public static World world;
	public static World world2;
	
	public static int level = 0;
	
	public static String worldSaveFile = "res/worlds/Start.txt";
	public static String world2SaveFile = "res/worlds/Level2.txt";
	public static String entitySaveFile = "res/worlds/entitySaveFile.txt";
	public static String itemSaveFile = "res/worlds/itemSaveFile.txt";
	
	public GameState(Handler handler){
		super(handler);
		if(MenuState.load == false){
			world = new World(handler, worldSaveFile, "res/worlds/OriginalFile.txt", itemSaveFile);
		} else {
			world = new World(handler, worldSaveFile, entitySaveFile, itemSaveFile);
		}
		
		world2 = new World(handler, world2SaveFile, entitySaveFile, itemSaveFile);	
		setWorld();
	
	}
	
	public void setWorld() {
		if(level == 0) {
			handler.setWorld(world);
		}else if(level == 1) {
			handler.setWorld(world2);
		}
	}
	
	@Override
	public void tick() {
		if(level == 0) {
			world.tick();	
		}else if(level == 1) {
			world2.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		if(level == 0) {
			world.render(g);
		} else if(level == 1) {
			world2.render(g);
		}
	}
	


}
