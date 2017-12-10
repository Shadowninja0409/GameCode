package TileGame.tiles;

import java.awt.image.BufferedImage;

import TileGame.gfx.Assets;

public class WaterTile extends Tile {

	public WaterTile(int id) {
		super(Assets.colors[7], id);
	}
	
	public boolean isLiquid() {
		return true;
	}

}
