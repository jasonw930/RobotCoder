package map;

// Used to represent a specific prop inside the map
public class MapProp {

	public Prop prop;
	public int row;
	public int col;
	public int rot, dr, dc;
	public boolean visible;
	
	public MapProp(Prop p, int r, int c) {
		prop = p;
		row = r;
		col = c;
		rot = 0;
		dr = 0;
		dc = 1;
		visible = true;
	}
	
}
