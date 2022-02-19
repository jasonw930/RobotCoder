package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.LinkedHashMap;

import command.Command;
import command.Grid;
import command.GridCommand;
import main.Game;

// GUI to display the programming grid
public class GuiGrid extends Gui {

	public Font[] sansSerif;
	public Font sansSerifBig;
	public Color background;
	public Image highlight;
	public Image[] arrow;
	
	public Rectangle editButton;
	public Rectangle savesButton;
	public Rectangle runButton;
	
//	Description: Used to draw a grid within a certain area
//	Parameters: Graphics object, highlight and arrow images, fonts, the grid to be displayed, tile size of grid, boundaries to draw the grid in
//	Return: Void
	public static void drawGrid(Graphics2D graphics, Image highlight, Image[] arrow, Font[] sansSerif, Grid grid, int tileSize, Rectangle bounds) {
		AffineTransform at = graphics.getTransform();
		// Calculating grid position to center it within bounds
		int space = tileSize + tileSize / 4;
		int gridx = bounds.x + (bounds.width - grid.width * space + tileSize / 4) / 2;
		int gridy = bounds.y + (bounds.height - grid.height * space + tileSize / 4) / 2;
		for (int r = 0; r < grid.height; r++) {
			for (int c = 0; c < grid.width; c++) {
				// Draw command
				graphics.translate(gridx+c*space, gridy+r*space);
				graphics.drawImage(grid.commands[r][c].command.texture, 0, 0, tileSize, tileSize, Game.frame);
				graphics.translate(tileSize / 2, tileSize / 2);
				if (grid.commands[r][c].command == Command.commandLoop) {
					// Draw loop counter
					graphics.setFont(sansSerif[tileSize / 4]);
					graphics.setColor(Color.DARK_GRAY);
					LinkedHashMap<String, Integer> v = grid.running ? grid.commands[r][c].vars : grid.commands[r][c].iniVars;
					graphics.drawString(Integer.toString(v.get("repeats")),
							-graphics.getFontMetrics().stringWidth(Integer.toString(v.get("repeats"))) / 2,
							graphics.getFontMetrics().getHeight() / 3);
				}
				
				// Draw all connecting arrows between commands
				AffineTransform at2 = graphics.getTransform();
				for (int i = 0; i < grid.commands[r][c].command.args.length; i++) {
					String argName = grid.commands[r][c].command.args[i];
					if (grid.commands[r][c].args.get(argName) == 0) {
						graphics.translate(space / 2, 0);
					} else if (grid.commands[r][c].args.get(argName) == 1) {
						graphics.translate(0, space / 2);
						graphics.rotate(Math.PI / 2);
						graphics.scale(1, -1);
					} else if (grid.commands[r][c].args.get(argName) == 2) {
						graphics.translate(-space / 2, 0);
						graphics.rotate(Math.PI);
						graphics.scale(1, -1);
					} else if (grid.commands[r][c].args.get(argName) == 3) {
						graphics.translate(0, -space / 2);
						graphics.rotate(3 * Math.PI / 2);
					}
					if (grid.commands[r][c].args.get(argName) != -1) {
						graphics.drawImage(arrow[i], -tileSize / 2, -tileSize / 2, tileSize, tileSize, Game.frame);
					}
					graphics.setTransform(at2);
				}
				graphics.setTransform(at);
			}
		}
		// Highlight which command is currently being run
		if (grid.curRow >= 0 && grid.curCol >= 0)
			graphics.drawImage(highlight, gridx+grid.curCol*space, gridy+grid.curRow*space, tileSize, grid.tileSize, Game.frame);
	}
	
	public GuiGrid() {
		super();
		
		x = 30;
		y = 30;
		width = 340;
		height = Game.screenHeight - 60;
		
		sansSerif = new Font[100];
		for (int i = 0; i < sansSerif.length; i++) {
			sansSerif[i] = new Font("SansSerif", Font.BOLD, i);
		}
		sansSerifBig = sansSerif[32];
		background = new Color(0x101010);
		highlight = Toolkit.getDefaultToolkit().createImage("textures/misc/grid_highlight.png");
		arrow = new Image[3];
		for (int i = 0; i < 3; i++)
			arrow[i] = Toolkit.getDefaultToolkit().createImage("textures/misc/grid_arrow_" + i + ".png");
		
		editButton = new Rectangle(30, height - 100, 135, 30);
		savesButton = new Rectangle(175, height - 100, 135, 30);
		runButton = new Rectangle(30, height - 60, 280, 30);
	}
	
