package TileGame.entities.creatures.npc;

import java.awt.Rectangle;

import TileGame.Handler;
import TileGame.entities.creatures.Creatures;
import TileGame.tiles.Tile;

public abstract class NPC extends Creatures {

	public NPC(Handler handler, float x, float y, int width, int height, int defaultAttack, int defaultAttackspeed, int id) {
		super(handler, x, y, width, height, defaultAttack, defaultAttackspeed, id, false);
		this.health = 999999;
	}
	
	public abstract void movePath();
	
	public abstract String text();
	public boolean intersects = false;

	public void areaDetection() {
		//creates rectangle for attacking
		Rectangle cb = getCollisionBounds(-(Tile.TILEWIDTH / 2),-(Tile.TILEHEIGHT / 2));
		Rectangle ar = new Rectangle();
		int arSize = 68;
		ar.width = arSize;
		ar.height = arSize;
		
		ar.x = cb.x;
		ar.y = cb.y;
		
		ar.width += Tile.TILEWIDTH;
		ar.height += Tile.TILEHEIGHT;

		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(ar)){
				intersects = true;
		} else intersects = false;
			
	}
	
}
