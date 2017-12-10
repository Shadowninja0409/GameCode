package TileGame.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import TileGame.Handler;
import TileGame.dialogue.Dialogue;
import TileGame.entities.creatures.Player;
import TileGame.entities.creatures.npc.Tutorial;
import TileGame.tiles.Tile;

public class Hud {

	private Handler handler;
	private Player player;
	
	private int color = 8;
	private int healthBar = 3;
	private boolean active = true;
	
	private int statPosX = 10;
	private int statPosY = -5;
	
	private Dialogue dialogue;

	String text = "Press T to talk to NPCs, WASD to move, arrow keys to\nattack, E to open inventory, and ENTER to equip.";
	
	public Hud(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		
		dialogue = new Dialogue(handler, text);
	}
	
	public void render(Graphics g){
		Text.drawString(g, "Health:", handler.getGame().getWidth() - 225, 0, false, Color.RED, Assets.font28);
		Text.drawString(g, "Cash:" + player.getCash(), handler.getGame().getWidth() - 195, 35, false, Color.BLUE, Assets.font28);
		
		Text.drawString(g, "Attack: " + player.getAttack(), statPosX, statPosY, false, Color.WHITE, Assets.font28);
		Text.drawString(g, "ASpeed: " + (double) player.getAttackSpeed() / 1000.0, statPosX, statPosY + 25, false, Color.WHITE, Assets.font28);
		Text.drawString(g, "MSpeed: " + (int) player.getSpeed(), statPosX, statPosY + 50, false, Color.WHITE, Assets.font28);
		if(active){
			dialogue.render(g);
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_T)){
			active = false;
		}
		if(player.getHealth() == 3){
			color = 8;
			healthBar = 3;
		} else if (player.getHealth() == 2){
			color = 4;
			healthBar = 2;
		} else if (player.getHealth() == 1){
			color = 2;
			healthBar = 1;
		} else 
			healthBar = 0;
		
		for (int x = 0; x < healthBar; x++){
			g.drawImage(Assets.colors[0], handler.getGame().getWidth() - 106 + (x * 34), 2, (Tile.TILEWIDTH / 2), (Tile.TILEHEIGHT / 2), null);
			g.drawImage(Assets.colors[color], handler.getGame().getWidth() - 105 + (x * 34), 3, (Tile.TILEWIDTH / 2) - 2, (Tile.TILEHEIGHT / 2) - 2, null);
		}
	}
	
	
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}



	private long viewTime = 2000, veiwTimer = 0;

	public void tick(){
		veiwTimer += 5;
		
		if(veiwTimer > viewTime)
			active = false;
	}
	
}
