package TileGame.ui.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import TileGame.ui.ClickListener;
import TileGame.ui.UIObject;

public class UISaveButton extends UIObject{

	private BufferedImage[] save;
	private ClickListener clicker;
	
	public UISaveButton(float x, float y, int width, int height, BufferedImage[] save, ClickListener clicker) {
		super(x, y, width, height);
		this.save = save;
		this.clicker = clicker;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if (hovering)
			g.drawImage(save[7],(int) x, (int) y, width, height, null);
		else
			g.drawImage(save[6],(int) x, (int) y, width, height, null);
	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

}
