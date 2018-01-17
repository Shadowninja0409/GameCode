package TileGame.entities.statics;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.gfx.Assets;
import TileGame.tiles.Tile;

public class BrickWall extends StaticEntity{

	private int id;
	public BrickWall(Handler handler, float x, float y, int id, boolean breakable) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, id, breakable);		
		this.id = id;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = (int) (width);
		bounds.height = (int)(height);
	}

	@Override
	public void tick() {

	}

	@Override
	public void die() {
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.colors[19], (int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
	}

	@Override
	public boolean isBreakable() {
		return true;
	}

	@Override
	public boolean isTriggered() {
		return false;
	}

	

}
