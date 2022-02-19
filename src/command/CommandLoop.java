package command;

import java.util.LinkedHashMap;

import map.Map;

// Command used to create loops
public class CommandLoop extends Command {

	public CommandLoop(String n, String key, int l) {
		super(n, key, l);
		args = new String[] {"next", "loop"};
		vars = new String[] {"repeats"};
	}
	
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		if (runStage == runLength-1) {
			if (vars.get("repeats") > 0) {
				// Decrements and loops
				vars.put("repeats", vars.get("repeats") - 1);
				nextCommand(grid, "loop");
			} else {
				// Exits loop
				nextCommand(grid, "next");
			}
		}
	}
	
}
