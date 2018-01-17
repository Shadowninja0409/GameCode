package TileGame.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import TileGame.Handler;
import TileGame.gfx.Assets;
import TileGame.gfx.Text;
import TileGame.items.Item;
import TileGame.states.GameState;
import TileGame.ui.ClickListener;
import TileGame.ui.UIManager;
import TileGame.ui.buttons.UISaveButton;
import TileGame.utils.FileSerialization;

public class Inventory {

	private Handler handler;
	private boolean active = false;
	private ArrayList<Item> inventoryItems;

	private UIManager uiManager;

	private int invX  = 40, invY = 40, 
			invWidth = 600, invHeight = 400,
			invListCenterX = invX + 60,
			invListSpacing = Assets.font28.getSize() - 1,
			invListCenterY = invHeight - 140;
	
	private int invImageX = 456, invImageY = invHeight - 144,
				invImageWidth = 68, invImageHeight = 68;
			
	private int invCountX = invImageX + 34, invCountY = invHeight - 78;
	
	private int selectedItem = 0;
	

	public Inventory(Handler handler){
		this.handler = handler;
		inventoryItems = new ArrayList<Item>();
		uiManager = new UIManager(handler);
		
		uiManager.addObject(new UISaveButton(577, (handler.getHeight() / 10) - 8, 64, 32, Assets.buttons, new ClickListener(){
			@Override
			public void onClick() {
				try{
					FileSerialization.writeFile(GameState.entitySaveFile, handler.getWorld().getEntityManager().entityData());
					FileSerialization.writeFile(GameState.itemSaveFile, inventoryData());
				}catch(Exception e){
					e.printStackTrace();
				}	
			}}));
	}

	public boolean isActive() {
		return active;
	}


	public void tick(){
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
			active = !active;
		
		if(active && handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE))
			active = false;
		
		if(!active)
			return;	
		
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			selectedItem--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			selectedItem++;
		
