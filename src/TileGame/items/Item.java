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
	public static Item goldItem = new Item(Assets.gold, "Gold", 0, false, false, false, false, 0, 0, 0);
	public static Item woodItem = new Item(Assets.wood, "Wood", 1, false, false, false, false, 0, 0, 0);
	public static Item berryItem = new Item(Assets.berries, "Berries", 2, false, false, true, false, 0, 0, 1);
	
	public static Item spearItem = new Item(Assets.spear, "Spear", 3, true, false, false, false, 3, 0, 0);
	public static Item bootsItem = new Item(Assets.boots, "Boots", 4, false, true, false, false, 0, 3, 0);

	//Class
	
	public static final int ITEMWIDTH = 50, ITEMHEIGHT = 50;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected Rectangle bounds;
	
	protected int x, y, count, attack, speed, hp;
	protected boolean pickedUp, weapon, armor, food, equipped;

	public Item(BufferedImage texture, String name, int id, boolean weapon, boolean armor, boolean food, boolean equipped, int attack, int speed, int hp){
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.weapon = weapon;
		this.armor = armor;
		this.food = food;
		this.attack = attack;
		this.speed = speed;
		this.hp = hp;
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
		Item i = new Item(texture, name, id, weapon, armor, food, equipped, attack, speed, hp);
		i.setPosition(x, y);
		return i;
	}
	
	public Item createNew(int count){
		Item i = new Item(texture, name, id, weapon, armor, food, equipped, attack, speed, hp);
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

	public static Item[] getItems(){
		return items;
	}
	//Getters and Setters
	
	public boolean isEquipped() {
		return equipped;
	}

	public void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}
	
	public boolean isFood() {
		return food;
	}

	public void setFood(boolean food) {
		this.food = food;
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
