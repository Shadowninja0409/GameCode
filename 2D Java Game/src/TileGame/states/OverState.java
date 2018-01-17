package TileGame.states;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.gfx.Assets;
import TileGame.ui.ClickListener;
import TileGame.ui.UIManager;
import TileGame.ui.buttons.UIStartButton;

public class OverState extends State{

	private UIManager uiManager;

	
	public OverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
	
	}

	@Override
	public void tick() {
	uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
	uiManager.render(g);
	}

}
