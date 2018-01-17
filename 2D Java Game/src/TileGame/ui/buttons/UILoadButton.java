package TileGame.ui.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import TileGame.ui.ClickListener;
import TileGame.ui.UIObject;

public class UILoadButton extends UIObject{
	// going to load last saved world
	
	private BufferedImage[] images;
	private ClickListener clicker;
	
	public UILoadButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
		
		
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if(hovering)
			g.drawImage(images[5], (int)x, (int)y, width, height, null);
		else
			g.drawImage(images[4], (int)x, (int)y, width, height, null);
		
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

}
