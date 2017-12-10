package TileGame.entities.statics;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.entities.creatures.enemies.Enemy;
import TileGame.gfx.Assets;
import TileGame.tiles.Tile;

public class Blockcade extends StaticEntity {

	private int id;
	
	public Blockcade(Handler handler, float x, float y, int id) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, id);
		this.setHealth(9999);
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
		if(isTriggered()){
			die();
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
