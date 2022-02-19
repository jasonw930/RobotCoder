package map;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

// Used to represent the different types of props that are available
public class Prop {

	public static HashMap<String, Prop> props;
	public static Prop propPlayer;
	public static Prop propExit;
	public static Prop propKey;
	
	public String name;
	public Image texture;
	
	public static void initialize() {
		props = new HashMap<String, Prop>();
		propPlayer = new Prop("player");
		props.put("p", propPlayer);
		propExit = new Prop("exit");
		props.put("e", propExit);
		propKey = new Prop("key");
		props.put("k", propKey);
	}
	
	public Prop(String n) {
		name = n;
		texture = Toolkit.getDefaultToolkit().createImage("textures/props/" + name + ".png");
	}
	
}
