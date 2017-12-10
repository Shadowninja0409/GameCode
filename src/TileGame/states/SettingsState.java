package TileGame.states;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.ui.UIManager;

public class SettingsState extends State{

	private UIManager uiManager;
	
	public SettingsState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}

}