		if(selectedItem < 0)
			selectedItem = inventoryItems.size() - 1;
		else if(selectedItem >= inventoryItems.size())
			selectedItem = 0;
	}
	
	//Inventory Graphics
	
	public void render(Graphics g){
		// if inactive dont run code
		if(!active){
			return;
		}
		g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);
		
		uiManager.render(g);
		//if nothing in array, return empty
		int len = inventoryItems.size();
		
		if(inventoryItems.size() == 0){
			Text.drawString(g, "Empty", invListCenterX, invListCenterY, false, Color.WHITE, Assets.font28);
			return;
		}
		
		
		//creates scrolling list
		for(int i = -4; i < 5; i++){
			if(selectedItem + i < 0 || selectedItem + i >= len)
				continue;
			if(inventoryItems.get(selectedItem).getCount() <= 0){
				inventoryItems.remove(selectedItem);
				len = 0;
				return;
			}
			int yPos = invListCenterY + i * invListSpacing;
			
			if(i == 0){
				Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", 
						invListCenterX, invListCenterY + i * invListSpacing, false, Color.YELLOW, Assets.font28);
				if(inventoryItems.get(selectedItem).isWeapon() || inventoryItems.get(selectedItem).isArmor()){
					if(inventoryItems.get(selectedItem).isEquipped()){
						Text.drawString(g, "Equipped", 490, 205, true, Color.BLUE, Assets.font18);
					}else if(!inventoryItems.get(selectedItem).isEquipped()){
						Text.drawString(g, "Unequipped", 490, 205, true, Color.RED, Assets.font18);
					}
				}
		
//				if(((inventoryItems.get(selectedItem).isWeapon() && !handler.getWorld().getEntityManager().getPlayer().isWeaponEquipped() || inventoryItems.get(selectedItem).isArmor()) || (inventoryItems.get(selectedItem).isArmor() && 
//						handler.getWorld().getEntityManager().getPlayer().getArmorNum() < 4 || inventoryItems.get(selectedItem).isEquipped())) 
//						&& handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER) && 
//						((!handler.getWorld().getEntityManager().getPlayer().isWeaponEquipped() && handler.getWorld().getEntityManager().getPlayer().getArmorNum() < 4) || 
//						inventoryItems.get(selectedItem).isEquipped())){
					if((inventoryItems.get(selectedItem).isWeapon() || (inventoryItems.get(selectedItem).isArmor() && (handler.getWorld().getEntityManager().getPlayer().getArmorNum() < 4 || inventoryItems.get(selectedItem).isEquipped()))) && handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
					
						if(inventoryItems.get(selectedItem).isWeapon() && handler.getWorld().getEntityManager().getPlayer().isWeaponEquipped() && !inventoryItems.get(selectedItem).isEquipped())
							return;
						
					 inventoryItems.get(selectedItem).setEquipped(!inventoryItems.get(selectedItem).isEquipped());
					 if(inventoryItems.get(selectedItem).isEquipped() && inventoryItems.get(selectedItem).isWeapon()){
						 handler.getWorld().getEntityManager().getPlayer().setWeaponEquipped(true);
						 handler.getWorld().getEntityManager().getPlayer().setAttack(handler.getWorld().getEntityManager().getPlayer().getAttack() + inventoryItems.get(selectedItem).getAttack());
						 System.out.println("Weapon is equipped!"); 
						 if(inventoryItems.get(selectedItem).isRanged()){
							 handler.getWorld().getEntityManager().getPlayer().setWeaponRanged(true);
							 System.out.println("Ranged weapon is equipped");
						 }
					 }else if(!inventoryItems.get(selectedItem).isEquipped() && inventoryItems.get(selectedItem).isWeapon()){
						 handler.getWorld().getEntityManager().getPlayer().setWeaponEquipped(false);
						handler.getWorld().getEntityManager().getPlayer().setAttack(handler.getWorld().getEntityManager().getPlayer().getAttack() - inventoryItems.get(selectedItem).getAttack());
						System.out.println("Weapon is unequipped!"); 
						 if(inventoryItems.get(selectedItem).isRanged()){
							 handler.getWorld().getEntityManager().getPlayer().setWeaponRanged(false);
							 System.out.println("Ranged weapon is unequipped");
						 }
					 }
					 if(inventoryItems.get(selectedItem).isEquipped() && inventoryItems.get(selectedItem).isArmor()){
						handler.getWorld().getEntityManager().getPlayer().setArmorNum(handler.getWorld().getEntityManager().getPlayer().getArmorNum() + 1);
						handler.getWorld().getEntityManager().getPlayer().setSpeed(handler.getWorld().getEntityManager().getPlayer().getSpeed() + inventoryItems.get(selectedItem).getSpeed());
						System.out.println("Armor is equipped!"); 
					 }else if(!inventoryItems.get(selectedItem).isEquipped() && inventoryItems.get(selectedItem).isArmor()){
						 handler.getWorld().getEntityManager().getPlayer().setArmorNum(handler.getWorld().getEntityManager().getPlayer().getArmorNum() - 1);
					 	handler.getWorld().getEntityManager().getPlayer().setSpeed(handler.getWorld().getEntityManager().getPlayer().getSpeed() - inventoryItems.get(selectedItem).getSpeed());
					 	System.out.println("Armor is unequipped!"); 
					 }
				}
				if(inventoryItems.get(selectedItem).isConsumable() && handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER) && handler.getWorld().getEntityManager().getPlayer().getHealth() < handler.getWorld().getEntityManager().getPlayer().DEFAULT_HEALTH){
					inventoryItems.get(selectedItem).setCount(inventoryItems.get(selectedItem).getCount() - 1); 
					handler.getWorld().getEntityManager().getPlayer().setHealth(handler.getWorld().getEntityManager().getPlayer().getHealth() + inventoryItems.get(selectedItem).getHp());
					
					System.out.println("Player health total: " + handler.getWorld().getEntityManager().getPlayer().getHealth());
				}
			}
				
			else {
				Text.drawString(g, inventoryItems.get(selectedItem + i).getName(),
						invListCenterX, yPos, false, Color.WHITE, Assets.font28); 
			}
		}
		
		Item item = inventoryItems.get(selectedItem);
		g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
		Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.RED, Assets.font28);
	}
	
	//Inventory methods
	
	public boolean getSelectedItem() {
		return inventoryItems.get(selectedItem).isEquipped();
	}
	
	public void clearInventory(){
		for(Item i : inventoryItems){
			inventoryItems.get(selectedItem).setCount(0);
			inventoryItems.remove(i);
			return;
		}
		active = false;
	}

	public void unEquipAll(){
		for(int i = 0; i < inventoryItems.size(); i++){
			if(inventoryItems.get(i).isEquipped())
			inventoryItems.get(i).setEquipped(false);
		}
	}
	
	public void setInventoryItems(ArrayList<Item> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	public void addItem(Item item){
		for(Item i : inventoryItems){
			if(!i.isWeapon() && !i.isArmor()){
				if(i.getId() == item.getId()){
					i.setCount(i.getCount() + item.getCount());
					return;
				}
			}
		}
		inventoryItems.add(item);
	}
	
	
	
	
	public String inventoryData(){
		String inventoryData = "";
		int itemCount = inventoryItems.size();
		
		inventoryData += itemCount + "\n";
		for(Item i : inventoryItems){
			inventoryData += i.getId() + " " + i.getCount() + " \n";
		}
		
		return inventoryData;
	}
	//Getters and Setters

	public Handler getHandler() {
		return handler;
	}
	
	public ArrayList<Item> getInventoryItems() {
		return inventoryItems;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	
}
