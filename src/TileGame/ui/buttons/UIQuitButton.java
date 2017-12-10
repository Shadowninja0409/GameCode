package TileGame.ui.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import TileGame.ui.ClickListener;
import TileGame.ui.UIObject;

public class UIQuitButton extends UIObject {

	private BufferedImage[] quit;
	private ClickListener clicker;
	
	public UIQuitButton(float x, float y, int width, int height ,BufferedImage[] quit, ClickListener clicker) {
		super(x, y, width, height);
		this.quit = quit;
		this.clicker = clicker;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if (hovering)
			g.drawImage(quit[3],(int) x, (int) y, width, height, null);
		else
			g.drawImage(quit[2],(int) x, (int) y, width, height, null);
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

}
