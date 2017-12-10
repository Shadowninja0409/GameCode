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
	
	public void unEquipAll(){
		Iterator<Item> it = items.iterator();
		while(it.hasNext())
		it.next().equipped = false;
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
