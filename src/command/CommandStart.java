package command;

import java.util.LinkedHashMap;

import map.Map;

// Command that the grid searches for to start program execution
public class CommandStart extends Command {

	public CommandStart(String n, String key, int l) {
		super(n, key, l);
		args = new String[] {"next"};
	}
	
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		if (runStage == runLength-1) {
			nextCommand(grid, "next");
		}
	}
	
}
