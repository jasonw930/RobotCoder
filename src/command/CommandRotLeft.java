package command;

import java.util.LinkedHashMap;

import map.Map;
import map.MapProp;

// Command used to rotate counter clockwise
public class CommandRotLeft extends Command {

	public CommandRotLeft(String n, String key, int l) {
		super(n, key, l);
		args = new String[] {"next"};
	}
	
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		if (runStage == runLength-1) {
			MapProp player = map.player;
			player.rot = (player.rot + 3) % 4;
			int temp = player.dr;
			player.dr = -player.dc;
			player.dc = temp;
			nextCommand(grid, "next");
		}
	}
	
}
