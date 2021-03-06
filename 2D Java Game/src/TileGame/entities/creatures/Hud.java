package TileGame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import TileGame.Handler;
import TileGame.dialogue.Dialogue;
import TileGame.entities.Entity;
import TileGame.entities.creatures.npc.Tutorial;
import TileGame.gfx.Assets;
import TileGame.gfx.Text;
import TileGame.tiles.Tile;

public class Hud {

	private Handler handler;
	private Player player;
	
	private int color = 8;
	private boolean active = true;
	public boolean bossActive = false;
	
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
		
		int healthBar = player.getHealth();
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_T)){
			active = false;
		}else if(player.getHealth() <= handler.getWorld().getEntityManager().getPlayer().getHealth() * 1/4){
			color = 2;
		} else if (player.getHealth() <= handler.getWorld().getEntityManager().getPlayer().getHealth() * 2/4){
			color = 4;
		} else if (player.getHealth() <= handler.getWorld().getEntityManager().getPlayer().getHealth() * 3/4){
			color = 4;
		} else if (player.getHealth() <= handler.getWorld().getEntityManager().getPlayer().getHealth()){
			color = 8;
		} 
		int bossHealth = 0;
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.getId() == 21 && e.active)
				bossHealth = e.getHealth();
		}
		if(bossActive) {
			for(Entity e : handler.getWorld().getEntityManager().getDeadEntities()) {
				if(e.getId() == 17 || e.getId() == 18 || e.getId() == 19 || e.getId() == 20)
					e.setActive(true);
			}
			for(int y = 0; y < bossHealth; y++) {
				g.drawImage(Assets.colors[0], handler.getGame().getWidth() - 106 + (y * 34), 2, (Tile.TILEWIDTH / 2), (Tile.TILEHEIGHT / 2), null);
				g.drawImage(Assets.colors[2], (handler.getGame().getWidth() / 2) - 250 + y, 30, (Tile.TILEWIDTH / 32) , (Tile.TILEHEIGHT / 2) - 2 , null);
			}
		}
		for (int x = 0; x < healthBar; x++){
			g.drawImage(Assets.colors[0], handler.getGame().getWidth() - 106 + (x * 34), 2, (Tile.TILEWIDTH / 2), (Tile.TILEHEIGHT / 2), null);
			g.drawImage(Assets.colors[color], handler.getGame().getWidth() - 104 + x, 3, (Tile.TILEWIDTH / 32) , (Tile.TILEHEIGHT / 2) - 2 , null);
		}
	}
	
	
	
	public boolean isBossActive() {
		return bossActive;
	}

	public void setBossActive(boolean bossActive) {
		this.bossActive = bossActive;
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
