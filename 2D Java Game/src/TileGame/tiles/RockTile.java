package TileGame.tiles;

import TileGame.gfx.Assets;

public class RockTile extends Tile{

	public RockTile(int id) {
		super(Assets.colors[1], id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
