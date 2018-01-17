package TileGame.entities.projectiles;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.gfx.Animation;
import TileGame.gfx.Assets;
import TileGame.tiles.Tile;

public class Arrow extends Entity{
		
	private Handler handler;
	private Animation explosion;
	private boolean collides = false;
	private int total = 2;
	
	private int width = 10, height = 40, dir;
	private Entity entity;
	public Arrow(Handler handler, float x, float y, int dir, Entity entity) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEWIDTH, 0, true);
		this.handler = handler;
		this.entity = entity;
		
		this.dir = dir;
		
		bounds.x = (int) handler.getWorld().getEntityManager().getPlayer().getX() + 20;
		bounds.y = (int) handler.getWorld().getEntityManager().getPlayer().getY() + 15;
		bounds.width = width;
		bounds.height = height;
		
		explosion = new Animation(200, Assets.explosion);
		
	}

	public void tick(){
		if(!isCollides()) {
			move();
		}
		if(isCollides())
			explosion.tick();
		checkCollision();
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
				e.hurt(handler.getWorld().getEntityManager().getPlayer().getAttack());
				bounds.width = 0;
				bounds.height = 0;
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
			bounds.width = width;
			bounds.height = height;
			bounds.y -= 4;
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
		//	g.drawRect(bounds.x - (int)(handler.getGameCamera().getxOffset()), bounds.y - (int)(handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
 			g.drawImage(Assets.arrow[dir], (bounds.x - (int)(handler.getGameCamera().getxOffset())) - 30, (bounds.y - (int)(handler.getGameCamera().getyOffset())) - 30, Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
 			if(isCollides()) {
 	 			g.drawImage(explosion.getCurrentFrame(), (bounds.x - (int)(handler.getGameCamera().getxOffset())) - 30, (bounds.y - (int)(handler.getGameCamera().getyOffset())) - 30, Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
 	 			if(explosion.index >= total)
 	 			this.setDead(true);
 			}
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void die() {
	}

	public boolean isCollides() {
		return collides;
	}

	public void setCollides(boolean collides) {
		this.collides = collides;
	}


}
