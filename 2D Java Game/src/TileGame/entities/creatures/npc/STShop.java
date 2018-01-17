package TileGame.entities.creatures.npc;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import TileGame.Handler;
import TileGame.dialogue.Dialogue;
import TileGame.gfx.Assets;
import TileGame.items.Item;
import TileGame.shop.Shop;

public class STShop extends NPC{

	private Dialogue dialogue;
	
	public STShop(Handler handler, float x, float y, int width, int height, int defaultAttack, int defaultAttackspeed, int id) {
		super(handler, x, y, width, height, defaultAttack, defaultAttackspeed, id);
		
	}

	@Override
	public void movePath() {
		
	}

	@Override
	public String text() {
		String text = "Hi welcome to my shop! Would you like to buy\nsomething?";
		if(this.intersects == true && handler.getWorld().getEntityManager().getPlayer().getShop().isPurchase()){
			text = "Thank you for your purchase, please come again";
		} else{
			handler.getWorld().getEntityManager().getPlayer().getShop().setPurchase(false);
			text = "Hi welcome to my shop! Would you like to buy\nsomething?";
		}
		return text;
	}

	@Override
	public void tick() {
		areaDetection();
	}


	

	
	
	@Override
	public void render(Graphics g) {
		dialogue = new Dialogue(handler, text());

		if(this.intersects == true){
			dialogue.render(g);
			if(this.intersects == true && handler.getWorld().getEntityManager().getPlayer().getShop().isActive())
				Shop.shopOpened = true;
		}else{
			handler.getWorld().getEntityManager().getPlayer().getShop().setActive(false);
		}
		g.drawImage(Assets.cat[0], (int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
	}

	@Override
	public void die() {
		
	}
	

}
