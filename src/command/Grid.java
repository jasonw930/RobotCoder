package command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import main.Game;

// Programming grid used to place and connect commands
public class Grid implements Comparable<Grid> {

	public static Grid[] grids;
	public static ArrayList<String>[] gridSaves;

	public String name;
	public int id;
	public int width, height;
	public int tileSize;
	
	public GridCommand[][] commands;
	public int curRow;
	public int curCol;
	
	public boolean running;
	
	@SuppressWarnings("unchecked")
	public static void initialize() {
		grids = new Grid[17];
		grids[0] = new Grid("grid_0", 0, 64);
		grids[1] = new Grid("grid_1", 1, 64);
		grids[2] = new Grid("grid_2", 2, 64);
		grids[3] = new Grid("grid_3", 3, 64);
		grids[4] = new Grid("grid_4", 4, 40);
		grids[5] = new Grid("grid_5", 5, 40);
		grids[6] = new Grid("grid_6", 6, 64);
		grids[7] = new Grid("grid_7", 7, 64);
		grids[8] = new Grid("grid_8", 8, 64);
		grids[9] = new Grid("grid_9", 9, 64);
		grids[10] = new Grid("grid_10", 10, 48);
		grids[11] = new Grid("grid_11", 11, 64);
		grids[12] = new Grid("grid_12", 12, 64);
		grids[13] = new Grid("grid_13", 13, 64);
		grids[14] = new Grid("grid_14", 14, 64);
		grids[15] = new Grid("grid_15", 15, 48);
		grids[16] = new Grid("grid_16", 16, 48);
		
		GridNameComparator comp = new GridNameComparator();
		// Reads and binary inserts save names
		gridSaves = new ArrayList[grids.length];
		for (int i = 0; i < grids.length; i++) {
			gridSaves[i] = new ArrayList<String>();
			for (String save : new File("grids/" + grids[i].name + "_saves").list()) {
				if (save.endsWith(".txt")) {
					String name = save.substring(0, save.length() - 4);
					System.out.println(grids[i].name + " save found: " + name);
					gridSaves[i].add(-Collections.binarySearch(gridSaves[i], name, comp) - 1, name);
				}
			}
		}
	}
	
	public Grid(String n, int i, int ts) {
		name = n;
		id = i;
		tileSize = ts;
		reset();
	}
	
//	Description: Used to compare grids
//	Parameters: Other grid to compare to
//	Return: Comparison result
	public int compareTo(Grid g) {
		return name.compareToIgnoreCase(g.name);
	}
	
//	Description: Resets grid by loading save file
//	Parameters: None
//	Return: Void
	public void reset() {
		load("grids/" + name + ".txt");
	}
	
//	Description: Resets variables in the grid to their initial values after execution completes
//	Parameters: None
//	Return: Void
	public void resetVars() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				for (String s : commands[r][c].command.vars) {
					commands[r][c].vars.put(s, commands[r][c].iniVars.get(s));
				}
			}
		}
	}
	
//	Description: Loads a save file by reading the file specified by the file name
//	Parameters: Name of save file
//	Return: Void
	public void load(String fileName) {
		try {
			System.out.println("Loaded grid: " + name);
			Scanner file = new Scanner(new File(fileName));
			String[] dim = file.nextLine().split(" ");
			height = Integer.parseInt(dim[0]);
			width = Integer.parseInt(dim[1]);
			
			file.nextLine();
			commands = new GridCommand[height][width];
			for (int r = 0; r < height; r++) {
				String[] line = file.nextLine().split("(?<=\\}) (?=\\{)");
				for (int c = 0; c < width; c++) {
					// Parsing individual commands
					String[] str = (line[c].substring(1, line[c].length() - 1) + "|.").split("\\|");
					String cmd = str[0];
					String[] args = str[1].length() == 0 ? new String[0] : str[1].split(",");
					String[] vars = str[2].length() == 0 ? new String[0] : str[2].split(",");
					commands[r][c] = new GridCommand(Command.commands.get(cmd), r, c);
					for (int i = 0; i < args.length; i++) {
						commands[r][c].args.put(commands[r][c].command.args[i], Integer.parseInt(args[i]));
					}
					for (int i = 0; i < vars.length; i++) {
						commands[r][c].iniVars.put(commands[r][c].command.vars[i], Integer.parseInt(vars[i]));
					}
				}
			}
			
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		curRow = -1;
		curCol = -1;
		running = false;
	}
	
//	Description: Saves the current grid to a save file
//	Parameters: Name of save file
//	Return: Void
	public void save(String fileName) {
		try {
			PrintWriter file = new PrintWriter(new FileWriter(fileName));
			file.println(height + " " + width + "\n");
			for (int r = 0; r < height; r++) {
				for (int c = 0; c < width; c++) {
					// Converting individual commands to strings
					String cmd = commands[r][c].command.key;
					String[] args = new String[commands[r][c].command.args.length];
					String[] vars = new String[commands[r][c].command.vars.length];
					for (int i = 0; i < commands[r][c].command.args.length; i++) {
						args[i] = Integer.toString(commands[r][c].args.get(commands[r][c].command.args[i]));
					}
					for (int i = 0; i < commands[r][c].command.vars.length; i++) {
						vars[i] = Integer.toString(commands[r][c].iniVars.get(commands[r][c].command.vars[i]));
					}
					file.print("{" + cmd + "|" + String.join(",", args) + "|" + String.join(",", vars) + "}");
					if (c < width - 1) file.print(" ");
					else if (r < height - 1) file.println();
				}
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	Description: Runs the program inside the grid
//	Parameters: None
//	Return: Void
	public void run() {
		if (!running) {
			Game.levelManager.currentMap.reset();
			resetVars();
			for (int r = 0; r < height; r++) {
				for (int c = 0; c < width; c++) {
					if (commands[r][c].command == Command.commandStart) {
						curRow = r;
						curCol = c;
						running = true;
						return;
					}
				}
			}
		}
	}
	
//	Description: Updates the grid as it is executing
//	Parameters: None
//	Return: Void
	public void update() {
		if (running) {
			commands[curRow][curCol].execute();
		}
	}
	
//	Description: Gets an adjacent command using direction angle
//	Parameters: None
//	Return: The adjacent command
	public GridCommand getAdjGrid(int r, int c, int dir) {
		// Convert angle to change in row and change in column
		int dr = 0, dc = 1;
		for (int i = 0; i < dir; i++) {
			int temp = dr;
			dr = dc;
			dc = -temp;
		}
		try {
			return commands[r+dr][c+dc];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
}
