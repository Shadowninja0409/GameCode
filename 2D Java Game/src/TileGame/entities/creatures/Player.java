package TileGame.entities.creatures;

import java.awt.AlphaComposite;
import java.awt.Color;
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
import TileGame.entities.projectiles.Arrow;
import TileGame.entities.projectiles.ProjectileController;
import TileGame.gfx.Animation;
import TileGame.gfx.Assets;
import TileGame.gfx.Text;
import TileGame.inventory.Inventory;
import TileGame.items.Item;
import TileGame.shop.Shop;
import TileGame.states.GameState;
import TileGame.states.MenuState;
import TileGame.states.State;
import TileGame.tiles.Tile;
import TileGame.ui.ClickListener;
import TileGame.ui.UIManager;
import TileGame.ui.buttons.UISaveQuitButton;
import TileGame.ui.buttons.UISettingsButton;
import TileGame.ui.buttons.UIStartButton;
import TileGame.utils.FileSerialization;
import TileGame.worlds.World;

public class Player extends Creatures {

	private Animation animUp, animDown, animLeft, animRight, aUp, aDown, aLeft, aRight;
	//Attack Timer
	private long lastAttackTimer, attackCoolDown = (long) attackSpeed, attackTimer = attackCoolDown;
	
	//Inventory
	private Inventory inventory;
	private Shop shop;
	
	private Hud hud;

	private int attackSpeedBuff = 0;
	
	@SuppressWarnings("unused")
	private int id;
	private int cash;
	
	private ProjectileController c;

	private boolean weaponEquipped = false;
	private int armorNum = 0;
	
	private UIManager uiManager;
	
	public Player(Handler handler, float x, float y, int id) {
		super(handler, x, y, Creatures.DEFAULT_CREATURE_WIDTH, Creatures.DEFAULT_CREATURE_HEIGHT, (int)Creatures.DEFAULT_ATTACK, (int)Creatures.DEFAULT_ATTACKSPEED, id, true);
		bounds.x = 12;
		bounds.y = 25;
		bounds.width = 44;
		bounds.height = 35;
		
		uiManager = new UIManager(handler);
		
		this.id = id;
		this.setAttack(1000);
		int attackSpeed = (int) (Player.DEFAULT_ATTACKSPEED + attackSpeedBuff);
		animUp = new Animation(attackSpeed, Assets.player_up);
		animDown = new Animation(attackSpeed, Assets.player_down);
		animLeft = new Animation(attackSpeed, Assets.player_left);
		animRight = new Animation(attackSpeed, Assets.player_right);
		
		aUp = new Animation(attackSpeed, Assets.player_Aup);
		aDown = new Animation(attackSpeed, Assets.player_Adown);
		aLeft = new Animation(attackSpeed, Assets.player_Aleft);
		aRight = new Animation(attackSpeed, Assets.player_Aright);
		
		inventory = new Inventory(handler);
		shop = new Shop(handler, Assets.BeginnerShopScreen);
		c = new ProjectileController(handler);


		hud = new Hud(handler, this);
	
		cash = 0;
		
		// UIManager for settings
		uiManager.addObject(new UISettingsButton((handler.getWidth() / 2) - 64, (handler.getHeight() / 2) - 64, 128, 64, Assets.buttons, new ClickListener(){

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}}));	
		
		uiManager.addObject(new UISaveQuitButton((handler.getWidth() / 2) - 64, (handler.getHeight() / 2) + 32, 128, 64, Assets.buttons, new ClickListener(){

			@Override
			public void onClick() {
				FileSerialization.writeFile(GameState.entitySaveFile, handler.getWorld().getEntityManager().entityData());
				FileSerialization.writeFile(GameState.itemSaveFile, getInventory().inventoryData());
				handler.mainMenu();
			}}));
		
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
		if( handler.getKeyManager().up || handler.getKeyManager().down  ||
			handler.getKeyManager().left || handler.getKeyManager().right){
			aUp.tick();
			aDown.tick();
			aLeft.tick();
			aRight.tick();
		}
		
