package TileGame.worlds;

import java.awt.Graphics;
import java.util.ArrayList;

import TileGame.Handler;
import TileGame.entities.Entity;
import TileGame.entities.EntityManager;
import TileGame.entities.creatures.Player;
import TileGame.entities.creatures.enemies.CatEnemy;
import TileGame.entities.creatures.enemies.CatEnemy2;
import TileGame.entities.creatures.enemies.Enemy;
import TileGame.entities.creatures.npc.NPC;
import TileGame.entities.creatures.npc.Tutorial;
import TileGame.entities.statics.Blockcade;
import TileGame.entities.statics.BrickWall;
import TileGame.entities.statics.Chest;
import TileGame.entities.statics.Fern;
import TileGame.entities.statics.StaticEntity;
import TileGame.entities.statics.Tree;
import TileGame.items.Item;
import TileGame.items.ItemManager;
import TileGame.states.MenuState;
import TileGame.tiles.Tile;
import TileGame.utils.Utils;

public class World {

	private Handler handler;
	private int width, height;
	public int spawnX, spawnY;
	private int[][] tiles;
	//Entity Manager
	private EntityManager entityManager;
	
	private ItemManager itemManager;
	
	public World(Handler handler, String world, String data, String inventory){
		this.handler = handler;
		//Managers
		entityManager = new EntityManager(handler, new Player(handler, 400, 400, 80189012));
		itemManager = new ItemManager(handler);
		
		entityManager.addEntity(new Tutorial(handler, 6 * Tile.TILEWIDTH, 9 * Tile.TILEHEIGHT, width, height, 0, 0, 2));

		//trees
		entityManager.addEntity(new Fern(handler, 15 * Tile.TILEWIDTH, 6 * Tile.TILEHEIGHT, 3));
		entityManager.addEntity(new Tree(handler, 2 * Tile.TILEWIDTH, 8 * Tile.TILEHEIGHT, 4));
		entityManager.addEntity(new Tree(handler, 3 * Tile.TILEWIDTH, 8 * Tile.TILEHEIGHT, 5));
		entityManager.addEntity(new Tree(handler, 4 * Tile.TILEWIDTH, 8 * Tile.TILEHEIGHT, 6));
		//bricks
		entityManager.addEntity(new BrickWall(handler, 9 * Tile.TILEWIDTH, 5 * Tile.TILEHEIGHT, 7));
		entityManager.addEntity(new BrickWall(handler, 9 * Tile.TILEWIDTH, 6 * Tile.TILEHEIGHT, 8));
		entityManager.addEntity(new BrickWall(handler, 9 * Tile.TILEWIDTH, 7 * Tile.TILEHEIGHT, 9));
		entityManager.addEntity(new BrickWall(handler, 6 * Tile.TILEWIDTH, 3 * Tile.TILEHEIGHT, 10));
		
		//chests
		entityManager.addEntity(new Chest(handler, 6 * Tile.TILEWIDTH, 1 * Tile.TILEHEIGHT, 11));
		
		//enemies
		entityManager.addEntity(new CatEnemy(handler, 11 * Tile.TILEWIDTH, 5 * Tile.TILEHEIGHT, 12));
		entityManager.addEntity(new CatEnemy2(handler, 13 * Tile.TILEWIDTH, 13 * Tile.TILEHEIGHT, 13));		
		//blockades

		entityManager.addEntity(new Blockcade(handler, 9 * Tile.TILEWIDTH, 11 * Tile.TILEHEIGHT, 14));
		entityManager.addEntity(new Blockcade(handler, 9 * Tile.TILEWIDTH, 12 * Tile.TILEHEIGHT, 14));
		entityManager.addEntity(new Blockcade(handler, 9 * Tile.TILEWIDTH, 13 * Tile.TILEHEIGHT, 14));
		entityManager.addEntity(new Blockcade(handler, 9 * Tile.TILEWIDTH, 14 * Tile.TILEHEIGHT, 14));
		
		entityManager.addEntity(new Blockcade(handler, 10 * Tile.TILEWIDTH, 18 * Tile.TILEHEIGHT, 14));
		entityManager.addEntity(new Blockcade(handler, 11 * Tile.TILEWIDTH, 18 * Tile.TILEHEIGHT, 14));
		entityManager.addEntity(new Blockcade(handler, 12 * Tile.TILEWIDTH, 18 * Tile.TILEHEIGHT, 14));

		
		//world path
		loadWorld(world, data, inventory);
		//spawn location
		entityManager.getPlayer().setX(spawnX * Tile.TILEWIDTH);
		entityManager.getPlayer().setY(spawnY * Tile.TILEHEIGHT);

	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void tick(){
		itemManager.tick();
		entityManager.tick();
	}
	
	public void render(Graphics g){
		
		int xStart =(int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset()+ handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset()+ handler.getHeight()) / Tile.TILEHEIGHT + 1);
		
		for (int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				getTile(x,y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), 
					(int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		itemManager.render(g);
		entityManager.render(g);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		
		if (t == null)
			return Tile.dirtTile;
		return t;
	}
	
	
	public void loadWorld(String path, String data, String inventory){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
		
		if(MenuState.load == true){
			handler.getWorld().getEntityManager().getPlayer().getHud().setActive(false);
			String file2 = Utils.loadFileAsString(data);
			String[] token = file2.split("\\s+");	
			

			int id = Utils.parseInt(token[0]);
			for(int x = 0; x < (id * 3) + 3; x++){
				if(Utils.parseInt(token[x]) == 80189012){
					handler.getWorld().getEntityManager().getPlayer().setHealth(Utils.parseInt(token[x + 1]));
					handler.getWorld().getEntityManager().getPlayer().setX(Utils.parseInt(token[x + 2]) * Tile.TILEWIDTH);
					handler.getWorld().getEntityManager().getPlayer().setY(Utils.parseInt(token[x + 3]) * Tile.TILEHEIGHT);
					handler.getWorld().getEntityManager().getPlayer().setCash(Utils.parseInt(token[x + 4]));
					handler.getWorld().getEntityManager().getPlayer().setAttack((int) Player.DEFAULT_ATTACK);
					handler.getWorld().getEntityManager().getPlayer().setAttackSpeed((float)(Utils.parseInt(token[x + 6])));
					handler.getWorld().getEntityManager().getPlayer().setSpeed((float)(Player.DEFAULT_SPEED));
					handler.getWorld().getEntityManager().getPlayer().setActive(true);
					x += 7;
				} else {
					for(Entity e : handler.getWorld().getEntityManager().getEntities()){
						if(e.getId() == Utils.parseInt(token[x]) && e.getId() != handler.getWorld().getEntityManager().getPlayer().getId()){
							if(Utils.parseInt(token[x + 1]) == 0){
								e.active = false;
								x += 2;
							} else{
								e.setHealth(Utils.parseInt(token[x + 1]));
								if(Utils.parseInt(token[x + 2]) == 1)
									e.setActive(true);
								x += 2;
							}
						}
					}
				}
			}
			String file3 = Utils.loadFileAsString(inventory);
			String[] tokens3 = file3.split("\\s+");
			int itemId = Utils.parseInt(tokens3[0]);
			
			for(int x = 1; x < (itemId * 2) + 1; x++){
				if(Utils.parseInt(tokens3[x]) == 0){
					handler.getWorld().getEntityManager().getPlayer().getInventory().
					addItem(Item.goldItem.createNew(Utils.parseInt(tokens3[x + 1])));
				}else if((Utils.parseInt(tokens3[x]) == 1)){
					handler.getWorld().getEntityManager().getPlayer().getInventory().
					addItem(Item.woodItem.createNew(Utils.parseInt(tokens3[x + 1])));
				}else if((Utils.parseInt(tokens3[x]) == 2)){
					handler.getWorld().getEntityManager().getPlayer().getInventory().
					addItem(Item.berryItem.createNew(Utils.parseInt(tokens3[x + 1])));
				}else if((Utils.parseInt(tokens3[x]) == 3)){
					handler.getWorld().getEntityManager().getPlayer().getInventory().
					addItem(Item.spearItem.createNew(Utils.parseInt(tokens3[x + 1])));
				}else if((Utils.parseInt(tokens3[x]) == 4)){
					handler.getWorld().getEntityManager().getPlayer().getInventory().
					addItem(Item.bootsItem.createNew(Utils.parseInt(tokens3[x + 1])));
				}
				x+= 1;
//				for(Item i : handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems()){
//					if(Utils.parseInt(tokens3[x + 2]) == 0){
//						i.setEquipped(false);
//					} else if(Utils.parseInt(tokens3[x + 2]) == 1)
//						i.setEquipped(true);	
//					x += 2;
//				}
			}
		}
	}
	
	public void createItemList(int amount, int count){
		if((amount == 0)){
			handler.getWorld().getEntityManager().getPlayer().getInventory().
			addItem(Item.goldItem.createNew(count));
		}else if((amount == 1)){
			handler.getWorld().getEntityManager().getPlayer().getInventory().
			addItem(Item.woodItem.createNew(count));
		}else if((amount == 2)){
			handler.getWorld().getEntityManager().getPlayer().getInventory().
			addItem(Item.berryItem.createNew(count));
		}else if((amount == 3)){
			handler.getWorld().getEntityManager().getPlayer().getInventory().
			addItem(Item.spearItem.createNew(count));
		}else if((amount == 4)){
			handler.getWorld().getEntityManager().getPlayer().getInventory().
			addItem(Item.bootsItem.createNew(count));
		}
	}
	public int getSpawnX() {
		return spawnX;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	
}
