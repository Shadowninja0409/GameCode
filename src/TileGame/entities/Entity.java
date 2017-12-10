package TileGame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import TileGame.Handler;

public abstract class Entity {
	
	public static final int DEFAULT_HEALTH = 3;
	protected Handler handler;
	protected float x, y;
	protected int width;
	protected int height;
	protected int health;
	protected int id;
	public boolean active = true;
	private boolean dead = false;


	public Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width, int height, int id){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		health = DEFAULT_HEALTH;
		
		bounds = new Rectangle(0, 0, width, height);
	}

	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
	public void hurt(int attack){
		health -= attack;
		if(health <= 0){
			active = false;
			die();
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
