package TileGame.shop;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import TileGame.Handler;
import TileGame.gfx.Assets;
import TileGame.items.Item;

public class Shop {
	
	private BufferedImage background;
	
	ArrayList<Item> items;
	
	private int shopX  = 40, shopY = 40, 
			shopWidth = 600, shopHeight = 400,
			shopListCenterX = shopX + 60,
			shopListSpacing = Assets.font28.getSize() - 1,
			shopListCenterY = shopHeight - 193;
	
	private int shopImageX = 456, shopImageY = shopHeight - 144,
				shopImageWidth = 68, shopImageHeight = 68;
	
	public Shop(Handler handler, BufferedImage background){
		this.background = background;
	}
	
}
