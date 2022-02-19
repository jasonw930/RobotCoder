package command;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedHashMap;

import map.Map;

// Used to represent the different types of commands
public class Command {

	public static HashMap<String, Command> commands;
	public static Command commandEmpty;
	public static Command commandBlank;
	
	public static CommandStart commandStart;
	public static CommandMove commandMove;
	public static CommandRotRight commandRotRight;
	public static CommandRotLeft commandRotLeft;
	public static CommandLoop commandLoop;
	public static CommandCopy commandCopy;
	public static CommandStairs commandStairs;
	public static CommandConnect commandConnect;
	public static CommandKeys commandKeys;
	
	public String name;
	public String key;
	public Image texture;
	public int runLength;
	public String[] args;
	public String[] vars;
	
	public static void initialize() {
		commands = new LinkedHashMap<String, Command>();
		commandEmpty = new Command("empty", ".", 0);
		commandBlank = new Command("blank", "b", 0);
		
		commandStart = new CommandStart("start", "s", 20);
		commandMove = new CommandMove("move", "m", 20);
		commandRotRight = new CommandRotRight("rot_right", "rr", 20);
		commandRotLeft = new CommandRotLeft("rot_left", "rl", 20);
		commandLoop = new CommandLoop("loop", "l", 5);
		commandCopy = new CommandCopy("copy", "c", 20);
		commandStairs = new CommandStairs("stairs", "st", 20);
		commandConnect = new CommandConnect("connect", "ct", 5);
		commandKeys = new CommandKeys("keys", "k", 20);
	}
	
	public Command(String n, String k, int l) {
		name = n;
		texture = Toolkit.getDefaultToolkit().createImage("textures/commands/" + name + ".png");
		runLength = l;
		args = new String[0];
		vars = new String[0];
		key = k;
		commands.put(key, this);
	}
	
//	Description: Runs the command based off of given information
//	Parameters: The map and grid being executed in, the row and column of the command in the grid, how many frames the command has been running for, the directions and numbers of the command
//	Return: Void
	public void execute(Map map, Grid grid, int row, int col, int runStage, LinkedHashMap<String, Integer> args, LinkedHashMap<String, Integer> vars) {
		
	}
	
//	Description: Moves the current command in the grid based its current command and the name of the direction given
//	Parameters: The grid to have its current command change and then name of the direction used to move to next command
//	Return: Void
	public void nextCommand(Grid grid, String arg) {
		// Terminates if direction is not set
		if (grid.commands[grid.curRow][grid.curCol].args.get(arg) == -1) {
			terminate(grid);
			return;
		}
		// Translate from angle to change in row and change in column
		int dr = 0, dc = 1;
		for (int i = 0; i < grid.commands[grid.curRow][grid.curCol].args.get(arg); i++) {
			int temp = dr;
			dr = dc;
			dc = -temp;
		}
		grid.curRow += dr;
		grid.curCol += dc;
		// Terminates if direction points to invalid positions
		if (grid.curRow < 0 || grid.curRow >= grid.height || grid.curCol < 0 || grid.curCol >= grid.width ||
				grid.commands[grid.curRow][grid.curCol].command == Command.commandBlank || grid.commands[grid.curRow][grid.curCol].command == Command.commandEmpty) {
			terminate(grid);
		}
	}
	
//	Description: Terminates grid execution
//	Parameters: The grid to be terminated
//	Return: Void
	public void terminate(Grid grid) {
		grid.curRow = -1;
		grid.curCol = -1;
		grid.running = false;
	}
	
}