	public void drawImage() {
		graphics.translate(x, y);
		
		// Grid Container
		Grid curGrid = Game.levelManager.currentGrid;
		
		graphics.setColor(background);
		graphics.fillRect(0, 0, width, height);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(sansSerifBig);
		graphics.drawString("Grid", (width - graphics.getFontMetrics().stringWidth("Grid")) / 2, 40);
		
		// Buttons
		if (!Game.levelManager.currentGrid.running) {
			drawButton(editButton, sansSerif[18], Game.guiEditGrid.visible ? Color.GRAY : Color.WHITE, Color.BLACK, "Edit");
			drawButton(savesButton, sansSerif[18], Color.WHITE, Color.BLACK, "Saves");
			drawButton(runButton, sansSerif[18], Color.WHITE, Color.BLACK, "Run");
		}
		
		// Grid
		GuiGrid.drawGrid(graphics, highlight, arrow, sansSerif, curGrid, curGrid.tileSize, new Rectangle(0, 40, width, height - 140));
		
		graphics.translate(-x, -y);
	}
	
	@Override
	public void mousePressed(int x, int y, int button) {
		// Different buttons being pressed to bring up different menus
		if (!Game.levelManager.currentGrid.running && editButton.contains(x, y)) {
			Game.guiEditGrid.visible = !Game.guiEditGrid.visible;
			Game.guiEditGrid.offset = 1;
			Game.guiEditGrid.cur = 1;
			Game.guiEditCommand.visible = false;
			Game.guiEditCommand.curR = -1;
			Game.guiEditCommand.curC = -1;
			return;
		}
		if (!Game.levelManager.currentGrid.running && savesButton.contains(x, y)) {
			Game.guiEditGrid.visible = false;
			Game.guiEditCommand.visible = false;
			Game.guiTips.visible = false;
			Game.guiEditCommand.curR = -1;
			Game.guiEditCommand.curC = -1;
			Game.guiSaves.selected = -1;
			Game.guiSaves.previewGrid = null;
			Game.guiSaves.visible = true;
			return;
		}
		if (!Game.levelManager.currentGrid.running && runButton.contains(x, y)) {
			Game.guiEditGrid.visible = false;
			Game.guiEditCommand.visible = false;
			Game.guiTips.visible = false;
			Game.guiEditCommand.curR = -1;
			Game.guiEditCommand.curC = -1;
			Game.levelManager.currentGrid.run();
			return;
		}
		// Clicking on a command in the grid
		Grid curGrid = Game.levelManager.currentGrid;
		int space = curGrid.tileSize + curGrid.tileSize / 4;
		int gridx = (width - curGrid.width * space + curGrid.tileSize / 4) / 2;
		int gridy = 40 + (height - 140 - curGrid.height * space + curGrid.tileSize / 4) / 2;
		if (x >= gridx && x < gridx+space*curGrid.width && y >= gridy && y < gridy+space*curGrid.height) {
			if ((x - gridx) % space < curGrid.tileSize && (y - gridy) % space < curGrid.tileSize) {
				int r = (y - gridy) / space;
				int c = (x - gridx) / space;
				// Change command if the person is currently editing the grid
				if (button == 1) {
					if (Game.guiEditGrid.visible) {
						curGrid.commands[r][c] = new GridCommand(Game.guiEditGrid.commands.get(Game.guiEditGrid.cur), r, c);
					}
				} else if (button == 3) {
					// Display the command editing GUI to edit the command that was clicked
					if (!curGrid.running && curGrid.commands[r][c].command != Command.commandEmpty && curGrid.commands[r][c].command != Command.commandBlank) {
						if (Game.guiEditCommand.curR == r && Game.guiEditCommand.curC == c) {
							Game.guiEditCommand.curR = -1;
							Game.guiEditCommand.curC = -1;
							Game.guiEditCommand.visible = false;
						} else {
							Game.guiEditCommand.curR = r;
							Game.guiEditCommand.curC = c;
							Game.guiEditCommand.visible = true;
						}
						Game.guiEditGrid.visible = false;
					}
				}
			}
		}
	}
	
}
