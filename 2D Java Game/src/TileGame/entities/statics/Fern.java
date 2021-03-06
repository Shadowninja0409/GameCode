package TileGame.entities.statics;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.gfx.Assets;
import TileGame.items.Item;
import TileGame.tiles.Tile;

public class Fern extends StaticEntity{

	private int id;
	
	public Fern(Handler handler, float x, float y, int id, boolean breakable) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, id, breakable);
		this.id = id;
		
		bounds.x = 17;
		bounds.y = (int)(height / 2 + height / 6) - 10;
		bounds.width = width / 2;
		bounds.height = (int)(height / 16) + 10;
	}

	@Override
	public boolean isBreakable() {
		return true;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.colors[20], (int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() - height / 5), null);
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int)(x + width / 6), (int)(y + (height / 2) + height / 8)));
	}

	@Override
	public boolean isTriggered() {
		return false;
	}

}
