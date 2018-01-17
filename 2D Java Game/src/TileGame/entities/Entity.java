package TileGame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import TileGame.Handler;

public abstract class Entity {
	
	public static final int DEFAULT_HEALTH = 100;
	protected Handler handler;
	protected float x, y;
	protected int width;
	protected int height;
	protected int health;
	protected int id;
	public boolean active = true;
	public boolean breakable;
	private boolean dead = false;

	public int healthBar = 4;
	public int color = 8;
	
	public Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width, int height, int id, boolean breakable){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.breakable = breakable;
		health = DEFAULT_HEALTH;
		
		bounds = new Rectangle(0, 0, width, height);
	}

	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
	public void checkHealth(){
		if(this.health <= 0){
			healthBar = 0;
		}else if(this.health <= 25){
			color = 2;
			healthBar = 1;
		} else if (this.health  <= 50){
			color = 4;
			healthBar = 2;
		}else if (this.health  <= 75){
			color = 4;
			healthBar = 3;
		} else if (this.health  <= 100){
		color = 8;
		healthBar = 4;
		} 
	}
	
	public void hurt(int attack){
		if(breakable) {
			health -= attack;
			System.out.println("ow");
			if(health <= 0){
				active = false;
				die();
			}
		}
	}
	
	public boolean checkEntityCollsions(float xOffset, float yOffset){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset,yOffset))){
					return true;
				}
		}
		return false;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), 
				(int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	
	
	public int getId() {
		return id;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	
	
}
