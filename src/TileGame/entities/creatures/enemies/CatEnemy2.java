 package TileGame.entities.creatures.enemies;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.entities.creatures.Creatures;
import TileGame.entities.statics.Blockcade;
import TileGame.gfx.Assets;
import TileGame.items.Item;
import TileGame.tiles.Tile;

public class CatEnemy2 extends Enemy{

	private float x, y;
	private int id;
	
	public static boolean trigger = false;
	
	public CatEnemy2(Handler handler, float x, float y, int id) {
		super(handler, x, y, Creatures.DEFAULT_CREATURE_WIDTH, Creatures.DEFAULT_CREATURE_HEIGHT, (int) Creatures.DEFAULT_ATTACK, (int)Creatures.DEFAULT_ATTACKSPEED, id);
		this.x = x;
		this.y = y;
		this.id = id;
		active = true;
		
		
		bounds.x = 12;
		bounds.y = 0;
		bounds.width = 44;
		bounds.height = 44;
	}

	@Override
	public void tick() {
		movePath();
		checkAttacks();
	}

	int healthBar = 3;
	private int color = 8;

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.cat[0], (int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
		g.drawImage(Assets.colors[color],(int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), (Tile.TILEWIDTH / 4) - 8, (Tile.TILEHEIGHT / 8) - 2, null);
		if(this.getHealth() == 3){
			color = 8;
			healthBar = 3;
		} else if (this.getHealth() == 2){
			color = 4;
			healthBar = 2;
		} else if (this.getHealth() == 1){
			color = 2;
			healthBar = 1;
		} else 
			healthBar = 0;
	}  

	@Override
	public void die() {
		handler.getWorld().getEntityManager().getPlayer().setCash(handler.getWorld().getEntityManager().getPlayer().getCash() + 100) ;
		handler.getWorld().getItemManager().addItem(Item.spearItem.createNew((int)(x + width / 4), (int)(y + (height / 2) - height / 3)));
	}
	
	public void movePath(){
		int z = 18 * Tile.TILEWIDTH;
		int z2 = 17 * Tile.TILEHEIGHT;
			if(x == z && y <= z2){
				this.y+=2;
				this.bounds.y+=2;
			}
			if(x <= z && y == Tile.TILEHEIGHT * 13){
				this.x+=2;
				this.bounds.x+=2;

			}
			if(x == Tile.TILEWIDTH * 13 && y <= z2){
				this.y-=2;
				this.bounds.y-=2;

			} 
			if(x >= Tile.TILEWIDTH * 13 && y == z2){
				this.x-=2;
				this.bounds.x-=2;
			}
	}
}