		c.tick();
		checkAttacks();
		inventory.tick();
		shop.tick();
		uiManager.tick();
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && !getInventory().isActive() && !getShop().isActive()) {
			handler.getMouseManager().setUIManager(uiManager);
			settings = !settings;
			

		}
	}

	private boolean weaponRanged = false;
	public boolean attacked = false;
	public static boolean settings = false;
	
	private void checkAttacks() {
		attacked = false;
		//attack timer
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCoolDown)
			return;
		
		if(inventory.isActive() || shop.isActive())
			return;
	
		//creates rectangle for attacking
			Rectangle cb = getCollisionBounds(0,0);
			Rectangle ar = new Rectangle();
			int arSize = 40;
			ar.width = arSize;
			ar.height = arSize;
			
		if(!weaponRanged){
			if (handler.getKeyManager().up){
				ar.height = (int)(arSize * 1.2);
				ar.x = cb.x + cb.width / 2 + arSize / 2;
				ar.y = cb.y - arSize;
			}else if (handler.getKeyManager().down){
				ar.height = (int)(arSize * 1.2);
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
				
			}else if (handler.getKeyManager().left){
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			
			}else if (handler.getKeyManager().right){
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}else return;
		}
		
		 else if (weaponRanged){
			if(handler.getKeyManager().up){
				c.addArrow(new Arrow(handler, this.getX(), this.getY(), 1, this));
			}else if(handler.getKeyManager().down){
				c.addArrow(new Arrow(handler, this.getX(), this.getY(), 3, this));
			}else if(handler.getKeyManager().left){
				c.addArrow(new Arrow(handler, this.getX(), this.getY(), 0, this));
			}else if(handler.getKeyManager().right){
				c.addArrow(new Arrow(handler, this.getX(), this.getY(), 2, this));
			}else
				return;
		}

		attackTimer = 0;

		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			//if intersecting, attack
			if(e.getCollisionBounds(0, 0).intersects(ar)){
				
				e.hurt((int) attack);
				attacked = true;

				return;
			}
			
		}
		
	}

	@Override
	public void die(){
		System.out.println("You Died");
		System.out.println("Armorments unequipped");
		handler.getWorld().getEntityManager().resetHp();
		getInventory().unEquipAll();
		weaponRanged = false;
		weaponEquipped = false;
		settings = false;
		setArmorNum(0);
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
		
		if(inventory.isActive() || settings)
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
	
	int z = 0;
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	public void postRender(Graphics g){
		c.render(g);
		hud.render(g);
		inventory.render(g);
		shop.render(g);
		if(settings == true) {
			g.setColor(new Color(100, 100, 100, 50));
			g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());
			uiManager.render(g);
		}

		
	}
	
	private BufferedImage lastFrame = Assets.player_down[0];

	private BufferedImage getCurrentAnimationFrame(){
		if(handler.getKeyManager().up){
			return aUp.getCurrentFrame();
		} else if(handler.getKeyManager().down){
			return aDown.getCurrentFrame();
		} else if(handler.getKeyManager().left){
			return aLeft.getCurrentFrame();
		} else if(handler.getKeyManager().right){
			return aRight.getCurrentFrame();
		} else if(xMove < 0){
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

	
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
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

	public boolean isWeaponEquipped() {
		return weaponEquipped;
	}

	public void setWeaponEquipped(boolean weaponEquipped) {
		this.weaponEquipped = weaponEquipped;
	}

	public int getArmorNum() {
		return armorNum;
	}

	public void setArmorNum(int armorNum) {
		this.armorNum = armorNum;
	}

	public boolean isWeaponRanged() {
		return weaponRanged;
	}

	public void setWeaponRanged(boolean weaponRanged) {
		this.weaponRanged = weaponRanged;
	}
	
}
