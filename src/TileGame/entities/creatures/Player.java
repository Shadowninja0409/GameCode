package TileGame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.entities.EntityManager;
import TileGame.entities.creatures.npc.NPC;
import TileGame.entities.creatures.npc.Tutorial;
import TileGame.gfx.Animation;
import TileGame.gfx.Assets;
import TileGame.gfx.Hud;
import TileGame.inventory.Inventory;
import TileGame.items.Item;
import TileGame.states.State;
import TileGame.tiles.Tile;
import TileGame.worlds.World;

public class Player extends Creatures {

	private Animation animUp, animDown, animLeft, animRight;
	//Attack Timer
	private long lastAttackTimer, attackCoolDown = 400, attackTimer = attackCoolDown;
	//Inventory
	private Inventory inventory;
	private Hud hud;

	private int attackSpeedBuff = 0;
	public ArrayList<Item> getItems() {
		return items;
	}

	private ArrayList<Item> items;
	@SuppressWarnings("unused")
	private int id;
	private int cash;
	
	public Player(Handler handler, float x, float y, int id) {
		super(handler, x, y, Creatures.DEFAULT_CREATURE_WIDTH, Creatures.DEFAULT_CREATURE_HEIGHT, (int) Creatures.DEFAULT_ATTACK, (int)Creatures.DEFAULT_ATTACKSPEED, id);
		bounds.x = 12;
		bounds.y = 25;
		bounds.width = 44;
		bounds.height = 35;
		
		this.id = id;
		
		int attackSpeed = (int) (Player.DEFAULT_ATTACKSPEED + attackSpeedBuff);
		animUp = new Animation(attackSpeed, Assets.player_up);
		animDown = new Animation(attackSpeed, Assets.player_down);
		animLeft = new Animation(attackSpeed, Assets.player_left);
		animRight = new Animation(attackSpeed, Assets.player_right);
		
		inventory = new Inventory(handler);
		items = inventory.getInventoryItems();
		
		hud = new Hud(handler, this);
	
		cash = 0;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	@Override
	public void tick() {
		//Animations
		if( handler.getKeyManager().w || handler.getKeyManager().s || 
			handler.getKeyManager().a || handler.getKeyManager().d){
		animUp.tick();
		animDown.tick();
		animLeft.tick();
		animRight.tick();
		} 
		
		hud.tick();
		//Movement
		getInput();
		move();
		reset();
		handler.getGameCamera().centerOnEntitiy(this);
		//attack
		checkAttacks();
		inventory.tick();
	}

	private void checkAttacks() {
		//attack timer
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCoolDown)
			return;
		
		if(inventory.isActive())
			return;
		
		
		//creates rectangle for attacking
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 40;
		ar.width = arSize;
		ar.height = arSize / 2;
		
		if (handler.getKeyManager().up){
			ar.x = cb.x + cb.width / 2 + arSize / 2;
			ar.y = cb.y - arSize;
		
		}else if (handler.getKeyManager().down){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
			
		}else if (handler.getKeyManager().left){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		
		}else if (handler.getKeyManager().right){
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
			
		}else{
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			//if intersecting, attack
			if(e.getCollisionBounds(0, 0).intersects(ar)){
				e.hurt((int) attack);
				return;
			}
			
		}
		
	}

	@Override
	public void die(){
		System.out.println("You Died");
		System.out.println("Armorments unequipped");
		handler.getWorld().getEntityManager().resetHp();
		handler.getWorld().getEntityManager().getPlayer().getInventory().clearInventory();
		handler.getWorld().getItemManager().unEquipAll();
		attack = DEFAULT_ATTACK;
		speed = DEFAULT_SPEED;
		active = true;
		handler.getWorld().getEntityManager().getPlayer().x = handler.getWorld().spawnX * Tile.TILEWIDTH;
		handler.getWorld().getEntityManager().getPlayer().y = handler.getWorld().spawnY * Tile.TILEHEIGHT;

	}
	
	public void reset(){
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_HOME)){
			handler.getWorld().getEntityManager().getPlayer().x = handler.getWorld().spawnX * Tile.TILEWIDTH;
			handler.getWorld().getEntityManager().getPlayer().y = handler.getWorld().spawnY * Tile.TILEHEIGHT;
		}
	}
	
	private void getInput(){
		xMove = 0;
		yMove = 0;
		
		if(inventory.isActive())
			return;
		
		if(handler.getKeyManager().w){
			yMove = -speed;
		}
		if(handler.getKeyManager().s){
			yMove = speed;
		}
		if(handler.getKeyManager().a){
			xMove = -speed;
		}
		if(handler.getKeyManager().d){
			xMove = speed;;
		}
	}
	
	private BufferedImage lastFrame = Assets.player_down[0];

	
	@Override
	public void render(Graphics g) {
//		g.drawImage(Assets.colors[2], (int) (x - handler.getGameCamera().getxOffset() + 250),
//				(int) (y - handler.getGameCamera().getyOffset() - 200), width / 2, height / 2, null);
		hud.render(g);
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	}
	
	public void postRender(Graphics g){
		inventory.render(g);
	}
	private BufferedImage getCurrentAnimationFrame(){
		if(xMove < 0){
			lastFrame = Assets.player_left[0];
			return animLeft.getCurrentFrame();
		} else if(xMove > 0){
			lastFrame = Assets.player_right[0];
			return animRight.getCurrentFrame();
		} else if(yMove < 0){
			lastFrame = Assets.player_up[0];
			return animUp.getCurrentFrame();
		} else if(yMove > 0){
			lastFrame = Assets.player_down[0];
			return animDown.getCurrentFrame();
		}else if (xMove == 0 && yMove == 0){
			return lastFrame;
		} else
			return animDown.getCurrentFrame();	
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Hud getHud() {
		return hud;
	}

	public void setHud(Hud hud) {
		this.hud = hud;
	}
	
}
