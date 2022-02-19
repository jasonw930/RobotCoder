package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Used to represent the map that the player will traverse through
public class Map {
	
	public static Map[] maps;

	public String name;
	public int id;
	public int width, height;
	public int tileSize;
	
	public Tile[][] tiles;
	public ArrayList<MapProp> props;
	public MapProp player;
	public MapProp stairs;
	public int remainingKeys;
	
	public static void initialize() {
		maps = new Map[17];
		maps[0] = new Map("map_0", 0, 64);
		maps[1] = new Map("map_1", 1, 64);
		maps[2] = new Map("map_2", 2, 64);
		maps[3] = new Map("map_3", 3, 64);
		maps[4] = new Map("map_4", 4, 64);
		maps[5] = new Map("map_5", 5, 64);
		maps[6] = new Map("map_6", 6, 48);
		maps[7] = new Map("map_7", 7, 64);
		maps[8] = new Map("map_8", 8, 64);
		maps[9] = new Map("map_9", 9, 48);
		maps[10] = new Map("map_10", 10, 48);
		maps[11] = new Map("map_11", 11, 64);
		maps[12] = new Map("map_12", 12, 64);
		maps[13] = new Map("map_13", 13, 64);
		maps[14] = new Map("map_14", 14, 48);
		maps[15] = new Map("map_15", 15, 64);
		maps[16] = new Map("map_16", 16, 40);
	}
	
	public Map(String n, int i, int ts) {
		name = n;
		id = i;
		tileSize = ts;
		reset();
	}
	
//	Description: Resets the map by reading from the map file
//	Parameters: None
//	Return: Void
	public void reset() {
		try {
			System.out.println("Reset map: " + name);
			Scanner file = new Scanner(new File("maps/" + name + ".txt"));
			String[] dim = file.nextLine().split(" ");
			height = Integer.parseInt(dim[0]);
			width = Integer.parseInt(dim[1]);
			remainingKeys = 0;
			
			// Read Tiles
			file.nextLine();
			tiles = new Tile[height][width];
			for (int r = 0; r < height; r++) {
				String[] line = file.nextLine().split(" ");
				for (int c = 0; c < width; c++) {
					tiles[r][c] = Tile.tiles.get(line[c]);
				}
			}
			
			// Read Props
			file.nextLine();
			props = new ArrayList<MapProp>();
			for (int r = 0; r < height; r++) {
				String[] line = file.nextLine().split(" ");
				for (int c = 0; c < width; c++) {
					for (String s : line[c].split("\\|")) {
						if (!s.equals(".")) {
							props.add(new MapProp(Prop.props.get(s), r, c));
							if (props.get(props.size() - 1).prop == Prop.propPlayer)
								player = props.get(props.size() - 1);
							else if (props.get(props.size() - 1).prop == Prop.propExit)
								stairs = props.get(props.size() - 1);
							else if (props.get(props.size() - 1).prop == Prop.propKey)
								remainingKeys++;
						}
					}
				}
			}
			props.remove(player);
			props.add(player);
			
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
