package TileGame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import TileGame.Handler;
import TileGame.entities.creatures.Player;
import TileGame.entities.creatures.enemies.CatEnemy;
import TileGame.gfx.Assets;
import TileGame.gfx.Text;
import TileGame.ui.ClickListener;
import TileGame.ui.UIManager;
import TileGame.ui.buttons.UILoadButton;
import TileGame.ui.buttons.UIQuitButton;
import TileGame.ui.buttons.UIStartButton;
import TileGame.utils.Utils;

public class MenuState extends State{

	public static UIManager uiManager;
	
	public static boolean load = false;
	
	public MenuState(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIStartButton((handler.getWidth() / 2) - 64, (handler.getHeight() / 2) - 32, 128, 64, Assets.buttons, new ClickListener(){

			@Override
			public void onClick() {
				Player.settings = false;
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}}));
		
		uiManager.addObject(new UIQuitButton((handler.getWidth() / 2) - 64, (handler.getHeight() / 2) + 48, 128, 64, Assets.buttons, new ClickListener(){

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				System.exit(1);
			}}));
		
		uiManager.addObject(new UILoadButton((handler.getWidth() / 2) - 64, (handler.getHeight() / 2) + 128, 128, 64, Assets.buttons, new ClickListener(){

			@Override
			public void onClick() {
				Player.settings = false;
				handler.getMouseManager().setUIManager(null);
				load = true;
				handler.getWorld().loadWorld(GameState.worldSaveFile, GameState.entitySaveFile, GameState.itemSaveFile);
				State.setState(handler.getGame().gameState);
				load = false;
			}}));
	}
	
	int x = 0;

	
	@Override
	public void tick() {
		uiManager.tick();	
		

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());
		uiManager.render(g);
		Text.drawString(g, "NEKO ISLAND", handler.getGame().getWidth() / 2, handler.getGame().getHeight() / 6, true, Color.BLACK, Assets.font72);
	}

}
