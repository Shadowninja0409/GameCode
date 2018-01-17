package TileGame.entities.statics;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.entities.creatures.enemies.Enemy;
import TileGame.gfx.Assets;
import TileGame.shop.Shop;
import TileGame.tiles.Tile;

public class Blockcade extends StaticEntity {

	private int id;
	
	public Blockcade(Handler handler, float x, float y, int id, boolean breakable) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, id, breakable);
		this.setHealth(100);
		this.id = id;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 68;
		bounds.height = 68;
	}

	@Override
	public boolean isBreakable() {
		return false;
	}

	@Override
	public void tick() {
		int dead = 0;
		for(Entity e : handler.getWorld().getEntityManager().getDeadEntities()){
			if((e.getId() == 12 && e.isDead() == true) || (e.getId() == 13 && e.isDead() == true)){
				dead++;
			}
			if(dead == 2 && (e.getId() == 14 || e.getId() == 15 || e.getId() == 16)){
				e.active = false;
			}
			if(Shop.shopOpened && (e.getId() == 17 || e.getId() == 18 || e.getId() == 19 || e.getId() == 20)){
				e.active = false;
			}
		}
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.colors[5],(int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
	}

	@Override
	public void die() {
	}

	@Override
	public boolean isTriggered() {
		return false;
	}

}
