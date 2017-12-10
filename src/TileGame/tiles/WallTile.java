package TileGame.tiles;

import TileGame.gfx.Assets;

public class WallTile extends Tile{

	public WallTile(int id) {
		super(Assets.colors[0], id);
	}
	
	@Override
	public boolean isSolid(){
		return true;
	}

}
