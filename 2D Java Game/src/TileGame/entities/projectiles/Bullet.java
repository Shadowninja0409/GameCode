package TileGame.entities.projectiles;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.entities.creatures.enemies.VampireBoss;
import TileGame.gfx.Animation;
import TileGame.gfx.Assets;
import TileGame.tiles.Tile;

public class Bullet extends Entity{

	private Handler handler;
	private Animation explosion;
	private boolean collides = false;
	private int total = 2;
	
	private int width = 68, height = 68, dir;
	private Entity entity;
	
	public Bullet(Handler handler, float x, float y, int dir, Entity entity) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEWIDTH, 0, true);
		this.handler = handler;
	
		this.entity = entity;
		this.dir = dir;
		
		bounds.x = (int) x;
		bounds.y = (int) y;
		bounds.width = width;
		bounds.height = height;
		
		explosion = new Animation(100, Assets.explosion);
		
	}

	public void tick(){
		checkCollision();
		if(!isCollides()) {
			move();
		}
		if(isCollides())
			explosion.tick();
	}
	
	private long lastAttackTimer, attackCoolDown = 100, attackTimer = attackCoolDown;


	public void checkCollision(){
		
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCoolDown)
			return;
		
		if(handler.getWorld().getEntityManager().getPlayer().getInventory().isActive() || handler.getWorld().getEntityManager().getPlayer().getShop().isActive())
			return;
	
		attackTimer = 0;

		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this) || e.equals(entity))
				continue;
			//if intersecting, attack
			if(e.getCollisionBounds(0, 0).intersects(bounds)){
				e.hurt(5);
//				bounds.width = 0;
//				bounds.height = 0;
				setCollides(true);
				return;
				
			}
			
		}
	}
	
	
	public void move(){
		if(dir == 0){
			bounds.width = height;
			bounds.height = width;
			bounds.x -= 4;
		} else if(dir == 1){
			bounds.y -= 4;
			y -= 4;
		} else if(dir == 2){
			bounds.width = height;
			bounds.height = width;
			bounds.x += 4;
		} else if(dir == 3){
			bounds.width = width;
			bounds.height = height;
			bounds.y += 4;
		}
	}
	
	int index = 0;

	public void render(Graphics g) {
			g.drawImage(Assets.bullet[dir], (bounds.x - (int)(handler.getGameCamera().getxOffset())), (bounds.y - (int)(handler.getGameCamera().getyOffset())), Tile.TILEWIDTH / 2, Tile.TILEHEIGHT / 2, null);
 			if(y < 0)
 				this.setDead(true);
 			if(isCollides()) {
 	 			g.drawImage(explosion.getCurrentFrame(), (bounds.x - (int)(handler.getGameCamera().getxOffset())), (bounds.y - (int)(handler.getGameCamera().getyOffset())), Tile.TILEWIDTH / 2, Tile.TILEHEIGHT / 2, null);
 	 			if(explosion.index >= total)
 	 			this.setDead(true);
 			}
	}


	@Override
	public void die() {
		
	}

	public float getY(){
		return this.y;
	}
	
	public float getX(){
		return this.x;
	}
	
	public boolean isCollides() {
		return collides;
	}

	public void setCollides(boolean collides) {
		this.collides = collides;
	}
	
}
