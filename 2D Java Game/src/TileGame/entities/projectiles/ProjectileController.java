package TileGame.entities.projectiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.gfx.Animation;
import TileGame.gfx.Assets;
import TileGame.tiles.Tile;

public class ProjectileController{
	
	LinkedList<Arrow> a = new LinkedList<Arrow>();
	LinkedList<Bullet> b = new LinkedList<Bullet>();
	
	private Arrow arrow;
	private Bullet bullet;
	private Animation explosion;

	private Handler handler;
	
	public ProjectileController(Handler handler){
		this.handler = handler;
		
	}
	
	public void tick() {
		for(int i = 0; i < a.size(); i++){
			arrow = a.get(i);
			
			arrow.tick();

			if(arrow.isDead())
			removeArrow(arrow);	
			
		
			
		}
		for(int n = 0; n < b.size(); n++) {
			bullet = b.get(n);
			
			bullet.tick();
			
			if(bullet.isDead() || bullet.getY() < Tile.TILEHEIGHT * 10 || bullet.getX() < 0 || bullet.getX() > Tile.TILEWIDTH * 9)
				removeBullet(bullet);	
		}
	}

	public void render(Graphics g) {
		for(int i = 0; i < a.size(); i++){
			arrow = a.get(i);
			
			arrow.render(g);
		}
		for(int n = 0; n < b.size(); n++) {
			bullet = b.get(n);
			
			bullet.render(g);
		}
	}
	
	public void addArrow(Arrow arrow){
		a.add(arrow);
	}
	
	public void removeArrow(Arrow arrow){
		a.remove(arrow);
	}
	
	public void addBullet(Bullet bullet){
		b.add(bullet);
	}
	
	public void removeBullet(Bullet bullet){
		b.remove(bullet);
	}
	
}
