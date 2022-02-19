package command;

import java.util.LinkedHashMap;

import main.Game;

// Used to represent a specific command inside the grid
public class GridCommand {

	public Command command;
	public int row;
	public int col;
	public int runStage;
	
	public LinkedHashMap<String, Integer> args;
	public LinkedHashMap<String, Integer> iniVars;
	public LinkedHashMap<String, Integer> vars;
	
	public GridCommand(Command cmd, int r, int c) {
		command = cmd;
		row = r;
		col = c;
		runStage = 0;
		args = new LinkedHashMap<String, Integer>();
		for (String s : cmd.args) {
			args.put(s, -1);
		}
		iniVars = new LinkedHashMap<String, Integer>();
		for (String s : cmd.vars) {
			iniVars.put(s, 0);
		}
		vars = new LinkedHashMap<String, Integer>();
		for (String s : cmd.vars) {
			vars.put(s, 0);
		}
	}
	
//	Description: Executes the command by calling the command's execute method and passing appropriate arguments
//	Parameters: None
//	Return: Void
	public void execute() {
		command.execute(Game.levelManager.currentMap, Game.levelManager.currentGrid, row, col, runStage, args, vars);
		runStage++;
		if (runStage >= command.runLength) {
			runStage = 0;
		}
	}
	
}
