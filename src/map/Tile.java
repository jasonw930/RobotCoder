package map;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

// Used to represent the different types of tiles that are available
public class Tile {

	public static HashMap<String, Tile> tiles;
	public static Tile tileEmpty;
	public static Tile tileFloor;
	public static Tile tileWall;
	
	public String name;
	public boolean solid;
	public Image texture;
	
	public static void initialize() {
		tiles = new HashMap<String, Tile>();
		tileEmpty = new Tile("empty", true);
		tiles.put(".", tileEmpty);
		tileFloor = new Tile("floor", false);
		tiles.put("f", tileFloor);
		tileWall = new Tile("wall", true);
		tiles.put("w", tileWall);
	}
	
	public Tile(String n, boolean s) {
		name = n;
		solid = s;
		texture = Toolkit.getDefaultToolkit().createImage("textures/tiles/" + name + ".png");
	}
	
}
