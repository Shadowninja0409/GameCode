package TileGame.ui.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import TileGame.ui.ClickListener;
import TileGame.ui.UIObject;

public class UISaveQuitButton extends UIObject {

	private BufferedImage[] savequit;
	private ClickListener clicker;
	
	public UISaveQuitButton(float x, float y, int width, int height ,BufferedImage[] savequit, ClickListener clicker) {
		super(x, y, width, height);
		this.savequit = savequit;
		this.clicker = clicker;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if (hovering)
			g.drawImage(savequit[11],(int) x, (int) y, width, height, null);
		else
			g.drawImage(savequit[10],(int) x, (int) y, width, height, null);
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

}
