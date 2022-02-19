package command;

import java.util.LinkedHashMap;

import main.Game;
import map.Map;

// Command used to descend stairs and complete levels
public class CommandStairs extends Command {

	public CommandStairs(String n, String key, int l) {
		super(n, key, l);
	}
	
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		if (runStage == runLength-1) {
			// Only completes level if stairs are present and all keys are collected
			if (map.player.row == map.stairs.row && map.player.col == map.stairs.col && map.remainingKeys == 0) {
				Game.levelManager.completed[map.id] = true;
				if (map.id < Map.maps.length - 1) Game.levelManager.unlocked[map.id+1] = true;
				Game.guiLevelOver.visible = true;
			}
			terminate(grid);
		}
	}
	
}
