 package TileGame.entities.creatures.enemies;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.entities.creatures.Creatures;
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
		bounds.y = 12;
		bounds.width = 44;
		bounds.height = 44;
	}

	@Override
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getX() > Tile.TILEWIDTH * 9 && (handler.getWorld().getEntityManager().getPlayer().getY() > Tile.TILEWIDTH * 9 || handler.getWorld().getEntityManager().getPlayer().getY() > Tile.TILEWIDTH * 18)) {
			aiMovePath();
		}
		checkAttacks();
	}

	@Override
	public void render(Graphics g) {
		checkHealth();
		g.drawImage(Assets.cat[0], (int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
		g.drawImage(Assets.colors[color],(int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), (Tile.TILEWIDTH / 4) - 8, (Tile.TILEHEIGHT / 8) - 2, null);
	}  

	public void aiMovePath(){
		if(handler.getWorld().getEntityManager().getPlayer().getX() < x && !checkEntityCollsions(-2, 0)) {
			bounds.x -= 2;
			x -= 2;
		}
		if(handler.getWorld().getEntityManager().getPlayer().getX() > x && !checkEntityCollsions(2, 0)) {
			xMove = 2;
			bounds.x += 2;
			x += 2;
		}
		if(handler.getWorld().getEntityManager().getPlayer().getY() < y && !checkEntityCollsions(0, -2)) {
			yMove = 2;
			bounds.y -= 2;
			y -= 2;
		}
		if(handler.getWorld().getEntityManager().getPlayer().getY() > y && !checkEntityCollsions(0, 2)) {
			yMove = 2;
			bounds.y += 2;
			y += 2;
		}

	}
	
	@Override
	public void die() {
		//Randomized.randomized((int)(x + width / 4), (int)(y + (height / 2) - height / 3));
		handler.getWorld().getEntityManager().getPlayer().setCash(handler.getWorld().getEntityManager().getPlayer().getCash() + 100) ;
		handler.getWorld().getItemManager().addItem(handler.getWorld().getItemManager().randomized((int)(x + width / 4), (int)(y + (height / 2) - height / 3)));
	}
	
}
