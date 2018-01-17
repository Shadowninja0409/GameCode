package TileGame.ui.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import TileGame.ui.ClickListener;
import TileGame.ui.UIObject;

public class UISettingsButton extends UIObject {

	private BufferedImage[] settings;
	private ClickListener clicker;
	
	public UISettingsButton(float x, float y, int width, int height ,BufferedImage[] settings, ClickListener clicker) {
		super(x, y, width, height);
		this.settings = settings;
		this.clicker = clicker;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if (hovering)
			g.drawImage(settings[9],(int) x, (int) y, width, height, null);
		else
			g.drawImage(settings[8],(int) x, (int) y, width, height, null);
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

}

