package command;

import java.util.LinkedHashMap;

import map.Map;

// Command that does nothing, used to connect other commands
public class CommandConnect extends Command {

	public CommandConnect(String n, String key, int l) {
		super(n, key, l);
		args = new String[] {"next"};
	}
	
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		if (runStage == runLength-1) {
			nextCommand(grid, "next");
		}
	}
	
}
