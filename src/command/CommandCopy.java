package command;

import java.util.LinkedHashMap;

import map.Map;

// Command that copies first number in one command to first number in another
public class CommandCopy extends Command {

	public CommandCopy(String n, String key, int l) {
		super(n, key, l);
		args = new String[] {"next", "source", "destination"};
	}
	
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		if (runStage == runLength-1) {
			GridCommand src = grid.getAdjGrid(row, col, args.get("source"));
			GridCommand dest = grid.getAdjGrid(row, col, args.get("destination"));
			
			// Copy number
			if (src != null && dest != null && src.command.vars.length > 0 && dest.command.vars.length > 0) {
				dest.vars.put(dest.command.vars[0], src.vars.get(src.command.vars[0]));
			}
			
			nextCommand(grid, "next");
		}
	}
	
}
