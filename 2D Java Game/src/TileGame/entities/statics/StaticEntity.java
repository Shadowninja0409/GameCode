package TileGame.entities.statics;

import TileGame.Handler;
import TileGame.entities.Entity;

public abstract class StaticEntity extends Entity {
	
	public abstract boolean isBreakable();
	
	public abstract boolean isTriggered();

	public StaticEntity(Handler handler, float x, float y, int width, int height, int id, boolean breakable) {
		super(handler, x, y, width, height, id, breakable);
	}

	
}
