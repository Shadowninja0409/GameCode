package TileGame.entities.statics;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.gfx.Assets;
import TileGame.items.Item;
import TileGame.tiles.Tile;

public class Chest extends StaticEntity{

	private int id;
	
	public Chest(Handler handler, float x, float y, int id) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEWIDTH, id);
		this.id = id;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = (int) (width);
		bounds.height = (int)(height);
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
		g.drawImage(Assets.chest,(int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.bootsItem.createNew((int)(x + width / 4), (int)(y + (height / 2) - height / 3)));
	}

	@Override
	public boolean isTriggered() {
		return false;
	}

}
