package TileGame.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	//tile size
	public static int width = 68, height = 68;
	
	//images
	public static BufferedImage[] cat, buttons, colors, player_up, player_down, player_left, player_right;
	//weapons
	public static BufferedImage chest, spear, boots;
	//itemss
	public static BufferedImage wood, gold, berries;
	public static BufferedImage inventoryScreen;
	
	//fonts
	public static Font font28, font18;
	//black, gray, red, lightred, orange, yellow, green, lightblue, 
	//darkblue, purple, lightgray, pink, orangeyellow, tan, bluish, grayblue, pinkishgray, brown
	
	public static void init(){
		font28 = FontLoader.LoadFont("res/fonts/slkscr.ttf", 28);
		font18 = FontLoader.LoadFont("res/fonts/slkscr.ttf", 18);

		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/Colors.png"));
		SpriteSheet sheet2 = new SpriteSheet(ImageLoader.LoadImage("/textures/PlayerRPG.png"));
		SpriteSheet sheet3 = new SpriteSheet(ImageLoader.LoadImage("/textures/UI elements.png"));
		SpriteSheet sheet4 = new SpriteSheet(ImageLoader.LoadImage("/textures/Weapons.png"));
		SpriteSheet sheet5 = new SpriteSheet(ImageLoader.LoadImage("/textures/enemys.png"));

		
		inventoryScreen = ImageLoader.LoadImage("/textures/Inventory.png");
		
		//Spritesheet 1
		colors = new BufferedImage[23];
		
		colors[0] = sheet.crop(0, 0, width, height);//black
		colors[1] = sheet.crop(width, 0, width, height);//gray
		colors[2] = sheet.crop(width * 2, 0, width, height);//red
		colors[3] = sheet.crop(width * 3, 0, width, height);//lightred
		colors[4] = sheet.crop(width * 4, 0, width, height);//orange
		colors[5] = sheet.crop(width * 5, 0, width, height);//blockade
		colors[6] = sheet.crop(width * 6, 0, width, height);//green
		colors[7] = sheet.crop(width * 7, 0, width, height);//lightblue
		colors[8] = sheet.crop(0, height, width, height);//darkblue
		colors[9] = sheet.crop(width, height, width, height);//purple
		colors[10] = sheet.crop(width * 2, height, width, height);//lightgray
		colors[11] = sheet.crop(width * 3, height, width, height);//pink
		colors[12] = sheet.crop(width * 4, height, width, height);//orangeyellow
		colors[13] = sheet.crop(width * 5, height, width, height);//tan
		colors[14] = sheet.crop(width * 6, height, width, height);//bluish
		colors[15] = sheet.crop(width * 7, height, width, height);//grayblue
		colors[16] = sheet.crop(0, height * 2, width, height);//pinkishgray
		colors[17] = sheet.crop(width, height * 2, width, height);//brown
		colors[18] = sheet.crop(0, height * 3, width, height * 2);//tree
		colors[19] = sheet.crop(width * 2, height * 2, width, height);//brick
		colors[20] = sheet.crop(width, height * 3, width, height * 2);//fern
		colors[21] = sheet.crop(width * 2, height * 3, width, height);//moneybag
		colors[22] = sheet.crop(width * 3, height * 3, width, height);//WoodenTile


		wood = sheet.crop(width * 3, height * 2, width, height);
		gold = sheet.crop(width * 4, height * 2, width, height);
		berries = sheet.crop(width * 5, height * 2, width, height);
		chest = sheet.crop(width * 6, height * 2, width, height);
		boots = sheet.crop(width * 7, height * 2, width, height);

		cat = new BufferedImage[1];
		cat[0] = sheet5.crop(0, 0, width, height);
		
		//Spritesheet 2
		player_up = new BufferedImage[4];
		player_down = new BufferedImage[4];
		player_left = new BufferedImage[4];
		player_right = new BufferedImage[4];

		player_down[0] = sheet2.crop(0, 0, width, height);
		player_down[1] = sheet2.crop(width, 0, width, height);
		player_down[2] = sheet2.crop(width * 2, 0, width, height);
		player_down[3] = sheet2.crop(width * 3, 0, width, height);
		
		player_left[0] = sheet2.crop(0, height, width, height);
		player_left[1] = sheet2.crop(width, height, width, height);
		player_left[2] = sheet2.crop(width * 2, height, width, height);
		player_left[3] = sheet2.crop(width * 3, height, width, height);
		
		player_right[0] = sheet2.crop(0, height * 2, width, height);
		player_right[1] = sheet2.crop(width, height * 2, width, height);
		player_right[2] = sheet2.crop(width * 2, height * 2, width, height);
		player_right[3] = sheet2.crop(width * 3, height * 2, width, height);
		
		player_up[0] = sheet2.crop(0, height * 3, width, height);
		player_up[1] = sheet2.crop(width, height * 3, width, height);
		player_up[2] = sheet2.crop(width * 2, height * 3, width, height);
		player_up[3] = sheet2.crop(width * 3, height * 3, width, height);
		
		//Spritesheet 3
		buttons = new BufferedImage[8];
		
		buttons[0] = sheet3.crop(0, 0, width, height / 2);
		buttons[1] = sheet3.crop(0, height / 2, width, height / 2);
		buttons[2] = sheet3.crop(width, 0, width, height / 2);
		buttons[3] = sheet3.crop(width, height / 2, width, height / 2);
		buttons[4] = sheet3.crop(width * 2, 0, width, height / 2);
		buttons[5] = sheet3.crop(width * 2, height / 2, width, height / 2);
		buttons[6] = sheet3.crop(width * 3, 0, width, height / 2);
		buttons[7] = sheet3.crop(width * 3, height / 2, width, height / 2);
		
		spear = ImageLoader.LoadImage("/textures/Weapons.png");
	}
	
}
