package TileGame.entities.statics;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.gfx.Assets;
import TileGame.items.Item;
import TileGame.tiles.Tile;

public class Tree extends StaticEntity {

	private int id;
	
	public Tree(Handler handler, float x, float y, int id, boolean breakable) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2, id, breakable);
		this.id = id;
		
		bounds.x = 10;
		bounds.y = (int)(height / 2 + height / 6) - 10;
		bounds.width = width / 2 + 14;
		bounds.height = (int)(height / 16) + 10;
		
	}
	
	public void tick(){
		
	}

	@Override
	public void die(){
		handler.getWorld().getItemManager().addItem(Item.berryItem.createNew((int)(x + width / 6), (int)(y + (height / 2) + height / 8)));
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.colors[18], (int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() - height / 5), null);
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
