package TileGame.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import TileGame.Handler;
import TileGame.entities.creatures.Player;
import TileGame.entities.creatures.enemies.CatEnemy;
import TileGame.entities.creatures.npc.NPC;
import TileGame.entities.creatures.npc.Tutorial;
import TileGame.entities.statics.Tree;
import TileGame.states.GameState;
import TileGame.tiles.Tile;

public class EntityManager {

	private Handler handler;
	private Player player;

	private ArrayList<Entity> entities;
	private ArrayList<Entity> deadEntities;

	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			
			return 1;
		}
	};
	
	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;

		entities = new ArrayList<Entity>();
		deadEntities = new ArrayList<Entity>();
		addEntity(player);

	}

	public void tick(){
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()){
			Entity e = it.next();
			e.tick();
			if(!e.isActive()){
				e.setDead(true);
				it.remove();
			}
			
		}
		entities.sort(renderSorter);
	}
	
	public void render(Graphics g){
		for (Entity e : entities){
			e.render(g);
		}
		player.postRender(g);
	}

	public void addEntity(Entity e){
		entities.add(e);
		deadEntities.add(e);
	}
	
	public void resetHp(){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(handler.getWorld().getEntityManager().getPlayer())){
				e.setHealth(Player.DEFAULT_HEALTH);
			}
		}
	}
	
	public String deadEntities(ArrayList<Entity> dead){
		String List = "";
		for(Entity e : dead){
			if(e.isDead() == true){
				List += e.getId() + " " + 0 + " " + 0 + "\n";
			}
			
		}
		return List;
	}
	
	public String entityData(){
		String entityData = "";
		int idCount = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getDeadEntities()){
			idCount++;
		}
		idCount += 1;
		entityData += idCount + "\n";
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			entityData +=  e.getId() + " " + e.getHealth() + " ";	
			if(e.equals(getPlayer())){
			entityData += Math.round((e.getX() / Tile.TILEWIDTH)) + " " + Math.round((e.getY() / Tile.TILEHEIGHT)) + " " + 
			handler.getWorld().getEntityManager().getPlayer().getCash() + " " +
			handler.getWorld().getEntityManager().getPlayer().getAttack() + " " +
			(int)(handler.getWorld().getEntityManager().getPlayer().getAttackSpeed()) + " " + 
			(int)(handler.getWorld().getEntityManager().getPlayer().getSpeed()) + " ";
			}
			if(e.active)
			entityData += 1;
			entityData += "\n";
		}
		entityData += deadEntities(deadEntities);
		entityData += GameState.level;

		return entityData;
	}
	
	// Getters and Setters
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public ArrayList<Entity> getDeadEntities() {
		return deadEntities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
	
}