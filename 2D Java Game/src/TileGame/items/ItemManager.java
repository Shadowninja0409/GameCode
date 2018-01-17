package TileGame.items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import TileGame.Handler;
import TileGame.entities.Entity;

public class ItemManager {

	private Handler handler;
	private ArrayList<Item> items;
	
	public ItemManager(Handler handler){
		this.handler = handler;
		items = new ArrayList<Item>();
	}
	
	public void tick(){
		Iterator<Item> it = items.iterator();
		while(it.hasNext()){
		Item i = it.next();
		i.tick();
		if(i.isPickedUp())
			it.remove();
		}
	}
	
	public void render(Graphics g){
		for (Item i : items)
			i.render(g);
	}
	
	public void addItem(Item i){
		i.setHandler(handler);
		items.add(i);
	}
	
	public String itemData(){
		String itemData = "";
		int itemIdCount = 0;
		for (Item i : items){
			itemIdCount++;
		}
		for (Item i : items){
		}
		return itemData;	
	}
	
	Item i;
	
	public Item randomized(int x,  int y){
		int roll = (int) (Math.random() * 100);
		String rarity = "";
		int roll2 = 0;

		if(roll >= 70 || roll <= 30){
			rarity = "Common";
			roll2 = (int)(Math.random() * 3);
			if(roll2 == 0) {
				i = Item.goldItem.createNew(x, y);
			}else if(roll2 == 1) {
				i = Item.woodItem.createNew(x, y);
			}else if(roll2 == 2) {
				i = Item.berryItem.createNew(x, y);
			}
		} else if(roll >= 50 || roll <= 40){
			rarity = "Uncommon";
			roll2 = (int)(Math.random() * 4);
			if(roll2 == 0) {
				i = Item.mPotItem.createNew(x, y);
			}else if(roll2 == 1) {
				i = Item.hPotItem.createNew(x, y);
			}else if(roll2 == 2) {
				i = Item.spearItem.createNew(x, y);
			}else if(roll2 == 3) {
				i = Item.bootsItem.createNew(x, y);
			}
		} else if(roll >= 42 || roll <= 40){
			rarity = "Rare";
			roll2 = (int)(Math.random() * 1);
			if(roll2 == 0) {
				i = Item.bowItem.createNew(x, y);
			}
		} else if(roll == 41) {
			rarity = "Legendary";
			roll2 = (int)(Math.random() * 1);
			if(roll2 == 0) {
				i = Item.headBandItem.createNew(x, y);
			}
		}
		
		System.out.println(roll + " " + roll2 + " " + rarity);
		return i;
	}
	

	public ArrayList<Item> getItems() {
		return items;
	}
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
