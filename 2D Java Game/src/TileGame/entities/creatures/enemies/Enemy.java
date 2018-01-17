package TileGame.entities.creatures.enemies;

import java.awt.Rectangle;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.entities.creatures.Creatures;

public abstract class Enemy extends Creatures {
	
	private long lastAttackTimer, attackCoolDown = 600, attackTimer = attackCoolDown;
	
	public Enemy(Handler handler, float x, float y, int width, int height, int defaultAttack, int defaultAttackspeed, int id) {
		super(handler, x, y, width, height, defaultAttack, defaultAttackspeed, id, true);
	}

	protected void checkAttacks() {
		//attack timer
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCoolDown)
			return;
		
		//creates rectangle for attacking
		Rectangle cb = getCollisionBounds(-10,-10);
		Rectangle ar = new Rectangle();
		int arSize = 48;
		ar.width = arSize;
		ar.height = arSize;
		
		ar.x = cb.x;
		ar.y = cb.y;
		
		ar.width += 20;
		ar.height += 20;
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			//if intersecting, attack
			if(e.getCollisionBounds(0, 0).intersects(ar) && e.equals(handler.getWorld().getEntityManager().getPlayer())){
				e.hurt((int)DEFAULT_ATTACK);
				System.out.println("player hurt by: " + DEFAULT_ATTACK);
				return;
			}
			
		}
		
	}
	
}
