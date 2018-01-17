package TileGame.dialogue;

import java.awt.Color;
import java.awt.Graphics;

import TileGame.Handler;
import TileGame.gfx.Assets;
import TileGame.gfx.Text;

public class Dialogue {

	private Handler handler;
	
	private int xPos = 50;
	private int yPos = 82;

	private String text;
	
	public Dialogue(Handler handler, String text){
		this.handler = handler;
		this.text = text;
	}
	
	public void render(Graphics g){
		g.clearRect(xPos - 10, yPos - 2, handler.getGame().getWidth() - 55, 58);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(xPos - 10, yPos - 2, handler.getGame().getWidth() - 55, 58);
		Text.drawString(g, text, 50, 82, false, Color.white, Assets.font18);

	}
	
}
