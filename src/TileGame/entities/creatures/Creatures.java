package TileGame.entities.creatures;

import java.awt.Graphics;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.tiles.Tile;

public abstract class Creatures extends Entity{
	
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 67, DEFAULT_CREATURE_HEIGHT = 67 ;
	public static final float DEFAULT_ATTACK = 1;
	public static final float DEFAULT_ATTACKSPEED = 500;

	protected float attack;
	protected float attackSpeed;
	protected float speed;

	protected float xMove;
	protected float yMove;


	public Creatures(Handler handler, float x, float y, int width, int height, int defaultAttack, int defaultAttackspeed, int id) {
		super(handler, x, y, width, height, id);
		speed = DEFAULT_SPEED;
		attack = DEFAULT_ATTACK;
		attackSpeed = DEFAULT_ATTACKSPEED;
		xMove = 0;
		yMove = 0;
	}
		
	public void move(){
		if(!checkEntityCollsions(xMove, 0f))
			moveX();
		if(!checkEntityCollsions(0f, yMove))
			moveY();
		
	}
	
	public void moveX(){
		if(xMove > 0){
			
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) 
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
				x += xMove;
			}else{
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}
			
		}else if(xMove < 0){
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) 
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
				x += xMove;
			}else{
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}
		}
	}
	
	public void moveY(){
		if(yMove < 0){
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)){
				y += yMove;
			}else{
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}
		}else if(yMove > 0){
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)){
				y += yMove;
			}else{
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x ,int y){
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	//GETTERS AND SETTERS
	public float getxMove() {
		return xMove;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}
	
	public void setAttackSpeed(float attackSpeed){
		this.attackSpeed = attackSpeed;
	}
	
	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMOve(float yMove) {
		this.yMove = yMove;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getAttack() {
		return (int) attack;
	}

}
