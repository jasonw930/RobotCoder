package command;

import java.util.LinkedHashMap;

import map.Map;
import map.MapProp;

// Command used to move forward
public class CommandMove extends Command {

	public CommandMove(String n, String key, int l) {
		super(n, key, l);
		args = new String[] {"next"};
	}
	
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		if (runStage == runLength-1) {
			MapProp player = map.player;
			if (!map.tiles[player.row+player.dr][player.col+player.dc].solid) {
				player.row += player.dr;
				player.col += player.dc;
			}
			nextCommand(grid, "next");
		}
	}
	
}
