package TileGame.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.entities.creatures.npc.STShop;
import TileGame.gfx.Assets;
import TileGame.gfx.Text;
import TileGame.items.Item;
import TileGame.ui.UIManager;

public class Shop {
	
	private Handler handler;
	public boolean active = false;
	public static boolean shopOpened = false;
	private BufferedImage background;
	
	
	private UIManager uiManager;
	ArrayList<Item> shopItems;
	
	private int shopX  = 40, shopY = 40, 
			shopWidth = 600, shopHeight = 400,
			shopListCenterX = shopX + 60,
			shopListSpacing = Assets.font28.getSize() - 1,
			shopListCenterY = shopHeight - 248;
	
	private int shopImageX = 456, shopImageY = shopHeight - 144,
				shopImageWidth = 68, shopImageHeight = 68;
	
	private int shopCountX = shopImageX + 34, shopCountY = shopHeight - 78;

	private int selectedItem = 0;
	
	public Shop(Handler handler, BufferedImage background){
		this.background = background;
		this.handler = handler;
		
		shopItems = new ArrayList<Item>();
		uiManager = new UIManager(handler);
	}
	
	public void loadItems(ArrayList<Item> items){
		shopItems = items;
	}
	
	public void tick(){
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_T)){
			active = !active;
		}
		
		if(!active){
			return;	
		}
		
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			selectedItem--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			selectedItem++;
		
		if(selectedItem < 0)
			selectedItem = shopItems.size() - 1;
		else if(selectedItem >= shopItems.size())
			selectedItem = 0;
		
		
	}
	
	//Shop Graphics
	boolean purchase = false;
	public void render(Graphics g){
		// if inactive dont run code
		if(!active || handler.getWorld().getEntityManager().getPlayer().getInventory().isActive() == true){
			return;
		}
		loadShopItems();
		g.drawImage(background, shopX, shopY, shopWidth, shopHeight, null);
		
		uiManager.render(g);
		//if nothing in array, return empty
		int len = shopItems.size();
		
		if(shopItems.size() == 0){
			Text.drawString(g, "Empty", shopListCenterX, shopListCenterY, false, Color.WHITE, Assets.font28);
			return;
		}
		
		//creates scrolling list	
		for(int x = 0; x < len; x++){
//			if(selectedItem + x >= len)
//				continue;
			if(shopItems.get(selectedItem).getCount() <= 0){
				shopItems.remove(selectedItem);
				len = 0;
				return;
			}
			if(x == selectedItem){
				Text.drawString(g, "> " + shopItems.get(x).getName() + " <", 
						shopListCenterX, shopListCenterY + x * shopListSpacing, false, Color.YELLOW, Assets.font28);
				Text.drawString(g, "Price: " + shopItems.get(x).getPrice(), 490, 205, true, Color.BLUE, Assets.font18);
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
					buyItem(shopItems.get(x));
					purchase = true;
					System.out.println(purchase);
				}
				
			}else{
				Text.drawString(g, shopItems.get(x).getName(),
						shopListCenterX, shopListCenterY + shopListSpacing * x, false, Color.WHITE, Assets.font28); 
			}
		}
		
		Item item = shopItems.get(selectedItem);
		g.drawImage(item.getTexture(), shopImageX, shopImageY, shopImageWidth, shopImageHeight, null);
		Text.drawString(g, Integer.toString(item.getCount()), shopCountX, shopCountY, true, Color.RED, Assets.font28);
	}
	boolean ran = false;
	public void loadShopItems(){
		if(ran == false){
			addItem(Item.spearItem.createNew(1));
			addItem(Item.bootsItem.createNew(1));					
			addItem(Item.woodItem.createNew(3));
			addItem(Item.hPotItem.createNew(2));
			addItem(Item.bowItem.createNew(1));
			ran = true;
		}
	}
	
	public void clearshop(){
		for(Item i : shopItems){
			shopItems.get(selectedItem).setCount(0);
			shopItems.remove(i);
			return;
		}
		active = false;
	}

	public void addItem(Item item){
		for(Item i : shopItems){
			if(!i.isWeapon() && !i.isArmor()){
				if(i.getId() == item.getId()){
					i.setCount(i.getCount() + item.getCount());
					return;
				}
			}
		}
		
		shopItems.add(item);
	}
	
	public void buyItem(Item Item){
		for(Item i : shopItems){
			if(i == Item){
				if(handler.getWorld().getEntityManager().getPlayer().getCash() >= i.getPrice()){
					i.setCount(i.getCount() - 1);
					handler.getWorld().getEntityManager().getPlayer().setCash(handler.getWorld().getEntityManager().getPlayer().getCash() - i.getPrice());	
					handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems().add(i.createNew(1));
				}
			}
		}
	}
	
	public boolean getSelectedItem() {
		return shopItems.get(selectedItem).isEquipped();
	}
	
	public boolean isActive() {
		return active;
	}

	public boolean isPurchase(){
		return purchase;
	}	
	
	public void setPurchase(boolean purchase){
		this.purchase = purchase;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}

	
}
