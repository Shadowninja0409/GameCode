package TileGame.entities.creatures.npc;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import TileGame.Handler;
import TileGame.dialogue.Dialogue;
import TileGame.gfx.Assets;
import TileGame.tiles.Tile;

public class Tutorial extends NPC{
	
	private Dialogue dialogue;
	
	private int id;
	public Tutorial(Handler handler, float x, float y, int width, int height, int defaultAttack, int defaultAttackspeed, int id) {
		super(handler, x, y, width, height, defaultAttack, defaultAttackspeed, id);
		
		bounds.x = 12;
		bounds.y = 12;
		bounds.width = 44;
		bounds.height = 44;
		
		this.id = id;
		
	}

	@Override
	public void tick() {
		areaDetection();
		if(count >= 4){
			movePath();
		}
	}

	@Override
	public void render(Graphics g) {
		dialogue = new Dialogue(handler, text());

		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_T) && this.intersects == true)
			count++;
		if(count > 0 && count <= 6 && intersects == true){
			dialogue.render(g);
		}
		g.drawImage(Assets.cat[0], (int)(x  - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
		
	}
	
	public void textRender(Graphics g){
	
	}

	@Override
	public void die() {
		
	}
	public int count = 0;
	
	public String text(){
		String text = "";
		
		if(count == 1){
			text = "Hi welcome to neko land!";
		} else if(count == 2){
			text = "you can travel the world, collect items, fight cats, \nand other creatures along the way.";
		} else if(count == 3){
			text = "objects can be broken by walking up to them and \npressing the arrow keys when within range.";
		} else if(count == 4){
			text = "here follow me please...";
		} else if(count == 5){
			text = "hold the arrow key down in the direction \n of one of the walls to destroy it";
		} else if(count == 6){
			text = "Good Luck! watch out for the bad kitties!";
		}
		
		return text;
	}

	private boolean stopped = false;
	@Override
	public void movePath() {
		float z = Tile.TILEWIDTH * 8;
		float z2 = Tile.TILEHEIGHT * 6;

		if(x <= z){
			x+=2;	
			count = 4;
		}else if(x >= z && y > z2){
			y-=2; 
			count = 4;
		} if(x >= z && y <= z2){
			stopped = true;
		}
		if (stopped == true && count < 5){
			count++;
		}
			
	}

}
