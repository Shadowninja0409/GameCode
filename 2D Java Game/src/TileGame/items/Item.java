package TileGame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import TileGame.Handler;
import TileGame.gfx.Assets;

public class Item {

	//Handler
	
	public static Item[] items = new Item[256];
	// first int is id, second is attack, third is mSpeed, 4th is health regen
	// first bool is weapon, second is armor, third is food, 4th is equipped
	public static Item nothingItem = new Item(null, "meow", 0, false, false, false, false, false, 0, 0, 0, 0, null);
	public static Item goldItem = new Item(Assets.gold, "Gold", 0, false, false, false, false, false, 0, 0, 0, 400, "Commmon");
	public static Item woodItem = new Item(Assets.wood, "Wood", 1, false, false, false, false, false, 0, 0, 0, 100, "Common");
	public static Item berryItem = new Item(Assets.berries, "Berries", 2, false, false, false, true, false, 0, 0, 10, 50, "Commmon");	
	
	public static Item hPotItem = new Item(Assets.hPot, "health potion", 5, false, false, false, true, false, 0, 0, 20, 100, "Uncommmon");	
	public static Item mPotItem = new Item(Assets.mPot, "mana potion", 6, false, false, false, true, false, 0, 0, 0, 100, "Uncommon");
	public static Item spearItem = new Item(Assets.spear, "Spear", 3, true, false, false, false, false, 5, 0, 0, 200, "UnCommon");
	public static Item bootsItem = new Item(Assets.boots, "Boots", 4, false, true, false, false, false, 0, 1, 0, 150, "Uncommon");

	public static Item bowItem = new Item(Assets.bow, "Bow", 7, true, false, true, false, false, 10, 0, 0, 800, "Rare");

	public static Item headBandItem = new Item(Assets.bow, "HeadBand", 8, false, true, false, false, false, 10, 0, 0, 800, "Legendary");
	
	//Class
	
	public static final int ITEMWIDTH = 50, ITEMHEIGHT = 50;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected Rectangle bounds;
	
	protected int x, y, count, attack, speed, price, hp;
	protected boolean pickedUp, weapon, armor, ranged, consumable, equipped;
	protected String rarity;
	
	public Item(BufferedImage texture, String name, int id, boolean weapon, boolean armor, 
			boolean ranged, boolean food, boolean equipped, int attack, int speed, 
			int hp, int price, String rarity){
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.weapon = weapon;
		this.armor = armor;
		this.ranged = ranged;
		this.consumable = food;
		this.attack = attack;
		this.speed = speed;
		this.hp = hp;
		this.price = price;
		this.rarity = rarity;
		this.equipped = equipped;
		count = 1;
		
		
		
		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
		
		items[id] = this;
	}
	
	public void tick(){
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)){
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
	}
	
	
	public void render(Graphics g){
		if(handler == null){
			return;
		}
		render(g, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()));
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}
	
	public Item createNew(int x, int y){
		Item i = new Item(texture, name, id, weapon, armor, ranged, consumable, equipped, attack, speed, hp, price, rarity);
		i.setPosition(x, y);
		return i;
	}
	
	public Item createNew(int count){
		Item i = new Item(texture, name, id, weapon, armor, ranged, consumable, equipped, attack, speed, hp, price, rarity);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}

	//Getters and Setters
	
	
	public boolean isRanged() {
		return ranged;
	}
	
	public String getRarity() {
		return rarity;
	}

	public boolean isEquipped() {
		return equipped;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}
	
	public boolean isConsumable() {
		return consumable;
	}

	public void setConsumable(boolean consumable) {
		this.consumable = consumable;
	}

	public int getAttack() {
		return attack;
	}
	
	public int getHp() {
		return hp;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public boolean isWeapon() {
		return weapon;
	}
	
	public boolean isArmor(){
		return armor;
	}
	
	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	
	
}
