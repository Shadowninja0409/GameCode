package TileGame.entities.creatures.enemies;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.entities.creatures.Creatures;
import TileGame.entities.creatures.Hud;
import TileGame.entities.projectiles.Arrow;
import TileGame.entities.projectiles.Bullet;
import TileGame.entities.projectiles.ProjectileController;
import TileGame.gfx.Assets;
import TileGame.shop.Shop;
import TileGame.states.GameState;
import TileGame.tiles.Tile;
import TileGame.utils.FileSerialization;

public class VampireBoss extends Enemy {

	private ProjectileController c;

	public static float x, y;
	
	public VampireBoss(Handler handler, float x, float y, int id) {
		super(handler, x, y, Creatures.DEFAULT_CREATURE_WIDTH * 2, Creatures.DEFAULT_CREATURE_HEIGHT * 2, (int) Creatures.DEFAULT_ATTACK, (int)Creatures.DEFAULT_ATTACKSPEED, id);
		VampireBoss.x = x;
		VampireBoss.y = y;
		bounds.x = 10;
		bounds.y = 10;
		bounds.width = 0;
		bounds.height = 0;
		this.health = 500;
		c = new ProjectileController(handler);

	}

	private long lastAttackTimer, attackCoolDown = 800, attackTimer = attackCoolDown;

	@Override
	public void tick() {
		c.tick();
		handler.getWorld().getEntityManager().getPlayer().getHud().setBossActive(false);
		if(handler.getWorld().getEntityManager().getPlayer().getX() <= Tile.TILEWIDTH * 9 && handler.getWorld().getEntityManager().getPlayer().getY() >= Tile.TILEWIDTH * 13) {
			handler.getWorld().getEntityManager().getPlayer().getHud().setBossActive(true);
			bounds.width = 116;
			bounds.height = 116;

			movePath();
		
			attackTimer += System.currentTimeMillis() - lastAttackTimer;
			lastAttackTimer = System.currentTimeMillis();
			if(attackTimer < attackCoolDown)
				return;
			
			c.addBullet(new Bullet(handler, x, y, 1, this));
			c.addBullet(new Bullet(handler, x + 96, y, 1, this));
			
			if(handler.getWorld().getEntityManager().getPlayer().getY() >= Tile.TILEWIDTH * 19){
				
				c.addBullet(new Bullet(handler, x, y + 116, 0, this));
				c.addBullet(new Bullet(handler, x, y, 0, this));
				c.addBullet(new Bullet(handler, x + 96, y + 116, 2, this));
				c.addBullet(new Bullet(handler, x + 96, y, 2, this));
				c.addBullet(new Bullet(handler, x, y + 116, 3, this));
				c.addBullet(new Bullet(handler, x + 96, y + 116, 3, this));
				
			}else handler.getWorld().getEntityManager().getPlayer().getHud().setBossActive(true);


			attackTimer = 0;
			
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.vampireKitty, (int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
		c.render(g);

	}

	@Override
	public void die() {
		FileSerialization.writeFile(GameState.itemSaveFile, handler.getWorld().getEntityManager().getPlayer().getInventory().inventoryData());
		
		handler.switchLevels();
	}

	private boolean left = true;
	private boolean right = false;
	private int leftM; 
	
	public void movePath(){
		if(x <= Tile.TILEWIDTH) {
			left = false;
			right = true;
		}else if(x >= 7 * Tile.TILEWIDTH) {
			left = true;
			right = false;
		}
	
		if(left == true) {
			leftM = -3;
		} else if(right == true) {
			leftM = 3;
		}
		
		x += leftM;
		bounds.x += leftM;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
}